# Builder Pattern — Restaurant Menu

## What is the Builder pattern?

The **Builder** is a *creational* design pattern. It lets you create a complex object **step by step**, and keeps the code that builds the object apart from the object itself.

It is especially handy when an object has many fields, some of them optional or with default values. Instead of calling one huge constructor, you describe the object one piece at a time.

### The problem it solves

Imagine creating a menu with a plain constructor:

```java
Menu menu = new Menu("Amanida Mediterrània", true, true, "Filet de Vedella",
                     "Guarnició extra", "Mousse de xocolata", null, "Vi Negre");
```

Which `true` means "vegan"? Why is there a `null`? This is known as the **telescoping constructor** anti-pattern: constructors with so many parameters that nobody can read them without checking the class.

### Why use it?

- 🧱 The object has many optional parts or possible configurations.
- 🏚️ The creation process becomes easier to read and maintain.
- ⛓️ It avoids long, confusing constructors.
- 🌊 Combined with a **Fluent** style, the code reads almost like a sentence.

## Fluent Builder

A **Fluent Builder** is a Builder where each method returns an object you can call the next method on. This lets you *chain* calls together:

```java
Car car = new CarBuilder()
        .withModel("Sedan Premium")
        .withEngine("Hybrid", 180)
        .withColour("Metallic Black")
        .withNavigationSystem()
        .build();
```

## The exercise: building restaurant menus

The goal is to build different kinds of restaurant menus using a Fluent Builder, following the SOLID principles.

### What a menu can contain

- Starter
- Main course
- Dessert **or** coffee
- Drink

### Rules

1. Any dish can be marked as **vegan** and/or **gluten-free**.
2. The main course can have a **supplement** (for example, an extra side dish).
3. A **half menu** contains only a main course and a drink.
4. A **kids' menu** contains only a main course, a dessert and a drink.
5. A menu may have **no drink**.
6. Dessert is **optional**, and it can be either a dessert or a coffee, **never both**.

### Requirements

1. Build menus step by step, always in the correct order. For example, you cannot add a dessert before the main course.
2. If you choose a dessert, you cannot add a coffee, and vice versa. This rule must be enforced by the **design of the interfaces**, not by `if` checks.
3. Dish properties (vegan, gluten-free, supplement) are set with expressive fluent methods instead of `boolean` parameters.
4. Unit tests show how different menus are created.

## Progressive interfaces

This is the key idea of the solution. A **progressive interface** reduces the options available at each step of the construction. Every builder method returns a **different interface**, and that interface only exposes the methods that make sense next.

As a result, the **compiler** prevents mistakes such as adding a dessert before the main course, or adding both a dessert and a coffee. The wrong method simply does not exist at that point, so the code will not compile.

### Steps and allowed methods

```
MenuBuilder.create()  ──► MenuStart

MenuStart
 ├─ withStarter(...)     ──► AfterStarter
 └─ withMainCourse(...)  ──► AfterMainCourse

AfterStarter
 ├─ isVegan() / isGlutenFree()  ──► AfterStarter
 └─ withMainCourse(...)         ──► AfterMainCourse

AfterMainCourse
 ├─ isVegan() / isGlutenFree() / withSupplement(...)  ──► AfterMainCourse
 ├─ withDessert(...)  ──► AfterDessert
 ├─ withCoffee(...)   ──► AfterCoffee
 ├─ withDrink(...)    ──► ReadyToBuild
 └─ build()

AfterDessert
 ├─ isVegan() / isGlutenFree()  ──► AfterDessert
 ├─ withDrink(...)  ──► ReadyToBuild
 └─ build()

AfterCoffee
 ├─ withDrink(...)  ──► ReadyToBuild
 └─ build()

ReadyToBuild
 └─ build()  ──► Menu
```

Notice that `AfterDessert` has no `withCoffee(...)` and `AfterCoffee` has no `withDessert(...)`. This is how the "dessert or coffee, never both" rule is guaranteed.

Notice, too, that `isVegan()` and `isGlutenFree()` always apply to the **last dish added**, so the properties sit right next to the dish they describe.

### The entry point: `MenuBuilder.create()`

Progressive interfaces only protect you if you never hold a reference to the concrete `MenuBuilder` class. That class implements **every** step interface, so with a `MenuBuilder` in your hands you could call any method in any order:

```java
new MenuBuilder().withDessert("Mousse").build();   // dessert with no main course!
```

To prevent this, `MenuBuilder` has a **private constructor** and a **static factory method**:

```java
private MenuBuilder() {
}

public static MenuStart create() {
    return new MenuBuilder();
}
```

- The constructor is `private`, so `new MenuBuilder()` does not compile outside the class.
- `create()` is `static`, so you call it on the class itself, before any builder exists.
- It returns the object as a `MenuStart`. The real object is still a `MenuBuilder`, but the caller only sees `withStarter(...)` and `withMainCourse(...)`, so the very first step is already restricted.

## Project structure

```
builder/
├── Dish.java              A dish: name + vegan / gluten-free flags
├── Menu.java              The final object; its constructor is package-private
├── MenuBuilder.java       Implements every step interface; created via create()
└── interfaces/
    ├── MenuStart.java
    ├── AfterStarter.java
    ├── AfterMainCourse.java
    ├── AfterDessert.java
    ├── AfterCoffee.java
    └── ReadyToBuild.java
```

- **`Menu`** has a package-private constructor, so the builder is the only way to create one from outside the package.
- **`MenuBuilder`** implements all the step interfaces, but each method returns only the interface for the next step. Its constructor is private, and `create()` hands out a `MenuStart`. That is what keeps the client on the right path from the very first call.

## Usage examples

```java
Menu executiveMenu = MenuBuilder.create()
        .withStarter("Amanida Mediterrània")
            .isVegan()
            .isGlutenFree()
        .withMainCourse("Filet de Vedella")
            .withSupplement("Guarnició extra")
        .withDessert("Mousse de xocolata")
        .withDrink("Vi Negre")
        .build();

Menu kidsMenu = MenuBuilder.create()
        .withMainCourse("Macarrons amb tomàquet")
        .withDessert("Gelat de Vainilla")
        .withDrink("Suc de taronja")
        .build();

Menu halfMenu = MenuBuilder.create()
        .withMainCourse("Risotto de bolets")
            .isVegan()
        .withCoffee("Espresso")
        .withDrink("Aigua")
        .build();
```

Printing `executiveMenu` gives:

```text
Menú:
 - Entrant: Amanida Mediterrània (Vegan) (Gluten Free)
 - Plat Principal: Filet de Vedella, Suplement: Guarnició extra
 - Postres: Mousse de xocolata
 - Beguda: Vi Negre
```

## Tests

The unit tests live in `src/test/java/com/patterns/builder/MenuBuilderTest.java`. They check that each type of menu is built and printed correctly.

Run them from the project root with:

```bash
mvn test
```

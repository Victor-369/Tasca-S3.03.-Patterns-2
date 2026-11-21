# Decorator Pattern — Bubble Tea Shop

## What is the Decorator pattern?

The **Decorator** is a *structural* design pattern. It lets you add new behaviour to an object **dynamically**, by wrapping it inside another object, without changing the original class.

Each wrapper (a *decorator*) implements the same interface as the object it wraps. It passes each call on to the wrapped object and then adds its own contribution. Because every layer looks exactly like the original, decorators can be stacked in any order and in any number.

### The problem it solves

Imagine a bubble tea shop with three bases and four optional extras. If every possible drink had its own class (`LatteWithSugar`, `LatteWithSugarAndIce`, `MatchaWithTapiocaAndTwoFlavours`…), the number of classes would grow out of control. Every new extra would multiply the number of classes again.

With the Decorator pattern, there is **one class per base and one class per extra**. Any drink is built at runtime by wrapping a base in as many extras as needed.

### Why use it?

- 🧩 Functionality can be added flexibly, without bloating the class hierarchy.
- 👽 Extending behaviour through inheritance would be awkward or impossible.
- 🕸️ There are many possible combinations of features, and a subclass for each one would be unmanageable.

It is commonly used in user-interface frameworks, file input/output (Java's own `InputStream` classes are decorators), logging frameworks and product customisation.

## The exercise: a bubble tea app

The goal is to design a set of classes that simulates customising a bubble tea, using the Decorator pattern to add ingredients such as tapioca pearls, ice and sugar.

### Bases

| Base | Class | Price |
|------|-------|-------|
| 🥛 Latte | `LatteBase` | €3.50 |
| 🍵 Matcha | `MatchaBase` | €3.20 |
| 🌿 Tea | `TeaBase` | €3.00 |

### Extras

| Extra | Class | Price |
|-------|-------|-------|
| 🧊 Ice | `Ice` | +€0.25 |
| 🍬 Sugar | `Sugar` | +€0.30 |
| 🥥 Tapioca | `Tapioca` | +€0.50 |
| 🍓 Flavour | `Flavor` | +€0.60 per flavour |

ℹ️ Adding two flavours raises the total by €1.20.

### Requirements

1. A `BubbleTea` interface with `String getDescription()` and `double getCost()`.
2. Base classes (`LatteBase`, `MatchaBase`, `TeaBase`) that implement `BubbleTea`, each with its own starting price and description.
3. Concrete decorators (`Ice`, `Sugar`, `Tapioca`, `Flavor`) that extend `BubbleTeaDecorator`.
4. Every decorator adds to both the cost and the description of the bubble tea.
5. Unit tests that build bubble teas with several combinations.

## How it works

Every decorator holds a reference to the `BubbleTea` it wraps. When `getCost()` or `getDescription()` is called on the outermost layer, the call travels inwards, layer by layer, until it reaches the base. On the way back out, each layer adds its own price and its own text.

```
Flavor("Mango")
 └─ Flavor("Maduixa")
     └─ Sugar
         └─ Tapioca
             └─ LatteBase
```

```
getCost()        = 0.60 + (0.60 + (0.30 + (0.50 + 3.50)))  = 5.50
getDescription() = "Latte Bubble Tea" + " + Tapioca" + " + Sugar"
                   + " + Flavor: Maduixa" + " + Flavor: Mango"
```

Each decorator follows the same pattern, for example `Sugar`:

```java
@Override
public String getDescription() {
    return this.bubbleTea.getDescription() + " + Sugar";
}

@Override
public double getCost() {
    return this.bubbleTea.getCost() + 0.30;
}
```

## Design notes

- **Programming to interfaces.** Bases, decorators and client code all work with the `BubbleTea` interface, which lives in the `interfaces/` sub-package. A decorator does not know or care whether it wraps a base or another decorator.
- **Abstract decorator.** `BubbleTeaDecorator` implements `BubbleTea` and stores the wrapped drink in a `protected final BubbleTea bubbleTea` field. Concrete decorators extend it and only have to write their own `getDescription()` and `getCost()`.
- **Open for extension.** A new extra (say, milk foam) only needs a new class that extends `BubbleTeaDecorator`. No existing class changes, which follows the **Open/Closed Principle**.
- **Composition over inheritance.** Drinks are assembled at runtime by wrapping objects, rather than by creating a subclass for every combination.
- **Repeatable extras.** Because each extra is an independent layer, the same decorator can be applied more than once. Two `Flavor` decorators add €0.60 each, and a double `Sugar` is charged twice.
- **`double` and rounding.** The exercise requires `getCost()` to return a `double`. Binary floating-point types cannot represent most decimal fractions exactly, so sums such as `3.50 + 0.50 + 0.30 + 0.60 + 0.60` give `5.499999999999999` rather than `5.5`. The code returns this raw value as it is, and the tests compare prices with a small margin of error for that reason. See the tip under [Usage example](#usage-example) for how to display prices with two decimal places.

## Project structure

```
decorator/
├── BubbleTeaDecorator.java   Abstract decorator: stores the wrapped BubbleTea
├── LatteBase.java            Base: "Latte Bubble Tea", €3.50
├── MatchaBase.java           Base: "Matcha Bubble Tea", €3.20
├── TeaBase.java              Base: "Tea Bubble Tea", €3.00
├── decorators/
│   ├── Flavor.java           + Flavor: <name>, +€0.60
│   ├── Ice.java              + Ice, +€0.25
│   ├── Sugar.java            + Sugar, +€0.30
│   └── Tapioca.java          + Tapioca, +€0.50
└── interfaces/
    └── BubbleTea.java        getDescription(), getCost()
```

## Usage example

```java
BubbleTea tea = new LatteBase();
tea = new Tapioca(tea);
tea = new Sugar(tea);
tea = new Flavor(tea, "Maduixa");
tea = new Flavor(tea, "Mango");

System.out.println(tea.getDescription());
System.out.println(tea.getCost());
```

Console output:

```text
Latte Bubble Tea + Tapioca + Sugar + Flavor: Maduixa + Flavor: Mango
5.499999999999999
```

The cost is correct, but floating-point rounding means it is not printed as `5.50`.

> 💡 **Tip: displaying prices with two decimal places**
>
> The code does not format prices; `getCost()` returns the raw `double`. To show a price rounded to two decimal places, format it when printing:
>
> ```java
> System.out.println(String.format(Locale.ROOT, "%.2f", tea.getCost()));   // 5.50
> ```
>
> `%.2f` rounds to two decimal places, and `Locale.ROOT` guarantees a dot as the decimal separator (`5.50`, not `5,50`), whatever the locale of the machine. The Observer exercise already formats its share values in this way.

## Tests

The unit tests live in `src/test/java/com/patterns/decorator/BubbleTeaTest.java`. They use **JUnit**'s `assertEquals`, and compare costs with a margin of error (`ERROR_MARGIN = 0.01`) to allow for floating-point rounding.

No mocks are needed. Both methods return a value that can be checked directly, and the aim is to prove that the real bases and decorators work correctly together.

| Test | What it checks |
|------|----------------|
| `getDescriptionAndCost_latteBaseWithoutExtras_returnsBaseValues` | A Latte on its own: `Latte Bubble Tea`, €3.50. |
| `getDescriptionAndCost_matchaBaseWithoutExtras_returnsBaseValues` | A Matcha on its own: `Matcha Bubble Tea`, €3.20. |
| `getDescriptionAndCost_teaBaseWithoutExtras_returnsBaseValues` | A Tea on its own: `Tea Bubble Tea`, €3.00. |
| `getDescriptionAndCost_teaWithIce_includesIceSurcharge` | Ice adds ` + Ice` and €0.25. |
| `getDescriptionAndCost_matchaWithSugar_includesSugarSurcharge` | Sugar adds ` + Sugar` and €0.30. |
| `getDescriptionAndCost_teaWithTapioca_includesTapiocaSurcharge` | Tapioca adds ` + Tapioca` and €0.50. |
| `getDescriptionAndCost_latteWithOneFlavour_includesFlavourSurcharge` | A flavour adds ` + Flavor: <name>` and €0.60. |
| `getDescriptionAndCost_exerciseExample_matchesExpectedOutput` | The exercise example gives the expected description and €5.50. |
| `getDescriptionAndCost_teaWithTwoFlavours_chargesEachFlavourSeparately` | Two flavours add €1.20 in total. |
| `getDescriptionAndCost_teaWithDoubleSugar_chargesSugarTwice` | The same extra applied twice is charged twice. |
| `getDescriptionAndCost_latteWithAllExtras_includesEverySurcharge` | Every extra at once on a Latte: €5.15. |

Run all the tests from the project root with:

```bash
mvn test
```

Or run only the Decorator tests with:

```bash
mvn test -Dtest=BubbleTeaTest
```

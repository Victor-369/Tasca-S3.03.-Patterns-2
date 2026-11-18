package com.patterns.builder;

public class Menu {
    private final Dish starter;
    private final Dish mainCourse;
    private final String supplement;
    private final Dish dessert;
    private final String coffee;
    private final String drink;


    Menu(Dish starter, Dish mainCourse, String supplement, Dish dessert, String coffee, String drink) {
        this.starter = starter;
        this.mainCourse = mainCourse;
        this.supplement = supplement;
        this.dessert = dessert;
        this.coffee = coffee;
        this.drink = drink;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("Menú:");

        if (this.starter != null) text.append("\n - Entrant: ").append(this.starter);

        text.append("\n - Plat Principal: ").append(this.mainCourse);

        if (this.supplement != null) text.append(", Suplement: ").append(this.supplement);
        if (this.dessert != null) text.append("\n - Postres: ").append(this.dessert);
        if (this.coffee != null) text.append("\n - Cafè: ").append(this.coffee);
        if (this.drink != null) text.append("\n - Beguda: ").append(this.drink);

        return text.toString();
    }
}

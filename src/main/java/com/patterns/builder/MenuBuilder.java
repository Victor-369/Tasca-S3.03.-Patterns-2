package com.patterns.builder;

import com.patterns.builder.interfaces.*;

public class MenuBuilder implements MenuStart, AfterStarter, AfterMainCourse, AfterDessert, AfterCoffee, ReadyToBuild {
    private Dish starter;
    private Dish mainCourse;
    private String supplement;
    private Dish dessert;
    private String coffee;
    private String drink;
    private Dish currentDish;

    private MenuBuilder() {
    }

    public static MenuStart create() {
        return new MenuBuilder();
    }

    @Override
    public AfterMainCourse withSupplement(String supplement) {
        this.supplement = supplement;

        return this;
    }

    @Override
    public AfterDessert withDessert(String dessert) {
        Dish newDessert = new Dish(dessert);
        this.currentDish = newDessert;
        this.dessert = newDessert;

        return this;
    }

    @Override
    public AfterCoffee withCoffee(String coffee) {
       this.coffee = coffee;

       return this;
    }

    @Override
    public ReadyToBuild withDrink(String drink) {
        this.drink = drink;

        return this;
    }

    @Override
    public MenuBuilder isVegan() {
        this.currentDish.markVegan();

        return this;
    }

    @Override
    public MenuBuilder isGlutenFree() {
        this.currentDish.markGlutenFree();

        return this;
    }

    @Override
    public AfterStarter withStarter(String starter) {
        Dish newStarter = new Dish(starter);
        this.currentDish = newStarter;
        this.starter = newStarter;

        return this;
    }

    @Override
    public AfterMainCourse withMainCourse(String mainCourse) {
        Dish newMainCourse = new Dish(mainCourse);
        this.currentDish = newMainCourse;
        this.mainCourse = newMainCourse;

        return this;
    }

    @Override
    public Menu build() {
        return new Menu(this.starter, this.mainCourse, this.supplement, this.dessert, this.coffee, this.drink);
    }
}

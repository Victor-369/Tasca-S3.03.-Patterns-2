package com.patterns.builder;

import com.patterns.builder.interfaces.*;

public class MenuBuilder implements MenuStart, AfterStarter, AfterMainCourse, AfterDessert, AfterCoffee, ReadyToBuild {
    private Menu menu;
    private Dish dish;

    @Override
    public AfterMainCourse withSupplement(String supplement) {
        return this;
    }

    @Override
    public AfterDessert withDessert(String dessert) {
        return this;
    }

    @Override
    public AfterCoffee withCoffee(String coffee) {
        return this;
    }

    @Override
    public ReadyToBuild withDrink(String drink) {
        return this;
    }

    @Override
    public Menu build() {
        return this.menu;
    }

    @Override
    public MenuBuilder isVegan() {
        return this;
    }

    @Override
    public MenuBuilder isGlutenFree() {
        return this;
    }

    @Override
    public AfterStarter withStarter(String starter) {
        return this;
    }

    @Override
    public AfterMainCourse withMainCourse(String mainCourse) {
        return this;
    }
}

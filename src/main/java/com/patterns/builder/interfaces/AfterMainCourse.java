package com.patterns.builder.interfaces;

import com.patterns.builder.Menu;

public interface AfterMainCourse {
    public AfterMainCourse isVegan();
    public AfterMainCourse isGlutenFree();
    public AfterMainCourse withSupplement(String supplement);
    public AfterDessert withDessert(String dessert);
    public AfterCoffee withCoffee(String coffee);
    public ReadyToBuild withDrink(String drink);
    public Menu build();
}

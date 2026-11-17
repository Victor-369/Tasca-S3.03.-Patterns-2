package com.patterns.builder.interfaces;

import com.patterns.builder.Menu;

public interface AfterMainCourse {
    public AfterMainCourse isVegan();
    public AfterMainCourse isGlutenFree();
    public AfterMainCourse withSupplement();
    public AfterDessert withDessert();
    public AfterCoffee withCoffee();
    public ReadyToBuild withDrink();
    public Menu build();
}

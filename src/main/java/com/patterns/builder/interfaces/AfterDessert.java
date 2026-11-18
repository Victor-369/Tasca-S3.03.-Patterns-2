package com.patterns.builder.interfaces;

import com.patterns.builder.Menu;

public interface AfterDessert {
    public AfterDessert isVegan();
    public AfterDessert isGlutenFree();
    public ReadyToBuild withDrink(String drink);
    public Menu build();
}

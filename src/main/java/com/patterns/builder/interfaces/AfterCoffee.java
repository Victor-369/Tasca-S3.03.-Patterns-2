package com.patterns.builder.interfaces;

import com.patterns.builder.Menu;

public interface AfterCoffee {
    public ReadyToBuild withDrink(String drink);
    public Menu build();
}

package com.patterns.decorator;

import com.patterns.decorator.interfaces.BubbleTea;

public class MatchaBase implements BubbleTea {
    public MatchaBase() { }

    @Override
    public String getDescription() {
        return "Matcha Bubble Tea";
    }

    @Override
    public double getCost() {
        return 3.20;
    }
}

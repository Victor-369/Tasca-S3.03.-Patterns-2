package com.patterns.decorator;

import com.patterns.decorator.interfaces.BubbleTea;

public class LatteBase implements BubbleTea {
    public LatteBase() { }

    @Override
    public String getDescription() {
        return "Latte Bubble Tea";
    }

    @Override
    public double getCost() {
        return 3.50;
    }
}

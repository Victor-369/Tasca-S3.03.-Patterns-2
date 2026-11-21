package com.patterns.decorator;

import com.patterns.decorator.interfaces.BubbleTea;

public class TeaBase implements BubbleTea {
    public TeaBase() { }

    @Override
    public String getDescription() {
        return "Tea Bubble Tea";
    }

    @Override
    public double getCost() {
        return 3.00;
    }
}

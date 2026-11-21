package com.patterns.decorator;

import com.patterns.decorator.interfaces.BubbleTea;

public class Ice extends BubbleTeaDecorator {
    public Ice(BubbleTea bubbleTea) {
        super(bubbleTea);
    }

    @Override
    public String getDescription() {
        return this.bubbleTea.getDescription() + " + Ice";
    }

    @Override
    public double getCost() {
        return this.bubbleTea.getCost() + 0.25;
    }
}

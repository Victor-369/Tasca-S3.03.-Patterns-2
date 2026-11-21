package com.patterns.decorator.decorators;

import com.patterns.decorator.BubbleTeaDecorator;
import com.patterns.decorator.interfaces.BubbleTea;

public class Flavor extends BubbleTeaDecorator {
    private final String flavor;

    public Flavor(BubbleTea bubbleTea, String flavor) {
        super(bubbleTea);
        this.flavor = flavor;
    }

    @Override
    public String getDescription() {
        return this.bubbleTea.getDescription() + " + Flavor: "  + this.flavor;
    }

    @Override
    public double getCost() {
        return this.bubbleTea.getCost() + 0.60;
    }
}

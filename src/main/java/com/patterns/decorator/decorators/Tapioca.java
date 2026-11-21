package com.patterns.decorator.decorators;

import com.patterns.decorator.BubbleTeaDecorator;
import com.patterns.decorator.interfaces.BubbleTea;

public class Tapioca extends BubbleTeaDecorator {
    public Tapioca(BubbleTea bubbleTea) {
        super(bubbleTea);
    }

    @Override
    public String getDescription() {
        return this.bubbleTea.getDescription() + " + Tapioca";
    }

    @Override
    public double getCost() {
        return this.bubbleTea.getCost() + 0.50;
    }
}

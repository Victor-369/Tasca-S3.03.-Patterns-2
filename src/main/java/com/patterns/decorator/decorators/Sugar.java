package com.patterns.decorator.decorators;

import com.patterns.decorator.BubbleTeaDecorator;
import com.patterns.decorator.interfaces.BubbleTea;

public class Sugar extends BubbleTeaDecorator {
    public Sugar(BubbleTea bubbleTea) {
        super(bubbleTea);
    }

    @Override
    public String getDescription() {
        return this.bubbleTea.getDescription() + " + Sugar";
    }

    @Override
    public double getCost() {
        return this.bubbleTea.getCost() + 0.30;
    }
}

package com.patterns.decorator;

import com.patterns.decorator.interfaces.BubbleTea;

public abstract class BubbleTeaDecorator implements BubbleTea {
    protected final BubbleTea bubbleTea;

    protected BubbleTeaDecorator(BubbleTea bubbleTea) {
        this.bubbleTea = bubbleTea;
    }
}

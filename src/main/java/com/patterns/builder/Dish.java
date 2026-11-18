package com.patterns.builder;

public class Dish {
    private final String name;
    private boolean isVegan = false;
    private boolean isGlutenFree = false;

    public Dish(String name) {
        this.name = name;
    }

    public void markVegan() {
        this.isVegan = true;
    }

    public void markGlutenFree() {
        this.isGlutenFree = true;
    }

    @Override
    public String toString() {
        String text = this.name;
        if (this.isVegan) text += " (Vegan)";
        if (this.isGlutenFree) text += " (Gluten Free)";

        return text;
    }
}

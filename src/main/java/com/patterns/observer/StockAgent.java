package com.patterns.observer;

import com.patterns.observer.interfaces.Observable;
import com.patterns.observer.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockAgent implements Observable {
    private final List<Observer> observers;
    private double currentValue;
    private boolean isStockMarketUp;

    public StockAgent() {
        this.observers = new ArrayList<>();
        this.isStockMarketUp = false;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void stockMarketUp(double value) {
        this.currentValue = value;
        this.isStockMarketUp = true;
        notifyObservers();
    }

    public void stockMarketDown(double value) {
        this.currentValue = value;
        this.isStockMarketUp = false;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        String stockMarketUp = "Stock market went UP to " + String.format(Locale.ROOT, "%.2f", this.currentValue);
        String stockMarketDown = "Stock market went DOWN to " + String.format(Locale.ROOT, "%.2f", this.currentValue);

        for (Observer observer : this.observers) {
            if (this.isStockMarketUp) {
                observer.update(stockMarketUp);
            } else {
                observer.update(stockMarketDown);
            }
        }
    }

}

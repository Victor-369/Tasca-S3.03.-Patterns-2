package com.patterns.observer;

import com.patterns.observer.interfaces.Observable;
import com.patterns.observer.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StockAgent  implements Observable {
    private final List<Observer> observers;
    private double currentValue;
    private double oldValue;

    public StockAgent() {
        this.observers = new ArrayList<>();
        this.currentValue = 90.67;
        this.oldValue = 90.67;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void stockMarketUp(double value) {
        this.oldValue = this.currentValue;
        this.currentValue = value;

        notifyObservers();
    }

    public void stockMarketDown(double value) {
        this.oldValue = this.currentValue;
        this.currentValue = value;

        notifyObservers();
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            if (this.currentValue > this.oldValue) {
                observer.update("Stock market went UP to " + String.format(Locale.ROOT, "%.2f", this.currentValue));
            } else {
                observer.update("Stock market went DOWN to " + String.format(Locale.ROOT, "%.2f", this.currentValue));
            }
        }
    }

}

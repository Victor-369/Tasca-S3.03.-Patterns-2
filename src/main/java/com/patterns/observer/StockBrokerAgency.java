package com.patterns.observer;

import com.patterns.observer.interfaces.Observer;

public class StockBrokerAgency implements Observer {
    private final String agencyName;

    public StockBrokerAgency(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public void update(String message) {
        System.out.println(agencyName + " received notification: " + message);
    }
}

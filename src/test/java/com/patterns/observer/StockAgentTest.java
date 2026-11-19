package com.patterns.observer;

import com.patterns.observer.interfaces.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class StockAgentTest {
    private StockAgent agent;
    private Observer alphaBrokers;
    private Observer zenithInvestments;

    @BeforeEach
    void setUp() {
        agent = new StockAgent();
        alphaBrokers = mock(Observer.class);
        zenithInvestments = mock(Observer.class);
        agent.addObserver(alphaBrokers);
        agent.addObserver(zenithInvestments);
    }

    @Test
    @DisplayName("Market up notifies every subscribed observer with UP message")
    void stockMarketUp_withSubscribedObservers_notifiesAllWithUpMessage() {
        agent.stockMarketUp(BigDecimal.valueOf(150.75));

        verify(alphaBrokers).update("Stock market went UP to 150.75");
        verify(zenithInvestments).update("Stock market went UP to 150.75");
    }

    @Test
    @DisplayName("Market down notifies with DOWN message")
    void stockMarketDown_withSubscribedObservers_notifiesAllWithDownMessage() {
        agent.stockMarketDown(BigDecimal.valueOf(145.50));

        verify(alphaBrokers).update("Stock market went DOWN to 145.50");
        verify(zenithInvestments).update("Stock market went DOWN to 145.50");
    }

    @Test
    @DisplayName("Removed observer stops receiving notifications")
    void removeObserver_thenMarketChanges_removedObserverIsNotNotified() {
        agent.removeObserver(zenithInvestments);

        agent.stockMarketUp(BigDecimal.valueOf(150.75));

        verify(alphaBrokers).update("Stock market went UP to 150.75");
        verifyNoInteractions(zenithInvestments);
    }
}

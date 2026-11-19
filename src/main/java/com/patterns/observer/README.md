# Observer Pattern — Stock Market Notifications

## What is the Observer pattern?

The **Observer** is a *behavioural* design pattern. It defines a **one-to-many** dependency between objects, so that when one object changes its state, all of its dependants are notified automatically.

In other words, it provides a **subscription mechanism**: several objects can sign up to be told about any event that happens to the object they are watching.

- The object being watched is the **Observable** (also called the *subject* or *publisher*).
- The objects that want to be told about changes are the **Observers** (also called *subscribers* or *listeners*).

### The problem it solves

Imagine several stockbroking agencies that need to know when the stock market moves. Without the Observer pattern, there are two poor options:

1. Each agency keeps asking the market "has anything changed?" over and over (**polling**). This wastes resources, and changes are noticed late.
2. The market calls each agency directly by name. Every time a new agency appears, the market's code has to change, so both sides become **tightly coupled**.

The Observer pattern fixes both problems. The market keeps a list of subscribers and simply tells all of them when something happens. It does not need to know who they are or what they do with the news.

### Why use it?

- 🔔 Several components must be notified automatically when a state change occurs.
- 🔃 A change in one object affects others, but we do not know which ones, or how many, when writing the code.
- 🔬 Objects must be able to subscribe or unsubscribe dynamically, at runtime.
- ✉️ We want a publish/subscribe (pub-sub) style system.
- 🗄️ Components stay decoupled: the Observable knows nothing about the internal logic of its Observers.

## The exercise: stock market notifications

The goal is to build a system in which a **stock agent** (the Observable) automatically notifies several **stockbroking agencies** (the Observers) every time the stock market goes up or down.

### Description

- A `StockAgent` class that:
  - lets observers subscribe and unsubscribe;
  - has methods to simulate the market going up or down;
  - when the market moves, automatically notifies every observer with a message stating the change and the new share value.
- One or more concrete observer classes (`StockBrokerAgency`) that:
  - subscribe to the `StockAgent`;
  - when notified, print the message on screen, showing whether the market went up or down and its current value.

### Requirements

1. The Observable keeps references to its observers.
2. Observers can be added and removed dynamically.
3. When the `StockAgent` changes state (up or down), it calls `notifyObservers()`.
4. Each observer prints the message it receives.
5. Unit tests cover subscribing several observers, simulating rises and falls, and checking that the observers receive the expected messages.

## How it works

```
                         ┌──────────────────────────┐
                         │  StockAgent (Observable) │
                         │  - observers: List       │
                         │  - currentValue          │
                         │  - isStockMarketUp       │
                         └────────────┬─────────────┘
                                      │ notifyObservers()
              ┌───────────────────────┼───────────────────────┐
              ▼                       ▼                       ▼
   ┌─────────────────────┐ ┌─────────────────────┐ ┌─────────────────────┐
   │ StockBrokerAgency   │ │ StockBrokerAgency   │ │  any other Observer │
   │ "Alpha Brokers"     │ │ "Zenith Investments"│ │                     │
   │ update(message)     │ │ update(message)     │ │ update(message)     │
   └─────────────────────┘ └─────────────────────┘ └─────────────────────┘
```

The sequence when the market rises:

```
agent.stockMarketUp(new BigDecimal("150.75"))
 ├─ currentValue     = 150.75 (BigDecimal)
 ├─ isStockMarketUp  = true
 └─ notifyObservers()
     └─ for each observer:
         observer.update("Stock market went UP to 150.75")
```

`stockMarketDown(value)` works in exactly the same way, but sets `isStockMarketUp` to `false`, so observers receive `"Stock market went DOWN to ..."`.

## Design notes

- **Programming to interfaces.** `Observable` and `Observer` live in the `interfaces/` sub-package. `StockAgent` only knows about the `Observer` interface, never about `StockBrokerAgency`. This follows the **Dependency Inversion Principle**.
- **Open for extension.** A new kind of subscriber (an email alert, a log writer, a mobile app…) only needs to implement `Observer`. `StockAgent` does not change at all, which follows the **Open/Closed Principle**.
- **Dynamic subscriptions.** Observers are kept in a `final List<Observer>`, and `addObserver(...)` / `removeObserver(...)` can be called at any time while the program runs.
- **Push model.** The Observable *pushes* a ready-made message to each observer through `update(String message)`, so observers do not need to query the agent afterwards.
- **`BigDecimal` for share values.** `stockMarketUp(...)` and `stockMarketDown(...)` take a `BigDecimal` rather than a `double`. Binary floating-point types cannot represent most decimal fractions exactly (for instance, `0.1 + 0.2` gives `0.30000000000000004`), so small rounding errors can creep in. `BigDecimal` stores the exact decimal value, which is the standard choice for prices and money. Create it from a `String`, as in `new BigDecimal("150.75")`, and not from a `double`, or the inaccuracy is carried over.
- **Locale-independent formatting.** The value is formatted with `String.format(Locale.ROOT, "%.2f", value)`. This guarantees the output is always `150.75` and never `150,75`, whatever the locale of the machine running the code.

## Project structure

```
observer/
├── StockAgent.java          The Observable: stores observers, simulates the market (BigDecimal values), sends notifications
├── StockBrokerAgency.java   A concrete Observer: prints every notification it receives
└── interfaces/
    ├── Observable.java      addObserver(...), removeObserver(...), notifyObservers()
    └── Observer.java        update(String message)
```

## Usage example

```java
StockAgent agent = new StockAgent();

Observer alphaBrokers = new StockBrokerAgency("Alpha Brokers");
Observer zenithInvestments = new StockBrokerAgency("Zenith Investments");

agent.addObserver(alphaBrokers);
agent.addObserver(zenithInvestments);

agent.stockMarketUp(new BigDecimal("150.75"));
agent.stockMarketDown(new BigDecimal("145.50"));

agent.removeObserver(zenithInvestments);
agent.stockMarketUp(new BigDecimal("152.00"));
```

Console output:

```text
Alpha Brokers received notification: Stock market went UP to 150.75
Zenith Investments received notification: Stock market went UP to 150.75
Alpha Brokers received notification: Stock market went DOWN to 145.50
Zenith Investments received notification: Stock market went DOWN to 145.50
Alpha Brokers received notification: Stock market went UP to 152.00
```

Once Zenith Investments has unsubscribed, it no longer receives notifications.

## Tests

The unit tests live in `src/test/java/com/patterns/observer/StockAgentTest.java`. They use **Mockito** to create mock `Observer`s, so each test can check exactly which messages every observer received, without relying on console output.

| Test | What it checks |
|------|----------------|
| `stockMarketUp_withSubscribedObservers_notifiesAllWithUpMessage` | A rise notifies every subscribed observer with the `UP` message. |
| `stockMarketDown_withSubscribedObservers_notifiesAllWithDownMessage` | A fall notifies every subscribed observer with the `DOWN` message. |
| `removeObserver_thenMarketChanges_removedObserverIsNotNotified` | An observer that has unsubscribed receives no further notifications. |

Run all the tests from the project root with:

```bash
mvn test
```

Or run only the Observer tests with:

```bash
mvn test -Dtest=StockAgentTest
```

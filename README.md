# Tasca S3.03 — Design Patterns 2

Java exercises from IT Academy (Java specialisation, Sprint 3) that put classic **design patterns** into practice, following the **SOLID** principles and backed by unit tests.

## Patterns

| Level | Pattern | Type | Exercise | Documentation |
|-------|---------|------|----------|---------------|
| 1 | **Builder** | Creational | Restaurant menus built with a Fluent Builder and progressive interfaces | [builder/README.md](src/main/java/com/patterns/builder/README.md) |
| 2 | **Observer** | Behavioural | Stock market notifications sent from a stock agent to stockbroking agencies | [observer/README.md](src/main/java/com/patterns/observer/README.md) |

Each package has its own README, explaining the pattern, the exercise, the design decisions and how to use the code.

## Tech stack

- **Java 25**
- **Maven**
- **JUnit Jupiter** 6.1.3
- **AssertJ** 3.27.7
- **Mockito** 5.24.0

## Project structure

```
src/
├── main/java/com/patterns/
│   ├── builder/            Level 1 — Builder pattern
│   │   ├── Dish.java
│   │   ├── Menu.java
│   │   ├── MenuBuilder.java
│   │   └── interfaces/     Progressive step interfaces
│   └── observer/           Level 2 — Observer pattern
│       ├── StockAgent.java
│       ├── StockBrokerAgency.java
│       └── interfaces/     Observable and Observer
└── test/java/com/patterns/
    ├── builder/MenuBuilderTest.java
    └── observer/StockAgentTest.java
```

## Getting started

Requirements: JDK 25 and Maven.

```bash
git clone <repository-url>
cd Tasca-S3.03.-Patterns-2

mvn compile        # compile the project
mvn test           # run every test
```

Run the tests for a single pattern:

```bash
mvn test -Dtest=MenuBuilderTest
mvn test -Dtest=StockAgentTest
```

## Licence

This project is released under the MIT Licence. See [LICENSE](LICENSE) for details.

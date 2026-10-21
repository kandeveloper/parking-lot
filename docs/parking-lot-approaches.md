# Parking Lot Approaches

This project includes two ways of modeling a parking lot system:

1. Quick application approach
2. Production-ready approach

## 1) Quick Application Approach

This is the simpler model built around a direct in-memory Java design.

### Purpose

- Good for learning OOP
- Good for coding interviews
- Good for quick demos and validation logic

### Typical components

- `Vehicle`
- `ParkingSlot`
- `ParkingFloor`
- `ParkingLot`
- `ParkingTicket`
- `PricingStrategy`

### Characteristics

- Everything is kept in memory
- No database
- No REST API by default
- Simple object graph
- Easy to understand and debug

### Best use case

- Prototype testing
- Interview design questions
- Local validation of parking rules

### Example behavior

- A car is assigned the first available matching slot
- The slot is marked occupied
- Exit calculates fee based on duration and pricing strategy

### Strengths

- Very fast to build
- Smaller codebase
- Easy to reason about without infrastructure

### Limitations

- Data is lost when app restarts
- No persistence layer
- Not ideal for multi-user production systems
- Harder to scale safely with concurrent requests

---

## 2) Production-Ready Approach

This is a layered Spring Boot architecture designed for real-world usage.

### Purpose

- Real application deployment
- Persistence and data safety
- Validation and API contracts
- Better maintainability and testability

### Architectural layers

- Controller layer
  - Handles HTTP requests and responses
- Service layer
  - Contains business logic
- Repository layer
  - Accesses database via JPA repositories
- Entity layer
  - Maps database tables to Java objects
- DTO layer
  - Defines request and response payloads

### Typical components

- `ParkingController`
- `ParkingService`
- `ParkingSlotRepository`
- `ParkingTicketRepository`
- `ParkingFloorEntity`
- `ParkingSlotEntity`
- `ParkingTicketEntity`
- `VehicleEntity`

### Characteristics

- Uses JPA + database persistence
- Uses validation with `@Valid`
- Has centralized exception handling
- Exposes REST endpoints via Spring Boot
- Supports OpenAPI/Swagger documentation
- More structured for production environments

### Best use case

- Real deployment
- Multi-user environment
- API-driven systems
- Systems with persistence, auditing, and future extensions

### Strengths

- Cleaner separation of concerns
- Better scalability and maintenance
- Data survives restarts when using a real database
- Easier to add testing, security, logging, and monitoring

### Limitations

- More complexity
- Requires database setup
- More code and configuration
- Slower to build initially

---

## When to Use Which

Use the quick approach when:

- building a conceptual solution
- preparing for interviews or coding exercises
- testing logic quickly

Use the production-ready approach when:

- exposing APIs to users or other systems
- storing data permanently
- handling service reliability and maintainability
- working in a multi-user environment

---

## Summary

The quick app is best for understanding core parking-lot logic.
The production-ready app is best for deploying a real system with persistence, API contracts, validation, and maintainability.

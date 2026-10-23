
# Idempotent Background Transaction Processing Service

## Overview
A Spring Boot application that accepts transaction requests,
validates input, stores transactions, and processes them
asynchronously using a scheduled background worker.

## Technology Stack
- Java 21
- Spring Boot
- Spring Data JPA
- H2 in-memory database
- Maven
- Swagger / OpenAPI
- JUnit 5

## Features
- Transaction request validation
- Duplicate transaction ID prevention
- Background transaction processing
- Transaction status tracking
- Simulated processing failures
- Bounded retry handling
- Basic per-account sequence ordering
- Transaction summary and status counts

## Run the Application
1. Ensure Java 21 is installed.
2. Open a terminal in the project root.
3. Run:

   ```bash
   mvnw.cmd spring-boot:run
   ```

## API Documentation
After starting the application, open:

http://localhost:8060/swagger-ui/index.html

## Database
The application uses an H2 in-memory database.

## Processing and Retry
New transactions are saved with RECEIVED status.
The background worker processes eligible transactions.
Simulated failures are retried up to the configured limit,
after which the transaction is marked FAILED.

## Ordering Strategy
The worker checks for earlier RECEIVED transactions
for the same account before processing a later sequence.
This is a basic ordering strategy, not a complete
production-grade ordering guarantee.

## Observability
Application logs and the transaction summary endpoint
provide basic visibility into processing status.

## Production Hardening
- Use a persistent production database.
- Add concurrency-safe idempotency handling.
- Make processing and retries resilient to application restarts.
- Add structured logs, metrics, alerts, and distributed tracing.
- Add automated tests for validation, duplicates, retries,
  and ordering.
- Define a production-grade ordering and recovery strategy.



# Synpulse8 Backend Challenge

## Installation and Setup
1. Clone the repository
   ```
   git@github.com:xz37/synpulse8-transaction-retrieval.git
   ```

2. Build the project using Maven

   ```
   ./mvnw clean package
   ```

3. Build the Docker image
   ```
   docker build -t synpulse-transaction-retrieval:0.0.1 ./consumer
   ```

## Running the Application
   ```
  docker run -p 8080:8080 synpulse-transaction-retrieval:0.0.1
   ```
The Swagger API document will be accessible at http://localhost:8080/swagger-ui/index.html.

## Project Structure
The project contains 2 modules:
- ``./common`` module

  Contains common classes and models used throughout the project (e.g. `Account`, `Transaction`). 

  - Specifically, `TransactionPage` class represents a paginated list of transactions, where the attribute `pageSize` indicates the assigned maximum number of the transactions per page, and the attribute `currentPage` indicates the assigned current page number.

- ``./consumer`` module
  - ``./config``: Includes configuration classes for the project. 
  - ``./kafka``: Contains the Kafka listener implementation for consuming transactions. 
  - ``./service``: Provides service classes for handling transactions. 
  - ``./web``: Contains the REST API controllers for handling HTTP requests. 
  - `./ConsumerApplication` class: The entry point for the application.

## Testing
Unit tests, integration tests, and API contract tests can be executed using the following command:
```
./mvnw clean test
```

## Continuous Integration
The project is integrated with CircleCI for continuous integration.

## Additional Notes
- The project uses an in-memory ConcurrentHashMap (configured in ``MapConfig.java``) to simulate the data storage for transactions. All transactions are assumed within  currency CHF. 
- Security, authentication, and authorization aspects are not implemented in this project.

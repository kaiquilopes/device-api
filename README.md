# Device API
This project is a Spring Boot application that provides RESTful endpoints for managing devices. It uses MySQL as the database, managed via Docker. The application is built with Java 21, Maven 3.9.9, and includes Swagger for API documentation. It follows clean architecture principles, SOLID principles, and uses design patterns like the Strategy pattern.
___
## Table of Contents

   - [Project Setup](#project-setup)
   - [Docker](#docker)
   - [API Endpoints](#api-endpoints)
   - [Swagger Documentation](#swagger-documentation)
   - [MySQL Database](#mysql-database)
   - [Design Patterns](#design-patterns)
   - [SOLID Principles](#solid-principles)
   - [Customization](#customization)
   - [Contributors](#contributors) 
___
## Project Setup
### Prerequisites
Before running the project, ensure the following tools are installed:
   - Docker + Docker Compose: [Install Docker](https://docs.docker.com/get-started/get-docker/)

### Running the Application
#### 1. Clone the Repository:
   ```sh
   git clone https://github.com/kaiquilopes/device-api.git
   cd device-api
   ```

#### 2. Build the Application:
   ```sh
   mvn clean package
   ```

#### 3. Run with Docker Compose:
   ```sh
  docker compose up --build
   ```

#### 4. Access the Application:
   - **API:** http://localhost:8080/api
   - **MySQL:** localhost:3306
      * **Username:** root
      * **Password:** q1w2e3r4t5
      * **Database:** world_devices

#### 5. Access MySQL Shell:
   ```sh
  docker compose exec db mysql -uroot -pq1w2e3r4t5 world_devices
   ```

## Docker
The project uses Docker for containerization. 
The Dockerfile and docker-compose.yml files define the setup:

## API Endpoints

- **Create a new device.**
    ```http
    POST /api/devices
    ```
- **Update an existing device by ID**
    ```http
    PUT /api/devices/{id}
    ```
- **Update the state of a device by ID**
    ```http
    PUT /api/devices/change-state/{id}
    ```
- **Retrieve a device by ID**
    ```http
    GET /api/devices/{id}
    ```
- **Retrieve all devices or with optional filters**
    ```http
    GET /api/devices
    ```
- **Delete a device by ID**
    ```http
    DELETE /api/devices/{id}
    ```

## Swagger Documentation
The API documentation is available through Swagger. 
1. Run the application.
2. You can access it at the following URL:
[Swagger UI](http://localhost:8080/api/swagger-ui/index.html)

## MySQL Database
The project uses MySQL as the database. The `application.properties` file defines the database configuration

## Design Patterns
### Strategy Pattern
The Strategy pattern is used to manage device state behavior in a flexible and extensible way. Each state (AVAILABLE, IN_USE, INACTIVE) has its own strategy implementation that defines what actions are allowed (canUpdate, canDelete) and how updates are handled (handleUpdate).

#### Key Components:
1. Strategy Interface (StateBehaviorStrategy):
   * Defines the contract for device state behavior.
2. Concrete Strategies:
   * AvailableStateBehavior: Handles behavior for devices in the AVAILABLE state.
   * InUseStateBehavior: Handles behavior for devices in the IN_USE state.
   * InactiveStateBehavior: Handles behavior for devices in the INACTIVE state.
3. Context (StateBehaviorContext):
   * Manages the current strategy based on the device's state.

## SOLID Principles
The project adheres to the SOLID principles, ensuring a clean, maintainable, and scalable design:

### 1. Single Responsibility Principle (SRP)
- Each class has a single responsibility:
- `DeviceController`: Handles HTTP requests.
- `DeviceService`: Manages business logic.
- `DeviceRepository`: Handles database operations.
- `StateBehaviorStrategy`: Defines state-specific behavior.

### 2. Open/Closed Principle (OCP)
- The system is open for extension but closed for modification:
- New device states can be added by implementing the StateBehaviorStrategy interface without modifying existing code.

### 3. Liskov Substitution Principle (LSP)
- Subtypes (concrete strategies) are substitutable for their base type (StateBehaviorStrategy):
- Any state behavior can be used interchangeably in the StateBehaviorContext.

### 4. Interface Segregation Principle (ISP)
- Interfaces are specific to their clients:
- `IDeviceControllerOpenApi`: Provides only the methods needed for Swagger documentation.
- `IDeviceService`: Defines only the methods required for device management.

### 5. Dependency Inversion Principle (DIP)
- High-level modules depend on abstractions, not concrete implementations:
- `DeviceService` depends on the `IDeviceService` interface.
- `StateBehaviorContext` depends on the `StateBehaviorStrategy` interface.

## Customization
Adding New Features
1. **Add a New Entity:**
   - Create a new `@Entity` class in the `core.domain` package.
   - Add a corresponding `Repository` interface in the `core.repository` package.
   - Create a `Service` and `Controller` class to expose endpoints.

2. **Modify the Database Schema:**
   - Update the `world_devices.sql` file with the required schema changes.
   
3. **Extend the API:**
   - Add new endpoints in the `Controller` class.
   - Update the `Swagger` annotations for documentation.

4. **Add New States:**
   - Add a new enum value in `StateDeviceEnum`.
   - Create a new strategy implementation in the `core.strategy` package.
   - Register the strategy in the `StateBehaviorContext` and `StrategyConfig`.


## Contributors
### - Kaiqui Lopes

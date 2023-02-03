<h1>
  🐒 Java solution to the Agile Monkeys challenge
</h1>

<p style="text-align:center;">
  <a href="https://github.com/santigamo/monkeys-challenge/actions/workflows/maven.yml"><img src="https://github.com/santigamo/monkeys-challenge/actions/workflows/maven.yml/badge.svg" alt="Build & Test" /></a>
  <a href="#"><img src="https://img.shields.io/static/v1?label=JDK&message=17&color=blueviolet" alt="JDK 17"/></a>
</p>

## 👀 Overview
The project follows a clean architecture, with the application layer interacting with the external system through a data provider interface. This allows the application layer to be isolated from the implementation details of the external system and makes it easier to test and maintain the code.

## 🤖 Technologies
- [Java 18](https://openjdk.java.net/projects/jdk/18/): The backend system is implemented using Java 17, the latest stable available versions of this language, which provides us with different features that we take advantage of in the API implementation.
- [Spring boot](https://spring.io/projects/spring-boot): The system uses Spring Boot as the web framework to handle HTTP requests and responses.
- [Docker](https://www.docker.com/): Docker is used to containerize the application and its dependencies.
- [Auth0](https://auth0.com/): Auth0 is used as provider to manage the authentication and authorization of the application.



## ☝️ How to run this project
### ️Make
1. Install `make` on your computer, if you do not already have it.
2. Start the application: `make up`
3. Run the application tests: `make test`

### Maven
1. Open a terminal and go to the project folder.
2. Start the application: `mvn spring-boot:run`

### Docker
1. Install docker on your computer, if you do not already have it.
2. Open a terminal and go to the project folder.
3. Build the image: `docker build -t java-monkey-api .`

### Docker compose
1. Install docker and docker-compose on your computer, if you do not already have it.
2. Start the application: `docker-compose up`
3. Execute `docker compose run -d -p "8080:8080" java-skeleton-api gradle clean build bootRun -x test`

## 🎯 API Calls
- `GET /actuator/health` - Health check
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by id
- `POST /api/customers` - Create customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

## 👽 Technical Details
### 📚 Dependencies
- [Lombok](https://projectlombok.org/): Used to reduce boilerplate code in the project.
- [PostgresSQL](https://www.postgresql.org/): The driver to connect with the database used in the project.
- [H2](https://www.h2database.com/): in-memory database used for testing.
- [Flyway](https://flywaydb.org/): Used to initialize the tables and manage database migrations.

### 🏗️ Architecture
- Clean Architecture: In the project we have always maintained a clean architecture, giving great importance to not coupling the domain and application layers to any framework. Pushing these needs to the infrastructure layer.
- SOLID: We have always tried to follow the SOLID principles, which are the basis of good software development.

### ✨ Implementation highlights
- In the infrastructure structure layer we have the [configuration package](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration) where we can find:
    - The [DatabaseConfig](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration/DatabaseConfig.java): which is responsible for configuring the database connection.
    - The [DependencyInjectionConfig](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration/DependencyInjectionConfig.java): which is responsible for configuring the dependency injection of the project.
- [application.yml](src/main/resources/application.properties)
    - `server.shutdown = graceful`: This property is responsible for allowing the application to finish the current requests before shutting down.
- [DB Migration folder](src/main/resources/db/migration): This folder contains the scripts that are executed when the application starts. Are responsible for creating the tables in the database if they do not exist and migrating the database if necessary.
- Exception are defined individually in the [exception package](src/main/java/com/monkeys/challenge/customer/exceptions) and are managed by the [ControllerAdvisor](src/main/java/com/monkeys/challenge/customer/infrastructure/exceptions/ControllerAdvisor.java) to keep a simple but ordered and powerful error handling.

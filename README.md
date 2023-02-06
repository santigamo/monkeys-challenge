<h1  align="center">
  🐒 Java solution to the Agile Monkeys challenge
</h1>

<p align="center">
  <a href="https://github.com/santigamo/monkeys-challenge/actions/workflows/maven.yml"><img src="https://github.com/santigamo/monkeys-challenge/actions/workflows/maven.yml/badge.svg" alt="Build & Test" /></a>
  <a href="https://github.com/santigamo/monkeys-challenge/actions/workflows/codeql.yml"><img src="https://github.com/santigamo/monkeys-challenge/actions/workflows/codeql.yml/badge.svg" alt="Analyze vulnerabilities" /></a>
  <a href="#"><img src="https://img.shields.io/static/v1?label=Java SE&message=17(TLS)&color=blueviolet&logo=OpenJDK" alt="JDK 17"/></a>
</p>

## 👀 Overview
The project follows a clean architecture, with the application layer interacting with the external system through a data provider interface. This allows the application layer to be isolated from the implementation details of the external system and makes it easier to test and maintain the code.

## 🤖 Technologies
- [Java 17](https://openjdk.java.net/projects/jdk/17/): The backend system is implemented using Java 17, the latest stable available versions of this language, which provides us with different features that we take advantage of in the API implementation.
- [Spring boot](https://spring.io/projects/spring-boot): The system uses Spring Boot as the web framework to handle HTTP requests and responses.
- [Docker](https://www.docker.com/): Docker is used to containerize the application and its dependencies.
- [Auth0](https://auth0.com/): Auth0 is used as provider to manage the authentication and authorization of the application.
- [OpenAPI 3](https://swagger.io/specification/): OpenAPI is used to generate the API documentation.

## ☝️ How to run this project

### Maven | ⚠️ A valid DB should be configured
1. Open a terminal and go to the project folder.
2. Start the application: `mvn spring-boot:run`

### Docker  | ⚠️ A valid DB should be configured
1. Install docker on your computer, if you do not already have it.
2. Open a terminal and go to the project folder.
3. Build the image: `docker build -t java-monkey-api .`
4. Start the application: `docker run -p 8080:8080 java-monkey-api`

### Docker compose | ✅ DB configuration included
1. Install docker and docker-compose on your computer, if you do not already have it.
2. Start the application: `docker-compose up`

Go to `http://localhost:8080/actuator/health` to see that everything is up & running!

## 🎯 API Documentation
ℹ️ **_To simplify the initial use of the API, a user with administrator permissions has been provided:_**
- Username: `test@email.com`
- Password: `Password!`

### 📢 Public endpoints
- `GET /actuator/health` - Health check
- `POST /login` - Login
- `GET /swagger-ui.html` - Swagger UI
- `GET /v3/api-docs` - OpenAPI specification

### 🔒 Admin permissions
- `GET /users` - List all users
- `POST /users` - Create new user
- `DELETE /users/{id}` - Remove a user
- `PATCH /users/{id}` - Update a user
- `GET /users/{id}/admin` - Change admin status

### 🧍 Customer permissions
- `GET /image/upload` - Upload an image
- `GET /customers` - List all customers
- `POST /customers` - Create customer
- `GET /customers/{id}` - Get customer by id
- `DELETE /customers/{id}` - Delete customer
- `PATCH /customers/{id}` - Update customer

### All the API calls are documented using OpenAPI
Once the app is running, you can access to the swagger UI by going to the following URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html), additionally you can get the OpenAPI 
specification in the following URL: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) or download it in yaml format: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

### Postman Collection
Attached with the project is a [Postman collection](Monkey%20Challenge.postman_collection.json) with all the API calls to easily test the application.

## 👽 Technical Details
### 📚 Dependencies
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html): Used to expose the health check endpoint.
- [Lombok](https://projectlombok.org/): Used to reduce boilerplate code in the project.
- [PostgresSQL](https://www.postgresql.org/): The driver to connect with the database used in the project.
- [H2](https://www.h2database.com/): in-memory database used for testing.
- [Flyway](https://flywaydb.org/): Used to initialize the tables and manage database migrations.
- [OpenAPI](https://swagger.io/specification/): Used to generate the API documentation.
- [Spring Doc OpenAPI](https://springdoc.org/): Used to generate the API documentation.

### 🏗️ Architecture
- Clean Architecture: In the project we have always maintained a clean architecture, giving great importance to not coupling the domain and application layers to any framework. Pushing these needs to the infrastructure layer.
- SOLID: We have always tried to follow the SOLID principles, which are the basis of good software development.

### ✨ Implementation highlights
- The application has been organized into **3 main packages**. Two domain bundles have been created, `customer` and `admin` to separate all its domain classes and behavior. In parallel. we have created a `shared` package where the classes that will be used by all the domains are added.
- In the infrastructure structure layer we have the [configuration package](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration) where we can find:
    - The [DatabaseConfig](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration/DatabaseConfig.java): which is responsible for configuring the database connection.
    - The [DependencyInjectionConfig](src/main/java/com/monkeys/challenge/customer/infrastructure/configuration/DependencyInjectionConfig.java): which is responsible for configuring the dependency injection of the domain bundle.
- [application.yml](src/main/resources/application.properties)
    - `server.shutdown = graceful`: This property is responsible for allowing the application to finish the current requests before shutting down.
- [DB Migration folder](src/main/resources/db/migration): This folder contains the scripts that are executed when the application starts. Are responsible for creating the tables in the database if they do not exist and migrating the database if necessary.
- Exception are defined individually in the [exception package](src/main/java/com/monkeys/challenge/customer/infrastructure/exceptions) and are managed by the [ControllerAdvisor](src/main/java/com/monkeys/challenge/customer/infrastructure/exceptions/CustomerControllerAdvisor.java) to keep a simple but ordered and powerful error handling.
- Testing: The project has a current testing coverage of 90% of the lines of code. Could be increased, finishing the configuration of the classes that should be excluded from the test.
  - Unit Tests: All the unit tests classes are tagged with the `@Tag("unit-test")` annotation to be able to execute them separately from the integration tests.
  - Integration Tests: All the integration tests classes are tagged with the `@Tag("integration-test")` annotation to be able to execute them separately from the unit tests.
    

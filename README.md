# Employee Bonus Management System

This project implements a RESTful API for managing employee bonuses based on Spring Boot. It includes functionalities for saving employee data with bonuses and retrieving filtered employee bonuses based on a specified date.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Mockito (for unit testing)
- JUnit (for unit testing)
- Maven/Gradle (build tool)
- MySql Database (in-memory database for testing)

## Project Structure

The project consists of the following main components:

- **EmployeeBonusController**: REST controller managing endpoints for saving employee bonuses (`POST /tci/employee-bonus`) and retrieving filtered employee bonuses (`GET /tci/employee-bonus`).
  
- **EmployeeRepository**: Interface extending Spring Data JPA's `JpaRepository` for CRUD operations on Employee entities.

- **LowercaseMonthDateFormatter**: Custom Spring `Formatter` for deserializing date strings with lowercase month names (e.g., "Jun-15-2024").

- **CustomLocalDateDeserializer**: Custom Jackson `JsonDeserializer` for deserializing date strings with capitalized month names.

- **EmployeeBonusControllerTest**: Unit tests using Mockito and Spring's `MockMvc` for testing `EmployeeBonusController` endpoints.

## Setup Instructions

To run the project locally, follow these steps:

1. Clone the repository from GitHub.
2. Ensure you have JDK 8+ and Maven or Gradle installed.
3. Navigate to the project directory and build using Maven (`mvn clean install`) or Gradle (`gradlew build`).
4. Run the application using Maven (`mvn spring-boot:run`) or Gradle (`gradlew bootRun`).
5. The application will start at `http://localhost:8080`.

## Testing

Unit tests are implemented for `EmployeeBonusController`, `CustomLocalDateDeserializerTest` and `LowercaseMonthDateFormatterTest` using Mockito and Spring's `MockMvc`. To run the tests:

- Execute `mvn test`, `gradlew.bat test` or `gradlew test` from the project directory.
- Test results will be displayed in the console.

## API Endpoints

### Save Employee Bonus

- **Endpoint**: `POST /tci/employee-bonus`
- **Request Body**: JSON object with `employees` field containing a list of Employee objects.
- **Response**: HTTP status `201 Created` if successful.

### Get Employee Bonus

- **Endpoint**: `GET /tci/employee-bonus`
- **Request Parameter**: `date` (format: "MMM-dd-yyyy")
- **Response**: JSON array containing objects grouped by currency with employees and their bonus amounts.


Example response:
```json
[
  {
    "currency": "USD",
    "employees": [
      {"empName": "John Doe", "amount": 1000.0},
      {"empName": "Jane Smith", "amount": 1500.0}
    ]
  },
  {
    "currency": "GBP",
    "employees": [
      {"empName": "Alice Brown", "amount": 2000.0}
    ]
  }
]

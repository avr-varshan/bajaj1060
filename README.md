# Bajaj Finserv Health Qualifier 1 - Spring Boot Application

This is a Spring Boot application developed for the Bajaj Finserv Health Qualifier 1 assessment.

## Author
**Name:** Varshan AVR
**Reg No:** 22BRS1060
**Email:** avr.varshan20@gmail.com

## Description
The application automates the following flow on startup:
1.  **Generate Webhook**: Sends a POST request to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA` to obtain a unique webhook URL and access token.
2.  **Solve SQL Problem**: Constructs a SQL query to solve the assigned problem (Question 2 for even registration numbers).
    *   **Problem**: Calculate the average age of employees with salary > 70,000 per department and list up to 10 names.
3.  **Submit Solution**: Sends the constructed SQL query to the received webhook URL using the access token.

## Prerequisites
-   Java 17 or higher
-   Maven

## How to Build
To build the project and generate the JAR file:
```bash
mvn clean package
```

## How to Run
To run the application:
```bash
java -jar bajaj-health.jar
```
*Note: The application uses port 0 (random port) to avoid conflicts if port 8080 is busy.*

## Project Structure
-   `src/main/java/com/bajaj/health/BajajHealthApplication.java`: Main entry point.
-   `src/main/java/com/bajaj/health/service/ApiClient.java`: Handles API interactions.
-   `src/main/java/com/bajaj/health/model`: Contains DTOs (`WebhookRequest`, `WebhookResponse`, `SolutionRequest`).

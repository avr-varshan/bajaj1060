package com.bajaj.health;

import com.bajaj.health.model.WebhookResponse;
import com.bajaj.health.service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BajajHealthApplication implements CommandLineRunner {

    @Autowired
    private ApiClient apiClient;

    public static void main(String[] args) {
        SpringApplication.run(BajajHealthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Bajaj Health Qualifier 1 Application...");

        try {
            // 1. Generate Webhook
            System.out.println("Generating Webhook...");
            WebhookResponse webhookResponse = apiClient.generateWebhook();

            if (webhookResponse == null) {
                System.err.println("Failed to generate webhook. Response is null.");
                return;
            }

            String webhookUrl = webhookResponse.getWebhook();
            String accessToken = webhookResponse.getAccessToken();

            System.out.println("Webhook URL: " + webhookUrl);
            System.out.println("Access Token received.");

            // 2. Construct SQL Query
            // Question 2: Odd -> Question 1, Even -> Question 2. 22BRS1060 ends in 60
            // (Even).
            // Problem: Average age of employees with salary > 70000 per department, list up
            // to 10 names.
            String sqlQuery = "SELECT " +
                    "d.DEPARTMENT_NAME, " +
                    "AVG(TIMESTAMPDIFF(YEAR, e.DOB, CURDATE())) AS AVERAGE_AGE, " +
                    "SUBSTRING_INDEX(GROUP_CONCAT(CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) SEPARATOR ', '), ', ', 10) AS EMPLOYEE_LIST "
                    +
                    "FROM EMPLOYEE e " +
                    "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                    "JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID " +
                    "WHERE p.AMOUNT > 70000 " +
                    "GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME " +
                    "ORDER BY d.DEPARTMENT_ID DESC;";

            System.out.println("SQL Query constructed.");

            // 3. Submit Solution
            System.out.println("Submitting solution...");
            apiClient.submitSolution(webhookUrl, accessToken, sqlQuery);

            System.out.println("Process completed successfully.");
            System.exit(0);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

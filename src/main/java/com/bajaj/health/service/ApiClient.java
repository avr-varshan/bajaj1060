package com.bajaj.health.service;

import com.bajaj.health.model.SolutionRequest;
import com.bajaj.health.model.WebhookRequest;
import com.bajaj.health.model.WebhookResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    public WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest("Varshan AVR", "22BRS1060", "avr.varshan20@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(GENERATE_WEBHOOK_URL, entity,
                WebhookResponse.class);
        return response.getBody();
    }

    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        SolutionRequest request = new SolutionRequest(finalQuery);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);
            System.out.println("Submission Response Code: " + response.getStatusCode());
            System.out.println("Submission Response Body: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error submitting solution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

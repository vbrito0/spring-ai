package com.movies.ai.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.model}")
    private String model;

    private static final String OPEN_AI_URL = "https://api.openai.com/v1/chat/completions";

    public String getResponseFromAi(String message) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHeaders();
        String requestBody = createRequestBody(message);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(OPEN_AI_URL, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private String createRequestBody(String message) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", new JSONObject[] {
            new JSONObject().put("role", "user").put("content", message)
        });
        return requestBody.toString();
    }
}

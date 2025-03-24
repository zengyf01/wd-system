package com.example.qa.service;

import com.example.qa.config.WanxiangConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WanxiangService {
    private static final Logger logger = LoggerFactory.getLogger(WanxiangService.class);
    private final WanxiangConfig wanxiangConfig;
    private final RestTemplate restTemplate;

    public WanxiangService(WanxiangConfig wanxiangConfig) {
        this.wanxiangConfig = wanxiangConfig;
        this.restTemplate = new RestTemplate();
    }

    public String generateImage(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            logger.warn("Received empty or null prompt");
            throw new IllegalArgumentException("Prompt cannot be null or empty");
        }

        String trimmedPrompt = prompt.trim();
        logger.info("Processing image generation prompt: {}", trimmedPrompt);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + wanxiangConfig.getApiKey());

            Map<String, Object> input = new HashMap<>();
            input.put("model", "wanx-v1");
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("style", "<auto>");
            parameters.put("size", "1024*1024");
            parameters.put("n", 1);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", input);
            requestBody.put("parameters", parameters);
            requestBody.put("prompt", trimmedPrompt);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            Map<String, Object> response = restTemplate.postForObject(
                wanxiangConfig.getApiUrl(),
                request,
                Map.class
            );

            if (response != null && response.containsKey("output")) {
                Map<String, Object> output = (Map<String, Object>) response.get("output");
                if (output.containsKey("results")) {
                    Object[] results = (Object[]) output.get("results");
                    if (results.length > 0) {
                        Map<String, String> result = (Map<String, String>) results[0];
                        return result.get("url");
                    }
                }
            }

            logger.error("Invalid response format from Wanxiang API");
            throw new RuntimeException("Failed to generate image: Invalid response format");

        } catch (Exception e) {
            logger.error("Error while generating image: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate image: " + e.getMessage(), e);
        }
    }
}
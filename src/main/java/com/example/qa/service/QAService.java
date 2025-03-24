package com.example.qa.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.output.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QAService {
    private static final Logger logger = LoggerFactory.getLogger(QAService.class);
    private final ChatLanguageModel model;

    public QAService(ChatLanguageModel model) {
        this.model = model;
    }

    public String ask(String question) throws IllegalArgumentException {
        if (question == null || question.trim().isEmpty()) {
            logger.warn("Received empty or null question");
            throw new IllegalArgumentException("Question cannot be null or empty");
        }

        String trimmedQuestion = question.trim();
        logger.info("Processing question: {}", trimmedQuestion);

        try {
            String msg = model.generate(trimmedQuestion);
            Response response = Response.from(msg);
            validateResponse(response);
            
            String content = response.content().toString();
            validateContent(content);
            
            logger.info("Generated response successfully, length: {}, first 50 chars: {}", 
                content.length(), 
                content.length() > 50 ? content.substring(0, 50) + "..." : content);
            return content;

        } catch (IllegalStateException e) {
            logger.error("Invalid response state: {}", e.getMessage());
            throw new RuntimeException("Failed to process response: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while processing question: {}", trimmedQuestion, e);
            throw new RuntimeException("Failed to process question: " + e.getMessage(), e);
        }
    }

    private void validateResponse(Response<String> response) {
        if (response == null) {
            logger.error("Received null response from model");
            throw new IllegalStateException("Model returned null response");
        }
    }

    private void validateContent(String content) {
        if (content == null) {
            logger.error("Received null content from response");
            throw new IllegalStateException("Response content is null");
        }
        if (content.isEmpty()) {
            logger.warn("Received empty content from response");
            throw new IllegalStateException("Response content is empty");
        }
    }
    }

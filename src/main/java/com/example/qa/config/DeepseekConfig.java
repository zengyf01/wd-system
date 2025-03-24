package com.example.qa.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepseekConfig {
    private static final Logger logger = LoggerFactory.getLogger(DeepseekConfig.class);
    private static final String DEFAULT_MODEL = "deepseek-r1";
    private static final double DEFAULT_TEMPERATURE = 0.7;
    private static final int DEFAULT_MAX_TOKENS = 2000;
    private static final double DEFAULT_TOP_P = 1.0;
    private static final double DEFAULT_FREQUENCY_PENALTY = 0.0;
    private static final double DEFAULT_PRESENCE_PENALTY = 0.0;

    public static ChatLanguageModel createDeepseekModel(String apiKey, String baseUrl) {
        return createDeepseekModel(apiKey, baseUrl, DEFAULT_MODEL, DEFAULT_TEMPERATURE);
    }

    public static ChatLanguageModel createDeepseekModel(String apiKey, String baseUrl, String modelName, double temperature) {
        return createDeepseekModel(apiKey, baseUrl, modelName, temperature, DEFAULT_MAX_TOKENS, DEFAULT_TOP_P, DEFAULT_FREQUENCY_PENALTY, DEFAULT_PRESENCE_PENALTY);
    }

    public static ChatLanguageModel createDeepseekModel(
            String apiKey,
            String baseUrl,
            String modelName,
            double temperature,
            int maxTokens,
            double topP,
            double frequencyPenalty,
            double presencePenalty) {
        
        logger.info("Initializing Deepseek model with custom parameters: model={}, temperature={}, maxTokens={}", 
            modelName, temperature, maxTokens);

        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .topP(topP)
                .frequencyPenalty(frequencyPenalty)
                .presencePenalty(presencePenalty)
                .build();
    }
}
package com.example.qa;

import com.example.qa.config.DeepseekConfig;
import com.example.qa.service.QAService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.base.url}")
    private String baseUrl;

    @Value("${deepseek.model.name:deepseek-v3}")
    private String modelName;

    @Value("${deepseek.temperature:0.7}")
    private double temperature;

    @Value("${deepseek.max-tokens:2000}")
    private int maxTokens;

    @Value("${deepseek.top-p:1.0}")
    private double topP;

    @Value("${deepseek.frequency-penalty:0.0}")
    private double frequencyPenalty;

    @Value("${deepseek.presence-penalty:0.0}")
    private double presencePenalty;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return DeepseekConfig.createDeepseekModel(
            apiKey,
            baseUrl,
            modelName,
            temperature,
            maxTokens,
            topP,
            frequencyPenalty,
            presencePenalty
        );
    }

    @Bean
    public QAService qaService(ChatLanguageModel model) {
        return new QAService(model);
    }
}
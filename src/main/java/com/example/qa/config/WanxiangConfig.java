package com.example.qa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WanxiangConfig {
    @Value("${wanxiang.api.key:sk-2e145196d98a4befb7ab313110ce7825}")
    private String apiKey;

    @Value("${wanxiang.api.url:https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
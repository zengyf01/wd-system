package com.example.qa;

import com.example.qa.config.DeepseekConfig;
import com.example.qa.service.QAService;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class Main {
    public static void main(String[] args) {
        // 替换为你的 Deepseek API Key 和 Base URL
        String apiKey = "sk-2e145196d98a4befb7ab313110ce7825";
        String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";

        if (apiKey == null || baseUrl == null) {
            System.err.println("请设置环境变量 DEEPSEEK_API_KEY 和 DEEPSEEK_API_BASE_URL");
            System.exit(1);
        }

        try {
            // 初始化 Deepseek 模型
            ChatLanguageModel model = DeepseekConfig.createDeepseekModel(apiKey, baseUrl);
            QAService qaService = new QAService(model);

            // 示例问题
            String question = "你好，请介绍一下自己。";
            System.out.println("问题: " + question);

            // 获取回答
            String answer = qaService.ask(question);
            System.out.println("回答: " + answer);

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
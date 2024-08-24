package com.brightkut.aroundtheworld.config;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfig {
    @Bean
    OpenAiEmbeddingModel embedding() {
        var openAiApi = new OpenAiApi(System.getenv("SPRING_AI_OPENAI_API_KEY"));

        return new OpenAiEmbeddingModel(
                openAiApi,
                MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder()
                        .withModel("text-embedding-3-small")
                        .build(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }
}

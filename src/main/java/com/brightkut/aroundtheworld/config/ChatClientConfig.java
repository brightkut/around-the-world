package com.brightkut.aroundtheworld.config;

import com.brightkut.aroundtheworld.constants.MessageConstant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    // Config chat client with the system message
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(MessageConstant.SYSTEM_MESSAGE)
                //TODO create chat history and ask vector store

//                .defaultAdvisors(
//                        new PromptChatMemoryAdvisor(chatMemory),
//                        // new MessageChatMemoryAdvisor(chatMemory), // CHAT MEMORY
//                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()),
//                        new LoggingAdvisor()) // RAG
                .build();
    }
}

package com.brightkut.aroundtheworld.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.brightkut.aroundtheworld.constants.MessageConstant.SYS_MESSAGE;

@Configuration
public class ChatClientConfig {

    private static final Log log = LogFactory.getLog(ChatClientConfig.class);
    private final VectorStore vectorStore;

    public ChatClientConfig(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Config chat client with the system message
    @Bean
    ChatClient chatClient() {
        OpenAiApi openAiApi = new OpenAiApi(System.getenv("SPRING_AI_OPENAI_API_KEY"));

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4)
                .withMaxTokens(200)
                .build();

        // Create a chat model for gpt
        ChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);

        // Define your custom search logic here
        var customAdvisor = new QuestionAnswerAdvisor(
                vectorStore,
                SearchRequest
                        .defaults()
                            .withTopK(5)
        );

        InMemoryChatMemory inMemoryChatMemory = new InMemoryChatMemory();

        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SYS_MESSAGE)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(inMemoryChatMemory),
//                         new VectorStoreChatMemoryAdvisor(vectorStore, "1",100), // CHAT MEMORY
                        customAdvisor
                )
//                        new LoggingAdvisor())
                .build();
    }
}

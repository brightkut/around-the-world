package com.brightkut.aroundtheworld.config;

import com.brightkut.aroundtheworld.constants.MessageConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private static final Log log = LogFactory.getLog(ChatClientConfig.class);
    private final VectorStore vectorStore;

    public ChatClientConfig(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Config chat client with the system message
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        OpenAiApi openAiApi = new OpenAiApi(System.getenv("SPRING_AI_OPENAI_API_KEY"));

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4)
                .withMaxTokens(200)
                .build();

        // Create a chat model for gpt
        ChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);

        // Define your custom search logic here
        var customAdvisor = new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
                .withTopK(5)
                .withSimilarityThreshold(0.5));

        return ChatClient.builder(openAiChatModel)
                // add a default system message
                .defaultSystem(MessageConstant.SYSTEM_MESSAGE)
                //TODO create chat history and ask vector store
                .defaultAdvisors(
//                        new PromptChatMemoryAdvisor(chatMemory),
                        // new MessageChatMemoryAdvisor(chatMemory), // CHAT MEMORY
                        customAdvisor)
//                        new LoggingAdvisor()) // RAG
                .build();
    }
}

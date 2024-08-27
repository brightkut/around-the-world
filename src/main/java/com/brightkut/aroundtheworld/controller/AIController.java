package com.brightkut.aroundtheworld.controller;

import com.brightkut.aroundtheworld.model.ChatRequest;
import com.brightkut.aroundtheworld.model.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@RequestMapping("/ai-chat")
public class AIController {

    private final ChatClient chatClient;

    public AIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chatWithAI(@RequestBody ChatRequest request) {
        var chatResponse = chatClient.prompt()
                .user(request.message())
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, request.chatId())
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                )
                .call()
                .chatResponse();

        return ResponseEntity.ok(new ChatResponse(chatResponse.getResult().getOutput().getContent()));
    }
}

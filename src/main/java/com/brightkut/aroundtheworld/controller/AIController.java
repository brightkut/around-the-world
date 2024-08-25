package com.brightkut.aroundtheworld.controller;

import com.brightkut.aroundtheworld.model.ChatRequest;
import com.brightkut.aroundtheworld.model.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai-chat")
public class AIController {

    private final ChatClient chatClient;

    public AIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chatWithAI(@RequestBody ChatRequest request) {

        ChatResponse res = chatClient.prompt()
                .user(request.message())
                .call()
                .entity(ChatResponse.class);

        return ResponseEntity.ok(res);
    }
}

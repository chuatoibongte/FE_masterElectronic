package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.chatbot.ChatbotTextRequest;
import com.example.mater_electronic.models.chatbot.ChatbotTextResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.chatbot.ChatbotService;

import retrofit2.Callback;

public class ChatbotRespository {
    private ChatbotService api = ApiClient.getChatbotService();
    public void getTextChatbot(ChatbotTextRequest request, Callback<ChatbotTextResponse> callback) {
        api.getTextChatbot(request).enqueue(callback);
    }
}

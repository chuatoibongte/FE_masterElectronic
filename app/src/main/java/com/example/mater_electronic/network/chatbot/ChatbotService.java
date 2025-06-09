package com.example.mater_electronic.network.chatbot;

import com.example.mater_electronic.models.chatbot.ChatbotTextRequest;
import com.example.mater_electronic.models.chatbot.ChatbotTextResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatbotService {
    //API lấy câu trả lời khi gửi dạng text
    @POST("user/chatbot/searchElec")
    Call<ChatbotTextResponse> getTextChatbot(@Body ChatbotTextRequest request);
}

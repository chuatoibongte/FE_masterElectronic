package com.example.mater_electronic.network.chatbot;

import com.example.mater_electronic.models.chatbot.ChatbotImgResponse;
import com.example.mater_electronic.models.chatbot.ChatbotTextRequest;
import com.example.mater_electronic.models.chatbot.ChatbotTextResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ChatbotService {
    //API lấy câu trả lời khi gửi dạng text
    @POST("user/chatbot/searchElec")
    Call<ChatbotTextResponse> getTextChatbot(@Body ChatbotTextRequest request);
    //API lấy câu trả lời khi gửi dạng ảnh
    @Multipart
    @POST("user/chatbot/searchSimilarImgs")
    Call<ChatbotImgResponse> getImgChatbot(
            @Part MultipartBody.Part file
    );
}

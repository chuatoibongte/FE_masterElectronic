package com.example.mater_electronic.repositories;

import android.net.Uri;
import android.util.Log;

import com.example.mater_electronic.models.chatbot.ChatbotImgResponse;
import com.example.mater_electronic.models.chatbot.ChatbotTextRequest;
import com.example.mater_electronic.models.chatbot.ChatbotTextResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.chatbot.ChatbotService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotRespository {
    private static final String TAG = "ChatbotRepository";
    private ChatbotService chatbotServiceApi = ApiClient.getChatbotService();

    public void getTextChatbot(ChatbotTextRequest request, Callback<ChatbotTextResponse> callback) {
        chatbotServiceApi.getTextChatbot(request).enqueue(callback);
    }

    public void getImgChatbot(Uri imageUri, android.content.Context context, Callback<ChatbotImgResponse> callback) {
        if (imageUri == null) {
            // Tạo response lỗi thay vì throw exception
            callback.onFailure(null, new Exception("Image URI is null"));
            return;
        }

        try {
            File imageFile = com.example.mater_electronic.utils.FileUtils.getFileFromUri(context, imageUri);

            if (imageFile == null || !imageFile.exists()) {
                Log.e(TAG, "Image file is null or does not exist");
                callback.onFailure(null, new Exception("Image file is null or does not exist"));
                return;
            }

            // Tạo RequestBody cho file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

            Log.d(TAG, "Sending image file: " + imageFile.getName() + ", size: " + imageFile.length() + " bytes");

            // Gọi API
            chatbotServiceApi.getImgChatbot(imagePart).enqueue(callback);

        } catch (Exception e) {
            Log.e(TAG, "Error processing image: " + e.getMessage(), e);
            callback.onFailure(null, e);
        }
    }
}
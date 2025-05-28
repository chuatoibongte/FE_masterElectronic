package com.example.mater_electronic.ui.navigation.chabot;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.databinding.FragmentChatbotBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatbotFragment extends Fragment {

    private FragmentChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItems;
    private ChatbotViewModel chatbotProductViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel riêng cho Chatbot
        chatbotProductViewModel = new ViewModelProvider(this).get(ChatbotViewModel.class);

        chatItems = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatItems);

        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChat.setAdapter(chatAdapter);

        // Observe sản phẩm từ ViewModel để hiển thị khi có data
        chatbotProductViewModel.getProductLiveData().observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                // Bot nhắn giới thiệu
                chatItems.add(new ChatItem.TextMessage(false, "Bạn Có Thể Tham Khảo Các Sản Phẩm Sau Đây"));
                chatAdapter.notifyItemInserted(chatItems.size() - 1);
                binding.rvChat.scrollToPosition(chatItems.size() - 1);

                String imgUrl = null;
                if (product.getElectronicImgs() != null && !product.getElectronicImgs().isEmpty()) {
                    imgUrl = product.getElectronicImgs().get(0).getUrl();
                }
                chatItems.add(new ChatItem.ProductMessage(
                        product.get_id(), // Lấy đúng id của product từ API
                        imgUrl,
                        product.getName(),
                        formatPrice(product.getPrice()) + " Đ"
                ));

                chatAdapter.notifyItemInserted(chatItems.size() - 1);
                binding.rvChat.scrollToPosition(chatItems.size() - 1);
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editChatInput.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    chatItems.add(new ChatItem.TextMessage(true, message));
                    chatAdapter.notifyItemInserted(chatItems.size() - 1);
                    binding.rvChat.scrollToPosition(chatItems.size() - 1);
                    binding.editChatInput.setText("");

                    // Call API lấy sản phẩm id cứng
                    binding.rvChat.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatbotProductViewModel.getProductById("6814329cc86355927f0c3bf3");
                        }
                    }, 800);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Hàm format giá
    private String formatPrice(double price) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getInstance(new java.util.Locale("vi", "VN"));
        return formatter.format(price);
    }
}

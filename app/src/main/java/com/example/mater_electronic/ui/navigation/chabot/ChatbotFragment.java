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
import com.example.mater_electronic.models.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ChatbotFragment extends Fragment {

    private FragmentChatbotBinding binding;
    private ChatAdapter chatAdapter;
    private List<ChatItem> chatItems;
    private ChatbotViewModel chatbotViewModel;

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
        chatbotViewModel = new ViewModelProvider(this).get(ChatbotViewModel.class);

        //Tạo chatitems và adapter
        chatItems = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatItems);

        //Setup RecyclerView
        binding.rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChat.setAdapter(chatAdapter);

        //Thêm thông báo chào khách hàng
        addWelcomeMessage();

        chatbotViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            if(products != null && !products.isEmpty()){
                //Bot nhắn giới thiệu
                addBotMessage("Tôi đã tìm thấy " + products.size() + " sản phẩm phù hợp với yêu cầu của bạn: ");

                //Thêm product vào chat
                for(Product product : products){
                    addProductMessage(product);
                }

                //Thêm thông báo gợi ý
                addBotMessage("Bạn có thể nhấn vào sản phẩm để xem chi tiết!");
            }
        });

        //Observe lỗi từ chatbot API
        chatbotViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error ->{
            if(error != null && !error.isEmpty()){
                addBotMessage("Xin lỗi, chúng tôi không tìm thấy sản phẩm. Vui lòng thử lại sau");
            }
        });

        //Xử lý khi nhấn nút gửi tin nhắn
        binding.btnSend.setOnClickListener(v -> {
            String message = binding.editChatInput.getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                //Thêm tin nhắn người dùng vào chat
                addUserMessage(message);

                //Clear input người dùng
                binding.editChatInput.setText("");

                // Hiện thị đang xử lý tin nhắn
                addBotMessage("Đang tìm kiếm...");

                //Gọi API chatbot
                binding.rvChat.postDelayed(() -> {
                    //Xóa dòng đang tìm kiếm
                    removeLastMessage();

                    //Gọi chatbot API
                    chatbotViewModel.getTextChatbot(message);
                }, 800);
            }
        });

    }

    //Hàm chào khách
    private void addWelcomeMessage() {
        addBotMessage("Chào mừng bạn! Tôi là trợ lý mua sắm của bạn. Hãy cho tôi biết bạn đang tìm kiếm sản phẩm gì?");
    }

    //Hàm thêm bot mesage
    private void addBotMessage(String message) {
        chatItems.add(new ChatItem.TextMessage(false, message));
        notifyItemInserted();
    }
    //Hàm thêm user message
    private void addUserMessage(String message) {
        chatItems.add(new ChatItem.TextMessage(true, message));
        notifyItemInserted();
    }

    //Hàm xử lý khi thêm product vào chatitem
    private void addProductMessage(Product product){
        String imageUrl = null;
        if(product.getElectronicImgs() != null && !product.getElectronicImgs().isEmpty()){
            //Lấy ảnh đầu tiên của product để hiển thị
            imageUrl = product.getElectronicImgs().get(0).getUrl();
        }

        ChatItem.ProductMessage productMessage = new ChatItem.ProductMessage(
                product.get_id(),
                imageUrl,
                product.getName(),
                formatPrice(product.getPrice()) + " Đ"
        );

        chatItems.add(productMessage);
        notifyItemInserted();
    }

    //Hàm xử lý khi item insert vào chatitem
    private void notifyItemInserted() {
        chatAdapter.notifyItemInserted(chatItems.size() - 1);
        binding.rvChat.scrollToPosition(chatItems.size() - 1);
    }

    //Xóa message cuối cùng
    private void removeLastMessage() {
        if(!chatItems.isEmpty()){
            int lastIndex = chatItems.size() - 1;
            chatItems.remove(lastIndex);
            chatAdapter.notifyItemRemoved(lastIndex);
        }
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

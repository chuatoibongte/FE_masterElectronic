package com.example.mater_electronic.ui.navigation.chabot;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ItemChatImgUserBinding;
import com.example.mater_electronic.databinding.ItemChatProductBinding;
import com.example.mater_electronic.databinding.ItemChatTextBotBinding;
import com.example.mater_electronic.databinding.ItemChatTextUserBinding;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatItem> items;

    public static final int TYPE_USER = 0;
    public static final int TYPE_BOT = 1;
    public static final int TYPE_PRODUCT = 2;
    public static final int TYPE_USER_IMAGE = 3;

    public ChatAdapter(List<ChatItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        ChatItem item = items.get(position);
        if (item instanceof ChatItem.TextMessage) {
            return ((ChatItem.TextMessage) item).isUser ? TYPE_USER : TYPE_BOT;
        } else if (item instanceof ChatItem.ProductMessage) {
            return TYPE_PRODUCT;
        } else if(item instanceof ChatItem.ImageMessage){
            return TYPE_USER_IMAGE;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_USER) {
            ItemChatTextUserBinding binding = ItemChatTextUserBinding.inflate(inflater, parent, false);
            return new UserTextViewHolder(binding);
        } else if (viewType == TYPE_BOT) {
            ItemChatTextBotBinding binding = ItemChatTextBotBinding.inflate(inflater, parent, false);
            return new BotTextViewHolder(binding);
        } else if (viewType == TYPE_PRODUCT) {
            ItemChatProductBinding binding = ItemChatProductBinding.inflate(inflater, parent, false);
            return new ProductViewHolder(binding);
        } else if(viewType == TYPE_USER_IMAGE){
            ItemChatImgUserBinding binding = ItemChatImgUserBinding.inflate(inflater, parent, false);
            return new UserImageViewHolder(binding);
        }
        throw new IllegalArgumentException("Invalid viewType: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatItem item = items.get(position);
        if (holder instanceof UserTextViewHolder) {
            ((UserTextViewHolder) holder).bind((ChatItem.TextMessage) item);
        } else if (holder instanceof BotTextViewHolder) {
            ((BotTextViewHolder) holder).bind((ChatItem.TextMessage) item);
        } else if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).bind((ChatItem.ProductMessage) item);
        } else if(holder instanceof UserImageViewHolder){
            ((UserImageViewHolder) holder).bind((ChatItem.ImageMessage) item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class UserTextViewHolder extends RecyclerView.ViewHolder {
        ItemChatTextUserBinding binding;
        UserTextViewHolder(ItemChatTextUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(ChatItem.TextMessage item) {
            binding.tvMessage.setText(item.message);
        }
    }

    static class BotTextViewHolder extends RecyclerView.ViewHolder {
        ItemChatTextBotBinding binding;
        BotTextViewHolder(ItemChatTextBotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(ChatItem.TextMessage item) {
            binding.tvMessage.setText(item.message);
        }
    }

    static class UserImageViewHolder extends RecyclerView.ViewHolder {
        ItemChatImgUserBinding binding;
        UserImageViewHolder(ItemChatImgUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatItem.ImageMessage item) {
            // Load image from URL
            if(item.imageUri != null){
                Glide.with(binding.imgUserImage.getContext())
                        .load(item.imageUri)
                        .placeholder(R.drawable.bg_image_placeholder)
                        .into(binding.imgUserImage);
            }
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemChatProductBinding binding;
        ProductViewHolder(ItemChatProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(ChatItem.ProductMessage item) {
            // Load image from URL
            if (item.productImageUrl != null && !item.productImageUrl.isEmpty()) {
                Glide.with(binding.imgProduct.getContext())
                        .load(item.productImageUrl)
                        .placeholder(R.drawable.bg_image_placeholder)
                        .into(binding.imgProduct);
            } else {
                binding.imgProduct.setImageResource(R.drawable.bg_image_placeholder);
            }
            binding.tvProductName.setText(item.productName);
            binding.tvProductPrice.setText(item.productPrice);

            // Xử lý sự kiện click mở ProductDetailActivity
            binding.getRoot().setOnClickListener(v -> {
                if (item.productId != null && !item.productId.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                    intent.putExtra("product_id", item.productId);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

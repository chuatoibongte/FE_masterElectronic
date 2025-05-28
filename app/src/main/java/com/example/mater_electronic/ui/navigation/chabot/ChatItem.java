package com.example.mater_electronic.ui.navigation.chabot;

public abstract class ChatItem {
    public static final int TYPE_USER_TEXT = 0;
    public static final int TYPE_BOT_TEXT = 1;
    public static final int TYPE_PRODUCT = 2;

    public abstract int getType();

    public static class TextMessage extends ChatItem {
        public boolean isUser;
        public String message;

        public TextMessage(boolean isUser, String message) {
            this.isUser = isUser;
            this.message = message;
        }

        @Override
        public int getType() {
            return isUser ? TYPE_USER_TEXT : TYPE_BOT_TEXT;
        }
    }

    public static class ProductMessage extends ChatItem {
        public String productId;
        public String productImageUrl;
        public String productName;
        public String productPrice;

        public ProductMessage(String productId, String productImageUrl, String productName, String productPrice) {
            this.productId = productId;
            this.productImageUrl = productImageUrl;
            this.productName = productName;
            this.productPrice = productPrice;
        }

        @Override
        public int getType() {
            return TYPE_PRODUCT;
        }
    }
}

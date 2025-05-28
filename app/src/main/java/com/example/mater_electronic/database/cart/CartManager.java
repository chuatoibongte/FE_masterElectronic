package com.example.mater_electronic.database.cart;
import android.content.Context;

import com.example.mater_electronic.database.cart.CartDAO;
import com.example.mater_electronic.database.cart.CartDatabase;
import com.example.mater_electronic.models.cart.CartItem;

import java.util.List;

public class CartManager {
    private CartDAO cartDAO;
    private String currentUserId;

    public CartManager(Context context, String userId) {
        CartDatabase database = CartDatabase.getInstance(context);
        this.cartDAO = database.cartDAO();
        this.currentUserId = userId;
    }

    // Add item to cart
    public void addToCart(String productId, String productName, String productImage,
                          double price, int quantity, String category) {
        CartItem existingItem = cartDAO.getCartItemByProductId(productId, currentUserId);

        if (existingItem != null) {
            // Update quantity if item already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartDAO.updateCartItem(existingItem);
        } else {
            // Add new item
            CartItem newItem = new CartItem(productId, productName, productImage,
                    price, quantity, category, currentUserId);
            cartDAO.insertCartItem(newItem);
        }
    }

    // Get all cart items
    public List<CartItem> getAllCartItems() {
        return cartDAO.getAllCartItems(currentUserId);
    }

    // Get selected cart items
    public List<CartItem> getSelectedCartItems() {
        return cartDAO.getSelectedCartItems(currentUserId);
    }

    // Update quantity
    public void updateQuantity(long itemId, int quantity) {
        if (quantity <= 0) {
            cartDAO.deleteCartItemById(itemId);
        } else {
            cartDAO.updateQuantity(itemId, quantity);
        }
    }

    // Remove item from cart
    public void removeFromCart(long itemId) {
        cartDAO.deleteCartItemById(itemId);
    }

    // Toggle item selection
    public void toggleSelection(long itemId, boolean isSelected) {
        cartDAO.updateSelection(itemId, isSelected);
    }

    // Get cart item count
    public int getCartItemCount() {
        return cartDAO.getCartItemCount(currentUserId);
    }

    // Get total price of selected items
    public double getTotalPrice() {
        Double total = cartDAO.getTotalPrice(currentUserId);
        return total != null ? total : 0.0;
    }

    // Clear entire cart
    public void clearCart() {
        cartDAO.clearCart(currentUserId);
    }

    // Delete selected items (after checkout)
    public void deleteSelectedItems() {
        cartDAO.deleteSelectedItems(currentUserId);
    }

    // Check if product is in cart
    public boolean isProductInCart(String productId) {
        CartItem item = cartDAO.getCartItemByProductId(productId, currentUserId);
        return item != null;
    }

    // Get product quantity in cart
    public int getProductQuantityInCart(String productId) {
        CartItem item = cartDAO.getCartItemByProductId(productId, currentUserId);
        return item != null ? item.getQuantity() : 0;
    }
}

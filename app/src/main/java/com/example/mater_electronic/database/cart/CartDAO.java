package com.example.mater_electronic.database.cart;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mater_electronic.models.cart.CartItem;

import java.util.List;

@Dao
public interface CartDAO {

    // Insert cart item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);

    // Get all cart items for a user
    @Query("SELECT * FROM cart_items WHERE user_id = :userId ORDER BY date_added DESC")
    List<CartItem> getAllCartItems(String userId);

    // Get cart item by product ID and user ID
    @Query("SELECT * FROM cart_items WHERE product_id = :productId AND user_id = :userId LIMIT 1")
    CartItem getCartItemByProductId(String productId, String userId);

    // Get cart item by ID
    @Query("SELECT * FROM cart_items WHERE id = :id LIMIT 1")
    CartItem getCartItemById(long id);

    // Get selected cart items
    @Query("SELECT * FROM cart_items WHERE user_id = :userId AND is_selected = 1 ORDER BY date_added DESC")
    List<CartItem> getSelectedCartItems(String userId);

    // Get cart item count
    @Query("SELECT COUNT(*) FROM cart_items WHERE user_id = :userId")
    int getCartItemCount(String userId);

    // Get total price of selected items
    @Query("SELECT SUM(price * quantity) FROM cart_items WHERE user_id = :userId AND is_selected = 1")
    Double getTotalPrice(String userId);

    // Get total quantity
    @Query("SELECT SUM(quantity) FROM cart_items WHERE user_id = :userId")
    Integer getTotalQuantity(String userId);

    // Update cart item
    @Update
    void updateCartItem(CartItem cartItem);

    // Update quantity
    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :id")
    void updateQuantity(long id, int quantity);

    // Update selection status
    @Query("UPDATE cart_items SET is_selected = :isSelected WHERE id = :id")
    void updateSelection(long id, boolean isSelected);

    // Delete cart item
    @Delete
    void deleteCartItem(CartItem cartItem);

    // Delete cart item by ID
    @Query("DELETE FROM cart_items WHERE id = :id")
    void deleteCartItemById(long id);

    // Clear all cart items for a user
    @Query("DELETE FROM cart_items WHERE user_id = :userId")
    void clearCart(String userId);

    // Delete selected items
    @Query("DELETE FROM cart_items WHERE user_id = :userId AND is_selected = 1")
    void deleteSelectedItems(String userId);
}
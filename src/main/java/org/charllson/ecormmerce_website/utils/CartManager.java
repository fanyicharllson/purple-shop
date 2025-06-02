package org.charllson.ecormmerce_website.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.charllson.ecormmerce_website.model.Product;

import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private final Map<Integer, CartItem> cartItems = new HashMap<>();

    private final IntegerProperty cartItemCount = new SimpleIntegerProperty(0); //

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        CartItem item = cartItems.get(product.getId());
        if (item == null) {
            cartItems.put(product.getId(), new CartItem(product, 1));
        } else {
            item.incrementQuantity();
        }
        updateCartItemCount(); // update UI
    }

    public void removeFromCart(Product product) {
        cartItems.remove(product.getId());
        updateCartItemCount(); // update UI
    }

    public void clearCart() {
        cartItems.clear();
        updateCartItemCount(); // update UI
    }

    public Map<Integer, CartItem> getCartItems() {
        return cartItems;
    }

    public int getTotalItemCount() {
        return cartItems.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public IntegerProperty cartItemCountProperty() {
        return cartItemCount;
    }

    public void updateCartItemCount() {
        cartItemCount.set(getTotalItemCount());
    }
}

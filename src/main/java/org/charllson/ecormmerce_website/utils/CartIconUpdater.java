package org.charllson.ecormmerce_website.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class CartIconUpdater {
    private static Label cartCountLabel;

    public static void setCartCountLabel(Label label) {
        cartCountLabel = label;
        updateCartCount();
    }

    public static void updateCartCount() {
        if (cartCountLabel != null) {
            Platform.runLater(() -> {
                int count = CartManager.getInstance().getTotalItemCount();
                cartCountLabel.setText(String.valueOf(count));
            });
        }
    }
}

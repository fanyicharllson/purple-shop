package org.charllson.ecormmerce_website.controller;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.utils.CartIconUpdater;
import org.charllson.ecormmerce_website.utils.CartManager;

import java.io.InputStream;

public class ProductCardController {

    @FXML
    private VBox productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productTitle;

    @FXML
    private HBox ratingStars;

    @FXML
    private Label ratingCount;

    @FXML
    private Label productPrice;

    @FXML
    private Label productOriginalPrice;

    @FXML
    private Button addToCartButton;

    @FXML
    private Label favoriteIcon;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;

        // Set product details
        productTitle.setText(product.getName());
        productPrice.setText(product.getFormattedPrice());

        if (product.getOriginalPrice() > product.getPrice()) {
            productOriginalPrice.setText(product.getFormattedOriginalPrice());
            productOriginalPrice.setVisible(true);
        } else {
            productOriginalPrice.setVisible(false);
        }

        ratingCount.setText("(" + product.getReviewCount() + ")");

        // Set rating stars
        updateRatingStars(product.getRating());

        // Load product image
        if (product.getMainImagePath() != null) {
            productImage.setImage(loadProductImages(product.getMainImagePath()));
        }
    }

    private void updateRatingStars(double rating) {
        ratingStars.getChildren().clear();

        int fullStars = (int) Math.floor(rating);
        boolean hasHalfStar = rating - fullStars >= 0.5;

        // Add full stars
        for (int i = 0; i < fullStars; i++) {
            Label star = new Label("★");
            star.getStyleClass().add("star-filled");
            ratingStars.getChildren().add(star);
        }

        // Add half star if needed
        if (hasHalfStar) {
            Label halfStar = new Label("★");
            halfStar.getStyleClass().add("star-half");
            ratingStars.getChildren().add(halfStar);
            fullStars++;
        }

        // Add empty stars
        for (int i = fullStars; i < 5; i++) {
            Label emptyStar = new Label("☆");
            emptyStar.getStyleClass().add("star-empty");
            ratingStars.getChildren().add(emptyStar);
        }
    }

    @FXML
    private void addToCart() {
        CartManager.getInstance().addToCart(product);
        System.out.println("Added " + product.getName() + " to cart");


        // Notify UI to update cart icon count
        CartIconUpdater.updateCartCount();

        // Animate the cart count
//        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), cartCount);
//        pulse.setToX(1.5);
//        pulse.setToY(1.5);
//        pulse.setCycleCount(2);
//        pulse.setAutoReverse(true);
//        pulse.play();
    }

    @FXML
    private void toggleFavorite() {
        if (favoriteIcon.getText().equals("♡")) {
            favoriteIcon.setText("♥");
        } else {
            favoriteIcon.setText("♡");
        }
    }

    // Helper method to load images
    private Image loadProductImages(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                return new Image(is);
            } else {
                System.err.println("Image not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}

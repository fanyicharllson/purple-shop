package org.charllson.ecormmerce_website.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.charllson.ecormmerce_website.model.Product;

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
        // This would add the product to the cart in a real application
        System.out.println("Added " + product.getName() + " to cart");
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

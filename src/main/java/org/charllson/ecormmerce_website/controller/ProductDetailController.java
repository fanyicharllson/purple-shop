package org.charllson.ecormmerce_website.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.charllson.ecormmerce_website.database.UserDb;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.model.User;
import org.charllson.ecormmerce_website.service.ProductService;
import org.charllson.ecormmerce_website.utils.CartIconUpdater;
import org.charllson.ecormmerce_website.utils.CartManager;
import org.charllson.ecormmerce_website.utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductDetailController implements Initializable {

    ManageAuth manageAuth = new ManageAuth();
    User user = UserDb.getUserById(SessionManager.getInstance().getCurrentUserId());
    @FXML
    private Label productTitle;
    @FXML
    private Text descriptionText;
    @FXML
    private Label productPrice;
    @FXML
    private Label productOriginalPrice;
    @FXML
    private Label discountBadge;
    @FXML
    private HBox ratingStars;
    @FXML
    private Label ratingText;
    @FXML
    private Label stockInfo;
    @FXML
    private ImageView mainProductImage;
    @FXML
    private ImageView productImage1;
    @FXML
    private ImageView productImage2;
    @FXML
    private ImageView productImage3;
    @FXML
    private ImageView productImage4;
    @FXML
    private FlowPane relatedProducts;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView reviewerImage1;
    @FXML
    private ImageView reviewerImage2;
    @FXML
    private Label favoriteIcon;
    @FXML
    private Button favoriteButton;
    @FXML
    private Button breadcrumbCategory2;
    @FXML
    private Button backButton;
    @FXML
    private Label quantityLabel;
    @FXML
    private Button addToCartButton;
    @FXML
    private Button buyNowButton;
    @FXML
    private Label cartCount;
    @FXML
    private Label wishlistCount;
    @FXML
    private Label breadcrumbCategory;
    @FXML
    private Label breadcrumbCurrent;
    @FXML
    private Label specBrand;
    @FXML
    private Label specModel;
    @FXML
    private Label specEngine;
    @FXML
    private Label specHorsepower;
    @FXML
    private Label specTransmission;
    @FXML
    private Label specAcceleration;
    @FXML
    private Label specWarranty;
    private boolean isFavorite = false;
    private int quantity = 1;
    private int wishlistItemCount = 5; // Initial value from FXML
    private ProductService productService;
    private Product currentProduct;
    private int productId;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get product service instance
        productService = ProductService.getInstance();

        //Ensure cart count sync across page ui
        cartCount.textProperty().bind(CartManager.getInstance().cartItemCountProperty().asString());


        // Set up button animations
        setupButtonAnimations();

        // Initialize product state
        updateQuantityLabel();


        String imagePath = user.getProfileImagePath();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                profileImage.setImage(new Image("file:" + imagePath));
            } else {
                // fallback to default
                profileImage.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/placeHolder.png"));
            }
        } else {
            profileImage.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/placeHolder.png"));
        }

        Circle clip = new Circle(profileImage.getFitWidth() / 2, profileImage.getFitHeight() / 2, profileImage.getFitWidth() / 2);
        profileImage.setClip(clip);

        reviewerImage1.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/carImages/rolls royce5.jpg"));
        reviewerImage2.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/carImages/rolls royce6.jpg"));
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        loadProductData();
    }

    private void loadProductData() {
        // Get product by ID
        currentProduct = productService.getProductById(productId);
        System.out.println("Current Product Id: " + currentProduct);

        if (currentProduct == null) {
            System.err.println("Product not found with ID: " + productId);
            return;
        }

        // Set product details
        productTitle.setText(currentProduct.getName());
        descriptionText.setText(currentProduct.getDescription());
        productPrice.setText(currentProduct.getFormattedPrice());

        if (currentProduct.getOriginalPrice() > currentProduct.getPrice()) {
            productOriginalPrice.setText(currentProduct.getFormattedOriginalPrice());
            productOriginalPrice.setVisible(true);

            int discountPercentage = currentProduct.getDiscountPercentage();
            discountBadge.setText(discountPercentage + "% OFF");
            discountBadge.setVisible(true);
        } else {
            productOriginalPrice.setVisible(false);
            discountBadge.setVisible(false);
        }

        // Set rating
        updateRatingStars(currentProduct.getRating());
        ratingText.setText(String.format("%.1f (%d reviews)",
                currentProduct.getRating(),
                currentProduct.getReviewCount()));

        // Set stock info
        stockInfo.setText(currentProduct.getStockQuantity() + " vehicles available");

        // Set breadcrumb
        breadcrumbCategory2.setText(currentProduct.getCategory());
        breadcrumbCurrent.setText(currentProduct.getName());

        // Set specifications
        specBrand.setText(currentProduct.getBrand());
        specModel.setText(currentProduct.getModel());

        String[] specs = currentProduct.getSpecifications();
        if (specs != null && specs.length >= 5) {
            specEngine.setText(specs[0]);
            specHorsepower.setText(specs[1]);
            specTransmission.setText(specs[2]);
            specAcceleration.setText(specs[3]);
            specWarranty.setText(specs[6]);
        }

        // Load images
        List<String> imagePaths = currentProduct.getImagePaths();
        if (!imagePaths.isEmpty()) {
            mainProductImage.setImage(loadProductImages(imagePaths.get(0)));

            if (imagePaths.size() > 1) {
                productImage1.setImage(loadProductImages(imagePaths.get(0)));
                productImage2.setImage(loadProductImages(imagePaths.size() > 1 ? imagePaths.get(1) : imagePaths.get(0)));
                productImage3.setImage(loadProductImages(imagePaths.size() > 2 ? imagePaths.get(2) : imagePaths.get(0)));
                productImage4.setImage(loadProductImages(imagePaths.size() > 3 ? imagePaths.get(3) : imagePaths.get(0)));
            }
        }

        // Load related products
        loadRelatedProducts();
    }

    private void loadRelatedProducts() {
        // Clear existing related products
        relatedProducts.getChildren().clear();

        // Get related products
        List<Product> related = productService.getRelatedProducts(productId);

        // Add related product cards
        for (Product product : related) {
            try {
                // Create a VBox for the related product
                VBox productCard = new VBox();
                productCard.getStyleClass().add("related-product-card");

                // Create ImageView for product image
                ImageView imageView = new ImageView();
                imageView.setFitWidth(200);
                imageView.setFitHeight(120);
                imageView.getStyleClass().add("related-product-image");

                if (!product.getImagePaths().isEmpty()) {
                    imageView.setImage(loadProductImages(product.getImagePaths().get(0)));
                }

                // Create VBox for product info
                VBox productInfo = new VBox(5);
                productInfo.getStyleClass().add("related-product-info");

                // Create Label for product title
                Label title = new Label(product.getName());
                title.getStyleClass().add("related-product-title");

                // Create HBox for price
                HBox priceBox = new HBox(5);
                priceBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                Label price = new Label(product.getFormattedPrice());
                price.getStyleClass().add("related-product-price");
                priceBox.getChildren().add(price);

                if (product.getOriginalPrice() > product.getPrice()) {
                    Label originalPrice = new Label(product.getFormattedOriginalPrice());
                    originalPrice.getStyleClass().add("related-product-original");
                    priceBox.getChildren().add(originalPrice);
                }

                // Create Button for add to cart
                Button addButton = new Button("Add to Cart");
                addButton.getStyleClass().add("related-add-cart");

                // Add all elements to the product info VBox
                productInfo.getChildren().addAll(title, priceBox, addButton);

                // Add image and info to the product card
                productCard.getChildren().addAll(imageView, productInfo);

                // Add click event to open product detail
                final int relatedProductId = product.getId();
                productCard.setOnMouseClicked(event -> openProductDetail(relatedProductId));

                // Add to related products container
                relatedProducts.getChildren().add(productCard);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error creating related product card: " + e.getMessage());
            }
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

    private void setupButtonAnimations() {
        // Add to Cart button animation
        addToCartButton.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), addToCartButton);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        addToCartButton.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), addToCartButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });

        // Buy Now button animation
        buyNowButton.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), buyNowButton);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        buyNowButton.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), buyNowButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });

        // Favorite button animation
        favoriteButton.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), favoriteButton);
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.play();
        });

        favoriteButton.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), favoriteButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });
    }

    @FXML
    private void toggleFavorite() {
        isFavorite = !isFavorite;

        if (isFavorite) {
            favoriteIcon.setText("♥"); // Filled heart
            wishlistItemCount++;
        } else {
            favoriteIcon.setText("♡"); // Empty heart
            wishlistItemCount--;
        }

        // Update wishlist count
        wishlistCount.setText(String.valueOf(wishlistItemCount));

        // Animate the favorite button
        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), favoriteButton);
        pulse.setToX(1.2);
        pulse.setToY(1.2);
        pulse.setCycleCount(2);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    @FXML
    private void increaseQuantity() {
        if (currentProduct != null && quantity < currentProduct.getStockQuantity()) {
            quantity++;
            updateQuantityLabel();
        }
    }

    @FXML
    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            updateQuantityLabel();
        }
    }

    private void updateQuantityLabel() {
        quantityLabel.setText(String.valueOf(quantity));
    }

    @FXML
    private void addToCart() {
        if (currentProduct == null) {
            System.err.println("Current Product not set in ProductDetailController");
            return;
        }

        CartManager.getInstance().addToCart(currentProduct);
        System.out.println("Added " + currentProduct.getName() + " to cart");

        CartIconUpdater.updateCartCount();
        // Animate the cart count
        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), cartCount);
        pulse.setToX(1.5);
        pulse.setToY(1.5);
        pulse.setCycleCount(2);
        pulse.setAutoReverse(true);
        pulse.play();
    }

    @FXML
    private void buyNow() {
        // First add to cart
        addToCart();

        // Then navigate to checkout (would be implemented in a real app)
        System.out.println("Navigating to checkout with " + quantity + " items");
    }

    @FXML
    private void selectImage(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Get the ImageView inside the button
        ImageView thumbnailView = (ImageView) clickedButton.getGraphic();

        // Get the image from the thumbnail
        Image selectedImage = thumbnailView.getImage();

        // Set the main product image to the selected thumbnail image
        if (selectedImage != null) {
            mainProductImage.setImage(selectedImage);
        }

    }

    @FXML
    private void handleBack() {
        try {
            // Load the welcome view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-catalog-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading welcome view: " + e.getMessage());
        }
    }

    @FXML
    private void selectColor() {
        // In a real app, this would update the selected color and possibly the product image
        // For this example, we'll just log the action
        System.out.println("Color selected");
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

    @FXML
    private void goToCartPage(ActionEvent event) throws IOException {
        CartManager cartManager = CartManager.getInstance();
        String username = SessionManager.getInstance().getCurrentUserName();
        if (cartManager.getTotalItemCount() == 0) {
            showInfoMessage("Hey " + username + " ,Your cart is empty!");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/cart-page.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setHeight(400);
        stage.show();
    }

    private void showInfoMessage(String message) {
        // Use Alert for simplicity:
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void backToCatalog() {
        try {
            // Load the product catalog view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-catalog-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) mainProductImage.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading product catalog view: " + e.getMessage());
        }
    }

    private void openProductDetail(int productId) {
        // Simply update the current product ID and reload the data
        this.productId = productId;
        loadProductData();

        // Scroll to top
        mainProductImage.getParent().requestFocus();
    }

    public void openUserProfile(MouseEvent mouseEvent) {
        System.out.println("Moving to open user profile");
        manageAuth.openUserProfile(mouseEvent, mainStage);
    }
}


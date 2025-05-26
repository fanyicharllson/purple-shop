package org.charllson.ecormmerce_website.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.service.ProductService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductCatalogController implements Initializable {

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private FlowPane productGrid;

    @FXML
    private ImageView profileImage;

    private ProductService productService;
    private List<Product> products;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get product service instance
        productService = ProductService.getInstance();
        products = productService.getAllProducts();

        // Initialize combo boxes
        sortComboBox.getItems().addAll("Featured", "Price: Low to High", "Price: High to Low", "Newest", "Best Selling");
        filterComboBox.getItems().addAll("All Categories", "Luxury", "Sports", "Shoes", "Underwear", "Cloth", "Vintage");

        // Load profile image
        profileImage.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/placeHolder.png"));

        // Populate product grid
        populateProductGrid();
    }

    private void populateProductGrid() {
        // Clear existing items
        productGrid.getChildren().clear();

        // Add product cards
        for (Product product : products) {
            try {
                // Load the product card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-card.fxml"));
                VBox productCard = loader.load();

                // Get the controller
                ProductCardController cardController = loader.getController();

                // Set the product data
                cardController.setProduct(product);

                // Add click event to open product detail
                productCard.setOnMouseClicked(event -> openProductDetail(product.getId()));

                // Add to grid
                productGrid.getChildren().add(productCard);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading product card: " + e.getMessage());
            }
        }
    }

    @FXML
    private void openProductDetail(int productId) {
        try {
            // Load the product detail view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-detail-view.fxml"));
            Parent root = loader.load();

            // Get the controller and set the product ID
            ProductDetailController detailController = loader.getController();
            detailController.setProductId(productId);

            // Get the current stage
            Stage stage = (Stage) productGrid.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading product detail view: " + e.getMessage());
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

    @FXML
    private void handleFilterChange() {
        String category = filterComboBox.getValue();

        if (category == null || category.equals("All Categories")) {
            products = productService.getAllProducts();
        } else {
            products = productService.getProductsByCategory(category);
        }

        populateProductGrid();
    }

    @FXML
    private void handleSortChange() {
        String sortOption = sortComboBox.getValue();

        if (sortOption != null) {
            switch (sortOption) {
                case "Price: Low to High":
                    products.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
                    break;
                case "Price: High to Low":
                    products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                    break;
                case "Best Selling":
                    products.sort((p1, p2) -> Integer.compare(p2.getReviewCount(), p1.getReviewCount()));
                    break;
                default:
                    // Default sorting (Featured)
                    products.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
                    break;
            }
        }

        populateProductGrid();
    }
}
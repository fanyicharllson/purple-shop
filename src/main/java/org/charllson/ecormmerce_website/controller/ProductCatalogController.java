package org.charllson.ecormmerce_website.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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

    @FXML
    private HBox categoryNav;

    @FXML
    private Label pageTitle;

    @FXML
    private Label pageSubtitle;

    @FXML
    private Label breadcrumbCurrent;

    // Category buttons
    @FXML
    private Button homeButton;

    @FXML
    private Button carsButton;

    @FXML
    private Button sportsButton;

    @FXML
    private Button shoesButton;

    @FXML
    private Button underwearsButton;

    @FXML
    private Button clothsButton;

    @FXML
    private Button vintageButton;

    private ProductService productService;
    private List<Product> products;
    private String currentCategory = "Home";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get product service instance
        productService = ProductService.getInstance();
        products = productService.getAllProducts();

        // Initialize combo boxes
        sortComboBox.getItems().addAll("Featured", "Price: Low to High", "Price: High to Low", "Newest", "Best Selling");
        filterComboBox.getItems().addAll("All Categories", "Cars", "Sports", "Shoes", "Underwears", "Cloths", "Vintage");

        // Load profile image
        profileImage.setImage(loadProductImages("/org/charllson/ecormmerce_website/images/placeHolder.png"));

        // Set initial page content
        updatePageContent("Home");

        // Populate product grid with all products initially
        populateProductGrid();
    }

    @FXML
    private void handleCategoryClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String category = (String) clickedButton.getUserData();

        // Remove active class from all category buttons
        clearCategoryActiveStates();

        // Add active class to clicked button
        if (!clickedButton.getStyleClass().contains("category-active")) {
            clickedButton.getStyleClass().add("category-active");
        }

        // Update current category
        currentCategory = category;

        // Update page content
        updatePageContent(category);

        // Filter products based on category
        if (category.equals("Home")) {
            products = productService.getAllProducts();
        } else {
            products = productService.getProductsByCategory(category);
        }

        // Update filter combo box to match selected category
        if (category.equals("Home")) {
            filterComboBox.setValue("All Categories");
        } else {
            filterComboBox.setValue(category);
        }

        // Repopulate the grid
        populateProductGrid();
    }

    private void updatePageContent(String category) {
        switch (category) {
            case "Home":
                pageTitle.setText("All Products");
                pageSubtitle.setText("Discover our complete collection of premium products");
                breadcrumbCurrent.setText("All Products");
                break;
            case "Cars":
                pageTitle.setText("Luxury Cars");
                pageSubtitle.setText("Discover our collection of premium vehicles");
                breadcrumbCurrent.setText("Cars");
                break;
            case "Sports":
                pageTitle.setText("Sports Equipment");
                pageSubtitle.setText("Professional sports gear for athletes");
                breadcrumbCurrent.setText("Sports");
                break;
            case "Shoes":
                pageTitle.setText("Premium Footwear");
                pageSubtitle.setText("Step up your style with our shoe collection");
                breadcrumbCurrent.setText("Shoes");
                break;
            case "Underwears":
                pageTitle.setText("Comfort Underwear");
                pageSubtitle.setText("Premium comfort for everyday wear");
                breadcrumbCurrent.setText("Underwears");
                break;
            case "Cloths":
                pageTitle.setText("Fashion Clothing");
                pageSubtitle.setText("Stylish clothing for every occasion");
                breadcrumbCurrent.setText("Cloths");
                break;
            case "Vintage":
                pageTitle.setText("Vintage Collection");
                pageSubtitle.setText("Timeless pieces with classic appeal");
                breadcrumbCurrent.setText("Vintage");
                break;
            default:
                pageTitle.setText("Products");
                pageSubtitle.setText("Browse our collection");
                breadcrumbCurrent.setText("Products");
                break;
        }
    }

    private void clearCategoryActiveStates() {
        if (homeButton != null) homeButton.getStyleClass().remove("category-active");
        if (carsButton != null) carsButton.getStyleClass().remove("category-active");
        if (sportsButton != null) sportsButton.getStyleClass().remove("category-active");
        if (shoesButton != null) shoesButton.getStyleClass().remove("category-active");
        if (underwearsButton != null) underwearsButton.getStyleClass().remove("category-active");
        if (clothsButton != null) clothsButton.getStyleClass().remove("category-active");
        if (vintageButton != null) vintageButton.getStyleClass().remove("category-active");
    }

    private void setActiveCategoryButton(String category) {
        clearCategoryActiveStates();

        switch (category) {
            case "Home":
            case "All Categories":
                if (homeButton != null) homeButton.getStyleClass().add("category-active");
                break;
            case "Cars":
                if (carsButton != null) carsButton.getStyleClass().add("category-active");
                break;
            case "Sports":
                if (sportsButton != null) sportsButton.getStyleClass().add("category-active");
                break;
            case "Shoes":
                if (shoesButton != null) shoesButton.getStyleClass().add("category-active");
                break;
            case "Underwears":
                if (underwearsButton != null) underwearsButton.getStyleClass().add("category-active");
                break;
            case "Cloths":
                if (clothsButton != null) clothsButton.getStyleClass().add("category-active");
                break;
            case "Vintage":
                if (vintageButton != null) vintageButton.getStyleClass().add("category-active");
                break;
        }
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

        // Show message if no products found
        if (products.isEmpty()) {
            Label noProductsLabel = new Label("No products found in this category");
            noProductsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666666; -fx-padding: 50px;");
            productGrid.getChildren().add(noProductsLabel);
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
            currentCategory = "Home";
            updatePageContent("Home");
            setActiveCategoryButton("Home");
        } else {
            products = productService.getProductsByCategory(category);
            currentCategory = category;
            updatePageContent(category);
            setActiveCategoryButton(category);
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
                case "Newest":
                    // Sort by ID in descending order (assuming higher ID = newer)
                    products.sort((p1, p2) -> Integer.compare(p2.getId(), p1.getId()));
                    break;
                default:
                    // Default sorting (Featured)
                    products.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
                    break;
            }
        }

        populateProductGrid();
    }

    // Method to handle search functionality (if you want to add it later)
    @FXML
    private void handleSearch() {
        // Implementation for search functionality
        // This can be added later when you implement search
        System.out.println("Search functionality - to be implemented");
    }

    // Method to handle navigation to cart
    @FXML
    private void openCart() {
        // Implementation for cart navigation
        // This can be added later when you implement cart page
        System.out.println("Cart navigation - to be implemented");
    }

    // Method to handle navigation to wishlist
    @FXML
    private void openWishlist() {
        // Implementation for wishlist navigation
        // This can be added later when you implement wishlist page
        System.out.println("Wishlist navigation - to be implemented");
    }
}
package org.charllson.ecormmerce_website.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private final int id;
    private final String name;
    private final String description;
    private final double price;
    private final double originalPrice;
    private final String brand;
    private final String model;
    private final String category;
    private final double rating;
    private final int reviewCount;
    private final int stockQuantity;
    private final List<String> imagePaths;
    private final List<String> colors;
    private String[] specifications;

    // Constructor
    public Product(int id, String name, String description, double price, double originalPrice,
                   String brand, String model, String category, double rating, int reviewCount,
                   int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.originalPrice = originalPrice;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.stockQuantity = stockQuantity;
        this.imagePaths = new ArrayList<>();
        this.colors = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
    }

    public List<String> getColors() {
        return colors;
    }

    public void addColor(String color) {
        this.colors.add(color);
    }

    public String[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String[] specifications) {
        this.specifications = specifications;
    }

    // Calculate discount percentage
    public int getDiscountPercentage() {
        if (originalPrice > price && originalPrice > 0) {
            return (int) Math.round(((originalPrice - price) / originalPrice) * 100);
        }
        return 0;
    }

    // Format price as currency string
    public String getFormattedPrice() {
        return String.format("$%,.0f", price);
    }

    public String getFormattedOriginalPrice() {
        return String.format("$%,.0f", originalPrice);
    }

    // Get main image (first in the list)
    public String getMainImagePath() {
        if (!imagePaths.isEmpty()) {
            return imagePaths.getFirst();
        }
        return null;
    }
}

package org.charllson.ecormmerce_website.model;

public class OrderItem {
    private final String productName;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(String productName, int quantity, double unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }
}

package org.charllson.ecormmerce_website.model;

public class Order {
    private int id;
    private String createdAt, shippingAddress, paymentMethod, status, reason;
    private double totalPrice;
    private String userEmail, fullName, paymentDetails;

    public Order(int id, String createdAt, String shippingAddress, String paymentMethod,
                 double totalPrice, String status, String reason, String userEmail, String fullName) {
        this.id = id;
        this.createdAt = createdAt;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.status = status;
        this.reason = reason;
        this.userEmail = userEmail;
        this.fullName = fullName;
    }

    public int getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public String getShippingAddress() { return shippingAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getReason() { return reason; }
    public String getUserEmail() { return userEmail; }
    public String getFullName() { return fullName; }
    public String getPaymentDetails() { return paymentDetails; }
}

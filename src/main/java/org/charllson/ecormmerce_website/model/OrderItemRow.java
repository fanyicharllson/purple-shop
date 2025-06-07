package org.charllson.ecormmerce_website.model;

import javafx.beans.property.*;

public class OrderItemRow {
    private final IntegerProperty orderId;
    private final StringProperty createdAt;
    private final StringProperty shippingAddress;
    private final StringProperty paymentMethod;
    private final DoubleProperty totalPrice;
    private final StringProperty status;
    private final StringProperty reason;

    private final StringProperty productName;
    private final IntegerProperty quantity;
    private final DoubleProperty unitPrice;
    private  final DoubleProperty totalPriceForItem;



    public OrderItemRow(
            int orderId,
            String createdAt,
            String shippingAddress,
            String paymentMethod,
            double totalPrice,
            String status,
            String reason,
            String productName,
            int quantity,
            double unitPrice
    ) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.shippingAddress = new SimpleStringProperty(shippingAddress);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.status = new SimpleStringProperty(status);
        this.reason = new SimpleStringProperty(reason);

        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);

        this.totalPriceForItem = new SimpleDoubleProperty(unitPrice * quantity);
    }

    // Getters and Property getters
    public int getOrderId() { return orderId.get(); }
    public IntegerProperty orderIdProperty() { return orderId; }

    public String getCreatedAt() { return createdAt.get(); }
    public StringProperty createdAtProperty() { return createdAt; }

    public String getShippingAddress() { return shippingAddress.get(); }
    public StringProperty shippingAddressProperty() { return shippingAddress; }

    public String getPaymentMethod() { return paymentMethod.get(); }
    public StringProperty paymentMethodProperty() { return paymentMethod; }

//    public double getTotalPrice() { return totalPrice.get(); }
//    public DoubleProperty totalPriceProperty() { return totalPrice; }

    public double getTotalPriceForItem() { return totalPriceForItem.get(); }
    public DoubleProperty totalPriceForItemProperty() {return totalPriceForItem;}

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }

    public String getReason() { return reason.get(); }
    public StringProperty reasonProperty() { return reason; }

    public String getProductName() { return productName.get(); }
    public StringProperty productNameProperty() { return productName; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getUnitPrice() { return unitPrice.get(); }
    public DoubleProperty unitPriceProperty() { return unitPrice; }
}

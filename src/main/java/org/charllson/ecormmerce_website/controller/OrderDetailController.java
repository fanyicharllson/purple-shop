package org.charllson.ecormmerce_website.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.Order;
import org.charllson.ecormmerce_website.model.OrderItem;
import org.charllson.ecormmerce_website.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDetailController {
    @FXML private Label orderInfoLabel;
    @FXML private TextArea paymentDetailsArea;
    @FXML private TextField reasonField;
    @FXML private TableView<OrderItem> itemsTable;
    @FXML private TableColumn<OrderItem, String> productColumn;
    @FXML private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML private TableColumn<OrderItem, Double> unitPriceColumn;
    @FXML private TableColumn<OrderItem, Double> totalPriceColumn;


    private Order currentOrder;

    public void setOrder(Order order) {
        this.currentOrder = order;
        orderInfoLabel.setText("Order #" + order.getId() + " by " + order.getFullName() + "\nStatus: " + order.getStatus());
        paymentDetailsArea.setText(order.getPaymentMethod());

        // Initialize item columns
        productColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        quantityColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getQuantity()));
        unitPriceColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUnitPrice()));
        totalPriceColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTotalPrice()));

        // Load order items from DB
        ObservableList<OrderItem> items = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM order_items WHERE order_id = ?"
             )) {

            stmt.setInt(1, order.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")
                ));
            }

            itemsTable.setItems(items);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleApprove() {
        updateStatus("approved", null);
    }

    public void handleReject() {
        String reason = reasonField.getText();
        if (reason.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Please provide a reason for rejection.");
            return;
        }
        updateStatus("rejected", reason);
    }

    private void updateStatus(String newStatus, String reason) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE orders SET status = ?, reason = ? WHERE id = ?"
             )) {
            stmt.setString(1, newStatus);
            stmt.setString(2, reason);
            stmt.setInt(3, currentOrder.getId());
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Order " + newStatus + " successfully.");
            ((Stage) orderInfoLabel.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating status.");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

package org.charllson.ecormmerce_website.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.Order;
import org.charllson.ecormmerce_website.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class AdminDashboardController {

    @FXML
    private TableView<Order> adminOrdersTable;
    @FXML
    private TableColumn<Order, Integer> idColumn;
    @FXML
    private TableColumn<Order, String> emailColumn;
    @FXML
    private TableColumn<Order, String> nameColumn;
    @FXML
    private TableColumn<Order, String> addressColumn;
    @FXML
    private TableColumn<Order, String> paymentColumn;
    @FXML
    private TableColumn<Order, String> totalColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private ComboBox<String> statusFilter;

    private final ObservableList<Order> adminOrders = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserEmail()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getShippingAddress()));
        paymentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPaymentMethod()));
        totalColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getTotalPrice())));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        statusFilter.setItems(FXCollections.observableArrayList("All", "pending", "approved", "rejected"));
        statusFilter.setValue("All");
        statusFilter.setOnAction(e -> loadOrders(statusFilter.getValue()));

        adminOrdersTable.setItems(adminOrders);
        adminOrdersTable.setOnMouseClicked(this::handleOrderClick);

        adminOrdersTable.setRowFactory(tableView -> new TableRow<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (order == null || empty) {
                    setStyle("");
                } else {
                    switch (order.getStatus()) {
                        case "approved" -> setStyle("-fx-background-color: #dcfce7;");  // Light green
                        case "rejected" -> setStyle("-fx-background-color: #fee2e2;");  // Light red
                        case "pending" -> setStyle("-fx-background-color: #fef9c3;");   // Light yellow
                        default -> setStyle("");
                    }
                }
            }
        });

        loadOrders("All");
    }


    private void loadOrders(String status) {
        adminOrders.clear();
        String query = "SELECT * FROM orders" +
                ("All".equals(status) ? "" : " WHERE status = ?") +
                " ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (!"All".equals(status)) stmt.setString(1, status);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                adminOrders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("created_at"),
                        rs.getString("shipping_address"),
                        rs.getString("payment_method"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getString("reason"),
                        rs.getString("user_email"),
                        rs.getString("full_name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading orders: " + e.getMessage());
        }
    }

    private void handleOrderClick(MouseEvent event) {
        if (event.getClickCount() == 2 && adminOrdersTable.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = adminOrdersTable.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/order-detail-view.fxml"));
                Parent root = loader.load();

                OrderDetailController controller = loader.getController();
                controller.setOrder(selectedOrder);

                Stage dialog = new Stage();
                dialog.setTitle("Order Details");
                dialog.setScene(new Scene(root));
                dialog.setResizable(false);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.showAndWait();

                loadOrders("All");
                statusFilter.setValue("All");

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to load order details.");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
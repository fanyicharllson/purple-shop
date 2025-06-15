package org.charllson.ecormmerce_website.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.OrderItemRow;
import org.charllson.ecormmerce_website.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class ViewOrdersController {

    private final ObservableList<OrderItemRow> orderItemRows = FXCollections.observableArrayList();
    @FXML
    private TableView<OrderItemRow> ordersTable;
    @FXML
    private TableColumn<OrderItemRow, Integer> idColumn;
    @FXML
    private TableColumn<OrderItemRow, String> createdAtColumn;
    @FXML
    private TableColumn<OrderItemRow, String> shippingAddressColumn;
    @FXML
    private TableColumn<OrderItemRow, String> paymentMethodColumn;
    @FXML
    private TableColumn<OrderItemRow, Double> totalPriceColumn;
    @FXML
    private TableColumn<OrderItemRow, String> statusColumn;
    @FXML
    private TableColumn<OrderItemRow, String> reasonColumn;
    @FXML
    private TableColumn<OrderItemRow, String> productNameColumn;
    @FXML
    private TableColumn<OrderItemRow, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderItemRow, Double> unitPriceColumn;
    @FXML
    private Label totalItemsPriceLabel;
    @FXML
    private Button backButton;
    private String currentUserEmail;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> data.getValue().orderIdProperty().asObject());
        createdAtColumn.setCellValueFactory(data -> data.getValue().createdAtProperty());
        shippingAddressColumn.setCellValueFactory(data -> data.getValue().shippingAddressProperty());
        paymentMethodColumn.setCellValueFactory(data -> data.getValue().paymentMethodProperty());
        totalPriceColumn.setCellValueFactory(data -> data.getValue().totalPriceForItemProperty().asObject());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        reasonColumn.setCellValueFactory(data -> data.getValue().reasonProperty());

        productNameColumn.setCellValueFactory(data -> data.getValue().productNameProperty());
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        unitPriceColumn.setCellValueFactory(data -> data.getValue().unitPriceProperty().asObject());

        ordersTable.setItems(orderItemRows);
    }

    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
        loadOrdersForUser(email);
    }

    private void loadOrdersForUser(String email) {
        orderItemRows.clear();
        double totalItemsPrice = 0.0;

        String sql = """
                    SELECT
                                     o.id AS order_id,
                                     o.created_at,
                                     o.shipping_address,
                                     o.payment_method,
                                     o.total_price,
                                     o.status,
                                     o.reason,
                                     oi.product_name,
                                     oi.quantity,
                                     oi.unit_price,
                                     (oi.quantity * oi.unit_price) AS item_total_price
                                 FROM orders o
                                 JOIN order_items oi ON o.id = oi.order_id
                                 WHERE o.user_email = ?
                                 ORDER BY o.created_at DESC, o.id, oi.id;
                            \s
                \s""";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItemRow row = new OrderItemRow(
                        rs.getInt("order_id"),
                        rs.getString("created_at"),
                        rs.getString("shipping_address"),
                        rs.getString("payment_method"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getString("reason"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")
                );
                orderItemRows.add(row);

                // Sum up item total price per row
                totalItemsPrice += rs.getDouble("item_total_price");
            }

            // Update label text
            final double totalPriceFinal = totalItemsPrice;
            totalItemsPriceLabel.setText(String.format("Total Price of All Ordered Items: $%.2f", totalPriceFinal));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load orders: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-catalog-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();

            ProductCatalogController controller = loader.getController();
            controller.setMainStage(stage);


            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to return to main page.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

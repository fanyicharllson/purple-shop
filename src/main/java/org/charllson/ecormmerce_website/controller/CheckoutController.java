package org.charllson.ecormmerce_website.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.database.OrderDb;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.utils.CartItem;
import org.charllson.ecormmerce_website.utils.CartManager;
import org.charllson.ecormmerce_website.utils.SessionManager;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CheckoutController {

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> paymentMethodBox;


    @FXML
    private VBox checkoutItemsContainer;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private VBox creditCardForm;
    @FXML
    private VBox mobileMoneyForm;
    @FXML
    private VBox paypalForm;

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expiryDateField;
    @FXML
    private TextField cvvField;

    @FXML
    private TextField mobileMoneyNumberField;

    @FXML
    private TextField paypalEmailField;

    OrderDb orderDb = new OrderDb();



    @FXML
    public void initialize() {
        loadCartDetails();
        String name = SessionManager.getInstance().getCurrentUserName();
        String email = SessionManager.getInstance().getCurrentUserEmail();



        nameField.setText(name);
        emailField.setText(email);

        backButton.setOnAction(e -> navigateTo("/org/charllson/ecormmerce_website/cart-page.fxml"));

        paymentMethodBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updatePaymentFormVisibility(newVal);
        });


        confirmOrderButton.setOnAction(e -> handleOrderConfirmation());
        System.out.println("Cart contains: " + CartManager.getInstance().getCartItems().size() + " items");

    }

    private void loadCartDetails() {
        checkoutItemsContainer.getChildren().clear();
        Map<Integer, CartItem> cartItems = CartManager.getInstance().getCartItems();
        double total = 0;

        for (CartItem item : cartItems.values()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double price = product.getPrice() * quantity;
            total += price;

            Label itemLabel = new Label(product.getName() + " x" + quantity + " — $" + String.format("%.2f", price));
            itemLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #333;");
            checkoutItemsContainer.getChildren().add(itemLabel);
        }

        totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void handleOrderConfirmation() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill out all billing fields.");
            return;
        }

        String paymentMethod = paymentMethodBox.getValue();
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Payment Method", "Please select a payment method.");
            return;
        }

        String paymentDetails = "";

        switch (paymentMethod) {
            case "Credit Card":
                String cardNumber = cardNumberField.getText().trim();
                String expiry = expiryDateField.getText().trim();
                String cvv = cvvField.getText().trim();

                if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Missing Credit Card Info", "Please fill all credit card fields.");
                    return;
                }

                paymentDetails = "Type: Credit Card\nCard: " + cardNumber + "\nExpiry: " + expiry;
                break;

            case "Mobile Money":
                String mobileNumber = mobileMoneyNumberField.getText().trim();
                if (mobileNumber.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Missing Mobile Money Info", "Please enter mobile number.");
                    return;
                }

                paymentDetails = "Type: Mobile Money\nNumber: " + mobileNumber;
                break;

            case "PayPal":
                String paypalEmail = paypalEmailField.getText().trim();
                if (paypalEmail.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Missing PayPal Info", "Please enter PayPal email.");
                    return;
                }

                paymentDetails = "Type: PayPal\nEmail: " + paypalEmail;
                break;

            case "Cash on Delivery":
                paymentDetails = "Type: Cash on Delivery\n(No additional info needed)";
                break;
        }

        double total = 0;
        for (CartItem item : CartManager.getInstance().getCartItems().values()) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }

        orderDb.saveOrderToDatabase(name, email, address, paymentMethod, paymentDetails, total);
        // Simulate success
        System.out.println("Order confirmed!");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Details:\n" + paymentDetails);
        CartManager.getInstance().clearCart();
        showAlert(Alert.AlertType.INFORMATION, "Order Placed", "Thank you for your purchase, " + name + "!");
        navigateTo("/org/charllson/ecormmerce_website/product-catalog-view.fxml");
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setHeight(600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load: " + fxmlPath);
        }
    }

    private void updatePaymentFormVisibility(String method) {
        creditCardForm.setVisible("Credit Card".equals(method));
        creditCardForm.setManaged("Credit Card".equals(method));

        mobileMoneyForm.setVisible("Mobile Money".equals(method));
        mobileMoneyForm.setManaged("Mobile Money".equals(method));

        paypalForm.setVisible("PayPal".equals(method));
        paypalForm.setManaged("PayPal".equals(method));
    }

}

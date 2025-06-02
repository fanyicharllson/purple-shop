package org.charllson.ecormmerce_website.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.charllson.ecormmerce_website.utils.CartItem;
import org.charllson.ecormmerce_website.utils.CartManager;
import org.charllson.ecormmerce_website.utils.DBUtil;

public class OrderDb {

    public void saveOrderToDatabase(String name, String email, String address, String paymentMethod, String paymentDetails, double totalPrice) {
        try (Connection conn = DBUtil.getConnection()) {
            // Insert into orders table
            String orderSql = "INSERT INTO orders (user_email, full_name, shipping_address, payment_method, payment_details, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setString(1, email);
            orderStmt.setString(2, name);
            orderStmt.setString(3, address);
            orderStmt.setString(4, paymentMethod);
            orderStmt.setString(5, paymentDetails);
            orderStmt.setDouble(6, totalPrice);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);

                // Insert order items
                String itemSql = "INSERT INTO order_items (order_id, product_name, quantity, unit_price) VALUES (?, ?, ?, ?)";
                PreparedStatement itemStmt = conn.prepareStatement(itemSql);

                for (CartItem item : CartManager.getInstance().getCartItems().values()) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setString(2, item.getProduct().getName());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getProduct().getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }
        } catch (SQLException e) {
            System.err.println("Error storing order to db: " + e.getMessage());
        }
    }
}

package org.charllson.ecormmerce_website.database;

import org.charllson.ecormmerce_website.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDb {

    public static void saveContactMessage(String name, String email, String message) throws SQLException {
        String sql = "INSERT INTO contacts (name, email, message) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, message);
            stmt.executeUpdate();
        }
    }
}

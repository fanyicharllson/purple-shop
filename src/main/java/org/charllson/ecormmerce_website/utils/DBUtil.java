package org.charllson.ecormmerce_website.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\NTECH\\OneDrive\\Desktop\\GENERAL FOLDER\\CREATED DATABASES\\purpleshop.db";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}

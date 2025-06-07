package org.charllson.ecormmerce_website.database;

import org.charllson.ecormmerce_website.model.User;
import org.charllson.ecormmerce_website.utils.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDb {
    // saveUser method to return the generated user ID
    public static int saveUser(String fullName, String email, String rawPassword) {
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        String sql = "INSERT INTO users (full_name, email, password, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated user ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
        return -1; // Return -1 if failed
    }


    // Method for user login - returns user ID if successful
    public static int authenticateUser(String email, String rawPassword) {
        String sql = "SELECT id, password FROM users WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                // Check if the provided password matches the hashed password
                if (BCrypt.checkpw(rawPassword, hashedPassword)) {
                    return rs.getInt("id"); // Return user ID if authentication successful
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return -1; // Return -1 if authentication failed
    }


    public static User getUserById(int userId) {
        String sql = "SELECT id, full_name, email, password, profile_image_path, created_at FROM users WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setProfileImagePath(rs.getString("profile_image_path"));

                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }

        return null;
    }

    public static User getUserByEmail(String email) {
        String sql = "SELECT id, full_name, email, password, profile_image_path, created_at FROM users WHERE email = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setProfileImagePath(rs.getString("profile_image_path"));

                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by email: " + e.getMessage());
        }

        return null;
    }

    public static boolean updateUserProfile(int userId, String fullName, String email, String profileImagePath) {
        String sql = "UPDATE users SET full_name = ?, email = ?, profile_image_path = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, profileImagePath);
            stmt.setInt(4, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }

    public static String getUserNameById(int userId) {
        String sql = "SELECT full_name FROM users WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("full_name");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user name by ID: " + e.getMessage());
        }

        return null; // return null if user not found or on error
    }

    // Returns the whole user data
    public static User getAllUserById(int userId) {
        String sql = "SELECT id, full_name, email, password, profile_image_path, created_at FROM users WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setProfileImagePath(rs.getString("profile_image_path"));

                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
        }

        return null;
    }

    public static void updateUser(User user) {
        String query = "UPDATE users SET full_name = ?, email = ?, profile_image_path = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); // Replace with your actual connection method
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getProfileImagePath());
            pstmt.setInt(4, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String resetPassword(String email, String rawPassword) {
        try (Connection conn = DBUtil.getConnection()) {
            // Check if user exists
            String checkQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                return "User with this email not found. Make sure you enter your previous email address!";
            }

            // Hash new password
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

            // Update password
            String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, hashedPassword);
            updateStmt.setString(2, email);

            int rows = updateStmt.executeUpdate();
            return rows > 0 ? "success" : "Failed to update password.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }



}


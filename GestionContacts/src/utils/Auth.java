package utils;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Auth {
	
	public boolean authenticate(String username, String passwordInput) {
        String query = "SELECT Password, Role FROM users WHERE Username = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String role = rs.getString("Role");

                if (storedPassword.equals(passwordInput)) {
                    System.out.println("Login successful! Role: " + role);
                    return true;
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
//	public static void main(String[] args) {
//        Auth auth = new Auth();
//        String inputUsername = "admin";     // simulate user input
//        String inputPassword = "admin123";
//
//        if (auth.authenticate(inputUsername, inputPassword)) {
//            System.out.println("Login successful!");
//        } else {
//            System.out.println("Invalid username or password.");
//        }
//    }
}

package src2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/miniprojectdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root2025";
    
    private static Connection connection = null;
    
    private void DBConnection() {}

    // Public method to get the connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("❌ Failed to connect to the database.");
            }
        }
        return connection;
    }
}








package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static Connection connection;

    static  {
            try {
    			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactsdb","root","");
                System.out.println("✅ Database connected successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("❌ Failed to connect to the database.");
            }
            
        }
    	
    	private static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactsdb", "root", "");
    	}
        public static Connection getConnection() {
        	try {
                if (connection == null || connection.isClosed()) {
                    System.out.println("⚠️ Connection was closed, reopening...");
                    connect(); // reconnect if closed
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
}









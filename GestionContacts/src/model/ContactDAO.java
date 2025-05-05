package model;

import utils.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM contact")) {

            while (rs.next()) {
            	System.out.println("Fetching contact with ID: " + rs.getInt("id_Contact"));
                contacts.add(new Contact(
                        rs.getInt("id_Contact"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Libelle"),
                        rs.getString("Sexe"),
                        rs.getString("TelPerso"),
                        rs.getString("TelProf"),
                        rs.getString("Email"),
                        rs.getInt("Num_Categorie"),
                        rs.getInt("Num_Ville")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public boolean deleteContact(int id) {
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE id_contact = ?")) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

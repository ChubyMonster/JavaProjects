package view;

import javax.swing.*;

import utils.MySQLConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Rechercher extends JFrame {
    private JTextField idField;
    private JTextArea resultArea;
    private JButton searchButton;

    public Rechercher() {
        setTitle("Rechercher Contact");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel idLabel = new JLabel("ID Contact:");
        idLabel.setBounds(30, 30, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 30, 200, 25);
        add(idField);

        searchButton = new JButton("Rechercher");
        searchButton.setBounds(150, 70, 120, 30);
        add(searchButton);

        resultArea = new JTextArea();
        resultArea.setBounds(30, 120, 320, 100);
        resultArea.setEditable(false);
        add(resultArea);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
    }

    private void searchContact() {
        int id = Integer.parseInt(idField.getText());

        String sql = "SELECT * FROM contact WHERE id_Contact=?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String email = rs.getString("Email");

                resultArea.setText("Nom: " + nom + "\nPrénom: " + prenom + "\nEmail: " + email);
            } else {
                resultArea.setText("Contact non trouvé.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de recherche");
        }
    }
}

package view;

import javax.swing.*;
import utils.MySQLConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
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
        setLocationRelativeTo(null);

        
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/images/app_icon.png"));
        setIconImage(appIcon.getImage());
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        setContentPane(panel);

        JLabel idLabel = new JLabel("ID Contact:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        idLabel.setBounds(30, 30, 100, 25);
        panel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 30, 200, 25);
        panel.add(idField);

        searchButton = new JButton("Rechercher");
        searchButton.setBounds(150, 70, 120, 30);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Arial", Font.BOLD, 13));
        searchButton.setBackground(new Color(66, 133, 244));
        searchButton.setForeground(Color.WHITE);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setBorderPainted(false);
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(51, 103, 194));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(66, 133, 244));
            }
        });
        panel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setBounds(30, 120, 320, 100);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(resultArea);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
    }

    private void searchContact() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.");
        }
    }
}

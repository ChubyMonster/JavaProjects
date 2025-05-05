package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.MySQLConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Modifier extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Name, LastName, Libel, TelPerso, TelPro, Email;
    private JComboBox<String> Ville, Categorie;
    private JRadioButton F, H;
    private ButtonGroup genderGroup;
    private int idContact;

    public Modifier(int idContact, String nom, String prenom, String libelle, String sexe, String telPerso, String telPro, String email, int numCategorie, int numVille) {

        setTitle("Modifier Contact : " + nom + " " + prenom);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Modifier Contact");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel.setForeground(new Color(60, 60, 60));
        lblNewLabel.setBounds(10, 10, 300, 25);
        contentPane.add(lblNewLabel);

        JLabel[] labels = {
            new JLabel("Nom"), new JLabel("Prénom"), new JLabel("Libellé"),
            new JLabel("Téléphone Perso"), new JLabel("Téléphone Pro"), new JLabel("Email"),
            new JLabel("Sexe"), new JLabel("Ville"), new JLabel("Catégorie")
        };

        int y = 50;
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setForeground(new Color(50, 50, 50));
            label.setBounds(20, y, 150, 20);
            contentPane.add(label);
            y += 35;
        }

        Name = new JTextField(nom); Name.setBounds(180, 50, 150, 22); contentPane.add(Name);
        LastName = new JTextField(prenom); LastName.setBounds(180, 85, 150, 22); contentPane.add(LastName);
        Libel = new JTextField(libelle); Libel.setBounds(180, 120, 150, 22); contentPane.add(Libel);
        TelPerso = new JTextField(telPerso); TelPerso.setBounds(180, 155, 150, 22); contentPane.add(TelPerso);
        TelPro = new JTextField(telPro); TelPro.setBounds(180, 190, 150, 22); contentPane.add(TelPro);
        Email = new JTextField(email); Email.setBounds(180, 225, 150, 22); contentPane.add(Email);

        F = new JRadioButton("Femme"); F.setBounds(180, 260, 70, 22); F.setBackground(new Color(245, 245, 245)); contentPane.add(F);
        H = new JRadioButton("Homme"); H.setBounds(260, 260, 80, 22); H.setBackground(new Color(245, 245, 245)); contentPane.add(H);
        genderGroup = new ButtonGroup(); genderGroup.add(F); genderGroup.add(H);
        if (sexe.equalsIgnoreCase("F")) F.setSelected(true);
        else if (sexe.equalsIgnoreCase("H")) H.setSelected(true);

        Ville = new JComboBox<>(); Ville.setBounds(180, 295, 150, 22); contentPane.add(Ville);
        Categorie = new JComboBox<>(); Categorie.setBounds(180, 330, 150, 22); contentPane.add(Categorie);

        JButton btnUpdate = createStyledButton("Enregistrer", 370, 70, 150, 30);
        JButton btnCancel = createStyledButton("Annuler", 370, 120, 150, 30);
        contentPane.add(btnUpdate); contentPane.add(btnCancel);

        try (Connection conn = MySQLConnection.getConnection()) {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT Num_Ville, Nom_Ville FROM ville");
            while (rs1.next()) {
                String nomVille = rs1.getString("Nom_Ville");
                Ville.addItem(nomVille);
                if (rs1.getInt("Num_Ville") == numVille) Ville.setSelectedItem(nomVille);
            }
            rs1.close();

            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT Num_Categorie, Nom_Categorie FROM categorie");
            while (rs2.next()) {
                String nomCategorie = rs2.getString("Nom_Categorie");
                Categorie.addItem(nomCategorie);
                if (rs2.getInt("Num_Categorie") == numCategorie) Categorie.setSelectedItem(nomCategorie);
            }
            rs2.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur chargement des données: " + e.getMessage());
        }

        btnUpdate.addActionListener(this::updateContact);
        btnCancel.addActionListener(e -> {
            dispose();
            new MenuPrincipale(null).setVisible(true);
        });
    }

    private JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(new Color(66, 133, 244));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(51, 103, 194));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 133, 244));
            }
        });
        return button;
    }

    private void updateContact(ActionEvent e) {
        String nom = Name.getText().trim();
        String prenom = LastName.getText().trim();
        String libelle = Libel.getText().trim();
        String telPerso = TelPerso.getText().trim();
        String telPro = TelPro.getText().trim();
        String email = Email.getText().trim();
        String sexe = F.isSelected() ? "F" : H.isSelected() ? "H" : "";

        if (sexe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un sexe.");
            return;
        }

        String ville = (String) Ville.getSelectedItem();
        String categorie = (String) Categorie.getSelectedItem();

        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "UPDATE contact SET Nom=?, Prenom=?, Libelle=?, Sexe=?, TelPerso=?, TelProf=?, Email=?, " +
                         "Num_Categorie=(SELECT Num_Categorie FROM categorie WHERE Nom_Categorie=?), " +
                         "Num_Ville=(SELECT Num_Ville FROM ville WHERE Nom_Ville=?) WHERE id_contact=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, libelle);
            pstmt.setString(4, sexe);
            pstmt.setString(5, telPerso);
            pstmt.setString(6, telPro);
            pstmt.setString(7, email);
            pstmt.setString(8, categorie);
            pstmt.setString(9, ville);
            pstmt.setInt(10, idContact);

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "Contact modifié avec succès !");
                dispose();
                new MenuPrincipale(null).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Aucune modification effectuée !");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + ex.getMessage());
        }
    }
}

package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.MySQLConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window
        setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Modifier Contact ID: " + idContact + " - " + nom + " " + prenom);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        
        System.out.println("Modifier window created!");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Modifier Contact");
        lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblNewLabel.setBounds(10, 11, 200, 14);
        contentPane.add(lblNewLabel);

        // --- Add your fields
        Name = new JTextField(nom);
        Name.setBounds(100, 50, 120, 20);
        contentPane.add(Name);

        LastName = new JTextField(prenom);
        LastName.setBounds(100, 90, 120, 20);
        contentPane.add(LastName);

        Libel = new JTextField(libelle);
        Libel.setBounds(100, 130, 120, 20);
        contentPane.add(Libel);

        TelPerso = new JTextField(telPerso);
        TelPerso.setBounds(100, 170, 120, 20);
        contentPane.add(TelPerso);

        TelPro = new JTextField(telPro);
        TelPro.setBounds(100, 210, 120, 20);
        contentPane.add(TelPro);

        Email = new JTextField(email);
        Email.setBounds(100, 250, 120, 20);
        contentPane.add(Email);

        F = new JRadioButton("Femme");
        F.setBounds(250, 50, 100, 20);
        contentPane.add(F);

        H = new JRadioButton("Homme");
        H.setBounds(250, 80, 100, 20);
        contentPane.add(H);

        genderGroup = new ButtonGroup();
        genderGroup.add(F);
        genderGroup.add(H);

        if (sexe.equalsIgnoreCase("F")) F.setSelected(true);
        else if (sexe.equalsIgnoreCase("H")) H.setSelected(true);

        Ville = new JComboBox<>();
        Ville.setBounds(250, 130, 120, 20);
        contentPane.add(Ville);

        Categorie = new JComboBox<>();
        Categorie.setBounds(250, 170, 120, 20);
        contentPane.add(Categorie);

        // Load villes and categories
        try (Connection conn = MySQLConnection.getConnection()) {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM ville");
            while (rs1.next()) {
                Ville.addItem(rs1.getString("Nom_Ville"));
            }
            rs1.close();

            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM categorie");
            while (rs2.next()) {
                Categorie.addItem(rs2.getString("Nom_Categorie"));
            }
            rs2.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur chargement des données: " + e.getMessage());
        }

        JButton btnUpdate = new JButton("Enregistrer");
        btnUpdate.setBounds(100, 350, 120, 30);
        contentPane.add(btnUpdate);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        JButton btnCancel = new JButton("Annuler");
        btnCancel.setBounds(250, 350, 120, 30);
        contentPane.add(btnCancel);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new MenuPrincipale(null).setVisible(true); // Back to main menu
            }
        });
    }

    private void updateContact() {
        String nom = Name.getText();
        String prenom = LastName.getText();
        String libelle = Libel.getText();
        String telPerso = TelPerso.getText();
        String telPro = TelPro.getText();
        String email = Email.getText();
        String sexe = F.isSelected() ? "F" : H.isSelected() ? "H" : "";
        String ville = (String) Ville.getSelectedItem();
        String categorie = (String) Categorie.getSelectedItem();

        if (sexe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un sexe.");
            return;
        }

        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "UPDATE contact SET Nom=?, Prenom=?, Libelle=?, Sexe=?, TelPerso=?, TelProf=?, Email=?, Num_Categorie=(SELECT Num_Categorie FROM categorie WHERE Nom_Categorie=?), Num_Ville=(SELECT Num_Ville FROM ville WHERE Nom_Ville=?) WHERE id_contact=?";
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
                JOptionPane.showMessageDialog(null, "Contact modifié avec succès !");
                dispose();
                new MenuPrincipale(null).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Erreur lors de la modification !");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur SQL : " + ex.getMessage());
        }
    }
}

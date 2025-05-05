package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.ContactController;
import model.Contact;
import utils.MySQLConnection;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.awt.Color;
import java.awt.Cursor;

public class MenuPrincipale extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private ContactController controller;

    public MenuPrincipale(Connection connection) {

        setTitle("Menu Principal");
        controller = new ContactController();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 912, 568);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setLocationRelativeTo(null);

        // Table model and JTable setup
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] {
            "ID", "Nom", "Prénom", "Libelle", "Sexe", "Tel Perso", "Tel Pro", "Email", "Catégorie", "Ville"
        });

        table = new JTable(model);
        table.setBounds(147, 37, 727, 438);
        contentPane.add(table);

        loadData(model);

        JLabel lblNewLabel = new JLabel("Liste des Contacts");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNewLabel.setForeground(new Color(60, 60, 60));
        lblNewLabel.setBounds(154, 12, 194, 20);
        contentPane.add(lblNewLabel);

        // Styled Buttons
        JButton AfficherTout = createStyledButton("Afficher Tout", 10, 86, 127, 55);
        AfficherTout.addActionListener(e -> loadData(model));
        contentPane.add(AfficherTout);

        JButton Imprimer = createStyledButton("Imprimer", 10, 152, 127, 23);
        contentPane.add(Imprimer);

        JButton Ajouter = createStyledButton("Ajouter", 17, 486, 158, 30);
        Ajouter.addActionListener(e -> new Ajouter().setVisible(true));
        contentPane.add(Ajouter);

        JButton Modifier = createStyledButton("Modifier", 192, 486, 158, 30);
        Modifier.addActionListener(e -> ModifyData());
        contentPane.add(Modifier);

        JButton Supprimer = createStyledButton("Supprimer", 367, 486, 158, 30);
        Supprimer.addActionListener(e -> DeleteData());
        contentPane.add(Supprimer);

        JButton Rechercher = createStyledButton("Rechercher", 542, 486, 158, 30);
        Rechercher.addActionListener(e -> new Rechercher().setVisible(true));
        contentPane.add(Rechercher);

        JButton Quitter = createStyledButton("Quitter", 717, 486, 158, 30);
        Quitter.addActionListener(e -> dispose());
        contentPane.add(Quitter);
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

    private void loadData(DefaultTableModel model) {
        DefaultTableModel model1 = (DefaultTableModel) table.getModel();
        model1.setRowCount(0);
        List<Contact> contacts = controller.fetchContacts();
        for (Contact c : contacts) {
            model1.addRow(new Object[] {
                c.getId(), c.getNom(), c.getPrenom(), c.getLibelle(), c.getSexe(),
                c.getTelPerso(), c.getTelPro(), c.getEmail(), c.getNumCategorie(), c.getNumVille()
            });
        }
    }

    private void DeleteData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un contact !");
            return;
        }

        int id = (int) table.getValueAt(row, 0);

        if (controller.removeContact(id)) {
            JOptionPane.showMessageDialog(this, "Contact supprimé !");
            loadData(new DefaultTableModel());
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
        }
    }

    private void ModifyData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un contact à modifier.");
            return;
        }
        Object value = table.getValueAt(selectedRow, 0);
        if (value == null) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer l'identifiant du contact.");
            return;
        }

        try {
            int idContact = (int) table.getValueAt(selectedRow, 0);
            String nom = (String) table.getValueAt(selectedRow, 1);
            String prenom = (String) table.getValueAt(selectedRow, 2);
            String libelle = (String) table.getValueAt(selectedRow, 3);
            String sexe = (String) table.getValueAt(selectedRow, 4);
            String telPerso = (String) table.getValueAt(selectedRow, 5);
            String telPro = (String) table.getValueAt(selectedRow, 6);
            String email = (String) table.getValueAt(selectedRow, 7);
            int numCategorie = (int) table.getValueAt(selectedRow, 8);
            int numVille = (int) table.getValueAt(selectedRow, 9);

            Modifier modifFrame = new Modifier(idContact, nom, prenom, libelle, sexe, telPerso, telPro, email, numCategorie, numVille);
            modifFrame.setVisible(true);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture de Modifier : " + ex.getMessage());
        }
    }
}

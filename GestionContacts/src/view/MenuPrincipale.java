package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ContactController;
import model.Contact;
import utils.Auth;
import utils.MySQLConnection;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class MenuPrincipale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private static Connection connection;

	/**
	 * Launch the application.
	 */

	private JTable table1;
    private ContactController controller;
    
	public MenuPrincipale(Connection connection) {
		
		 controller = new ContactController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel();
		
		table = new JTable(model);
		table.setBounds(147, 37, 727, 438);
		contentPane.add(table);
		
		model.setColumnIdentifiers(new Object[] {
	            "ID", "Nom", "Prénom", "Libelle", "Sexe", "Tel Perso", "Tel Pro", "Email", "Catégorie", "Ville"
	        });
		
		loadData(model);
		
		JLabel lblNewLabel = new JLabel("Liste des Contacts");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNewLabel.setBounds(154, 12, 194, 14);
		contentPane.add(lblNewLabel);
		
		JButton AfficherTout = new JButton("Afficher Tout");
		AfficherTout.setBounds(10, 86, 127, 55);
		AfficherTout.addActionListener(e -> loadData(model));
		contentPane.add(AfficherTout);
		
		JButton Imprimer = new JButton("Imprimer");
		Imprimer.setBounds(10, 152, 127, 23);
		contentPane.add(Imprimer);
		
		JButton Ajouter = new JButton("Ajouter");
		Ajouter.setBounds(17, 486, 158, 23);
		contentPane.add(Ajouter);
		
		Ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {new Ajouter().setVisible(true);}
		});
		
		JButton Modifier = new JButton("Modifier");
		Modifier.setBounds(192, 486, 158, 23);
		Modifier.addActionListener(e -> ModifyData());
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("Supprimer");
		Supprimer.setBounds(367, 486, 158, 23);
		Supprimer.addActionListener(e -> DeleteData());
		contentPane.add(Supprimer);
		
		JButton Rechercher = new JButton("Rechercher");
		Rechercher.setBounds(542, 486, 158, 23);
		contentPane.add(Rechercher);
		
		JButton Quitter = new JButton("Quitter");
		Quitter.setBounds(717, 486, 158, 23);
		Quitter.addActionListener(e -> dispose());
		contentPane.add(Quitter);

		Rechercher.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        new Rechercher().setVisible(true);
		    }
		});
	}
	private void loadData(DefaultTableModel model) {
		DefaultTableModel model1 = (DefaultTableModel) table.getModel();
        model1.setRowCount(0); // Clear old data

        List<Contact> contacts = controller.fetchContacts();
        for (Contact c : contacts) {
            model1.addRow(new Object[] {
                c.getId(), c.getNom(), c.getPrenom(), c.getLibelle(), c.getSexe(), c.getTelPerso(),
                c.getTelPro(), c.getEmail(), c.getNumCategorie(), c.getNumVille()
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
            loadData(null);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
        }
    }
	private void ModifyData() {
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow != -1) {
	        try {
	            System.out.println("Row selected: " + selectedRow); // 🛑 DEBUG STEP 1

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

	            System.out.println("Opening Modifier Frame with ID: " + idContact); // 🛑 DEBUG STEP 2

	            Modifier modifFrame = new Modifier(idContact, nom, prenom, libelle, sexe, telPerso, telPro, email, numCategorie, numVille);
	            modifFrame.setVisible(true);
	            dispose();
	        } catch (Exception ex) {
	            ex.printStackTrace(); // 🛑 DEBUG STEP 3
	            JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture de Modifier : " + ex.getMessage());
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un contact à modifier !");
	    }
	}

}
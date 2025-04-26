package src1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import src2.Auth;
import src2.MySQLConnection;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MenuPrincipale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipale frame = new MenuPrincipale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuPrincipale() {
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
		
		JButton Modifier = new JButton("Supprimer");
		Modifier.setBounds(192, 486, 158, 23);
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("Modifier");
		Supprimer.setBounds(367, 486, 158, 23);
		contentPane.add(Supprimer);
		
		JButton Rechercher = new JButton("Rechercher");
		Rechercher.setBounds(542, 486, 158, 23);
		contentPane.add(Rechercher);
		
		JButton Quitter = new JButton("Quitter");
		Quitter.setBounds(717, 486, 158, 23);
		contentPane.add(Quitter);
}
	private void loadData(DefaultTableModel model) {
		Connection conn = MySQLConnection.getConnection();
		
        String query = "SELECT * FROM contact";
        
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id_Contact"),
                    rs.getString("Nom"),
                    rs.getString("Prenom"),
                    rs.getString("Libelle"),
                    rs.getString("Sexe"),
                    rs.getString("TelPerso"),
                    rs.getString("TelProf"),
                    rs.getString("Email"),
                    rs.getInt("Num_Categrorie"),
                    rs.getInt("Num_Ville")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
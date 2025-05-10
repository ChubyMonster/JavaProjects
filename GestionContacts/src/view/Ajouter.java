package view;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.MySQLConnection;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Cursor;

public class Ajouter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Name;
	private JTextField LastName;
	private JTextField Libel;
	private JTextField TelPerso;
	private JTextField MailAdresse;
	private JTextField TelPro;
	private JComboBox<String> Ville;
	private JComboBox<String> Categorie;
	private JRadioButton F, H;
	private ButtonGroup genderGroup;

	private Map<String, Integer> villesMap = new HashMap<>();
	private Map<String, Integer> categoriesMap = new HashMap<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Ajouter frame = new Ajouter();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Ajouter() {
		setTitle("Ajouter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 470);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Ajouter Contact");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(60, 60, 60));
		lblNewLabel.setBounds(20, 11, 300, 20);
		contentPane.add(lblNewLabel);
		
		ImageIcon appIcon = new ImageIcon(getClass().getResource("/images/app_icon.png"));
        setIconImage(appIcon.getImage());

		String[] labels = {"Nom", "Prénom", "Libellé", "Téléphone Perso", "Téléphone Pro", "Email", "Catégorie", "Sexe", "Ville"};
		int y = 50;
		for (String labelText : labels) {
			JLabel label = new JLabel(labelText);
			label.setFont(new Font("Arial", Font.PLAIN, 13));
			label.setForeground(new Color(50, 50, 50));
			label.setBounds(20, y, 150, 20);
			contentPane.add(label);
			y += 35;
		}

		Name = new JTextField(); Name.setBounds(180, 50, 150, 22); contentPane.add(Name);
		LastName = new JTextField(); LastName.setBounds(180, 85, 150, 22); contentPane.add(LastName);
		Libel = new JTextField(); Libel.setBounds(180, 120, 150, 22); contentPane.add(Libel);
		TelPerso = new JTextField(); TelPerso.setBounds(180, 155, 150, 22); contentPane.add(TelPerso);
		TelPro = new JTextField(); TelPro.setBounds(180, 190, 150, 22); contentPane.add(TelPro);
		MailAdresse = new JTextField(); MailAdresse.setBounds(180, 225, 150, 22); contentPane.add(MailAdresse);
		Categorie = new JComboBox<>(); Categorie.setBounds(180, 260, 150, 22); contentPane.add(Categorie);
		F = new JRadioButton("Femme"); F.setBounds(180, 295, 70, 23); F.setBackground(new Color(245, 245, 245)); contentPane.add(F);
		H = new JRadioButton("Homme"); H.setBounds(260, 295, 70, 23); H.setBackground(new Color(245, 245, 245)); contentPane.add(H);
		genderGroup = new ButtonGroup(); genderGroup.add(F); genderGroup.add(H);
		Ville = new JComboBox<>(); Ville.setBounds(180, 330, 150, 22); contentPane.add(Ville);

		JButton Valider = createStyledButton("Valider", 400, 70, 130, 30);
		JButton Annuler = createStyledButton("Annuler", 400, 120, 130, 30);
		JButton Quitter = createStyledButton("Quitter", 400, 170, 130, 30);
		contentPane.add(Valider); contentPane.add(Annuler); contentPane.add(Quitter);

		loadComboBoxes();
		Valider.addActionListener(e -> insertContact());
		Annuler.addActionListener(e -> clearFields());
		Quitter.addActionListener(e -> dispose());
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

	private void loadComboBoxes() {
		try (Connection conn = MySQLConnection.getConnection()) {
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT Num_Ville, Nom_Ville FROM ville");
			while (rs1.next()) {
				villesMap.put(rs1.getString("Nom_Ville"), rs1.getInt("Num_Ville"));
				Ville.addItem(rs1.getString("Nom_Ville"));
			}
			rs1.close();

			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT Num_Categorie, Nom_Categorie FROM categorie");
			while (rs2.next()) {
				categoriesMap.put(rs2.getString("Nom_Categorie"), rs2.getInt("Num_Categorie"));
				Categorie.addItem(rs2.getString("Nom_Categorie"));
			}
			rs2.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erreur de chargement: " + e.getMessage());
		}
	}

	private void insertContact() {
		String nom = Name.getText().trim();
		String prenom = LastName.getText().trim();
		String libelle = Libel.getText().trim();
		String telPerso = TelPerso.getText().trim();
		String telPro = TelPro.getText().trim();
		String email = MailAdresse.getText().trim();
		String selectedVille = (String) Ville.getSelectedItem();
		String selectedCategorie = (String) Categorie.getSelectedItem();

		String sexe = F.isSelected() ? "F" : H.isSelected() ? "H" : "";
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		String phoneRegex = "^(\\+212|00212|0)([5-7])\\d{8}$";

		if (sexe.isEmpty() || selectedVille == null || selectedCategorie == null ||
			nom.isEmpty() || prenom.isEmpty() || libelle.isEmpty() || email.isEmpty() ||
			telPerso.isEmpty() || telPro.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
			return;
		}
		if (!email.matches(emailRegex)) {
			JOptionPane.showMessageDialog(this, "Adresse email invalide.");
			return;
		}
		if (!telPerso.matches(phoneRegex) || !telPro.matches(phoneRegex)) {
			JOptionPane.showMessageDialog(this, "Numéro de téléphone invalide.");
			return;
		}

		try (Connection conn = MySQLConnection.getConnection()) {
			String sql = "INSERT INTO contact (Nom, Prenom, Libelle, Sexe, TelPerso, TelProf, Email, Num_Categorie, Num_Ville) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setString(3, libelle);
			pstmt.setString(4, sexe);
			pstmt.setString(5, telPerso);
			pstmt.setString(6, telPro);
			pstmt.setString(7, email);
			pstmt.setInt(8, categoriesMap.get(selectedCategorie));
			pstmt.setInt(9, villesMap.get(selectedVille));
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Contact ajouté avec succès !");
			clearFields();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion : " + e.getMessage());
		}
	}

	private void clearFields() {
		Name.setText(""); LastName.setText(""); Libel.setText(""); TelPerso.setText("");
		TelPro.setText(""); MailAdresse.setText(""); Categorie.setSelectedIndex(0);
		Ville.setSelectedIndex(0); genderGroup.clearSelection();
	}
}

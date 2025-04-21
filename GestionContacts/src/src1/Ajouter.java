package src1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class Ajouter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Name;
	private JTextField LastName;
	private JTextField Libel;
	private JTextField TelPerso;
	private JTextField MailAdresse;
	private JTextField Cat;
	private JTextField TelPro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ajouter frame = new Ajouter();
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
	public Ajouter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ajouter Contact");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 134, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_1.setBounds(10, 59, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Prénom");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_2.setBounds(10, 118, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Libélle");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_3.setBounds(10, 178, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Téléphone");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_4.setBounds(10, 239, 78, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Email");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_5.setBounds(10, 300, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Catégorie");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_6.setBounds(10, 361, 78, 14);
		contentPane.add(lblNewLabel_6);
		
		Name = new JTextField();
		Name.setBounds(98, 59, 86, 20);
		contentPane.add(Name);
		Name.setColumns(10);
		
		LastName = new JTextField();
		LastName.setBounds(98, 118, 86, 20);
		contentPane.add(LastName);
		LastName.setColumns(10);
		
		Libel = new JTextField();
		Libel.setBounds(98, 178, 86, 20);
		contentPane.add(Libel);
		Libel.setColumns(10);
		
		TelPerso = new JTextField();
		TelPerso.setBounds(98, 239, 86, 20);
		contentPane.add(TelPerso);
		TelPerso.setColumns(10);
		
		MailAdresse = new JTextField();
		MailAdresse.setBounds(98, 300, 86, 20);
		contentPane.add(MailAdresse);
		MailAdresse.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Sexe");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_7.setBounds(375, 59, 46, 14);
		contentPane.add(lblNewLabel_7);
		
		Cat = new JTextField();
		Cat.setBounds(98, 361, 86, 20);
		contentPane.add(Cat);
		Cat.setColumns(10);
		
		TelPro = new JTextField();
		TelPro.setBounds(214, 239, 86, 20);
		contentPane.add(TelPro);
		TelPro.setColumns(10);
		
		JRadioButton F = new JRadioButton("Femme");
		F.setBounds(443, 96, 109, 23);
		contentPane.add(F);
		
		JRadioButton H = new JRadioButton("Homme");
		H.setBounds(332, 96, 109, 23);
		contentPane.add(H);
		
		JLabel lblNewLabel_8 = new JLabel("Ville");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.ITALIC, 10));
		lblNewLabel_8.setBounds(375, 161, 46, 14);
		contentPane.add(lblNewLabel_8);
		
		JComboBox Ville = new JComboBox();
		Ville.setBounds(375, 197, 109, 22);
		contentPane.add(Ville);
		
		JButton Annuler = new JButton("Annuler");
		Annuler.setBounds(375, 333, 109, 23);
		contentPane.add(Annuler);
		
		JButton Quitter = new JButton("Quitter");
		Quitter.setBounds(375, 357, 109, 23);
		contentPane.add(Quitter);
		
		JButton Valider = new JButton("Valider");
		Valider.setBounds(375, 310, 109, 23);
		contentPane.add(Valider);
	}
}

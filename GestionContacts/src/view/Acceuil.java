package src1;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import src2.*;
public class Acceuil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField passwordField;
	private JTextField usernameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Acceuil frame = new Acceuil();
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
	public Acceuil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton Connection = new JButton("Se Connecter");
		Connection.setBounds(283, 211, 165, 23);
		contentPane.add(Connection);
		
		passwordField = new JTextField();
		passwordField.setBounds(283, 144, 165, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setBounds(283, 88, 165, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Num d'Utilisateur");
		lblNewLabel.setBounds(43, 91, 165, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mot de Passe");
		lblNewLabel_1.setBounds(43, 147, 165, 14);
		contentPane.add(lblNewLabel_1);
		
		Connection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String Username = usernameField.getText();
				String Password = passwordField.getText();
				
				src2.Auth Auth = new Auth();
				boolean success = Auth.authenticate(Username, Password);
				
				if (success) {
					JOptionPane.showMessageDialog(lblNewLabel_1, "Login successful!");
					
					Connection conn = MySQLConnection.getConnection();
	                new MenuPrincipale().setVisible(true);

	                // Close the login frame
	                dispose();
	            } else {
	                JOptionPane.showMessageDialog(lblNewLabel_1, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		
	}
}

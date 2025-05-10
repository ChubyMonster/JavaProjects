package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import utils.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Acceuil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField passwordField;
	private JTextField usernameField;

	private Connection connection;

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

	public Acceuil() {

		setTitle("Accueil");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        ImageIcon appIcon = new ImageIcon(getClass().getResource("/images/app_icon.png"));
        setIconImage(appIcon.getImage());
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/app_logo.png"));
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/AuthSucc.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/AuthFail.png"));
        Image scaled = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);
        
        JLabel welcomeLabel = new JLabel("Contact Management App");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy++;
        panel.add(welcomeLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Num d'Utilisateur:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Mot de Passe:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JButton loginButton = new JButton("Se Connecter");
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(66, 133, 244)));
        loginButton.setPreferredSize(new Dimension(140, 35));

        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(66, 133, 244)),
                BorderFactory.createEmptyBorder(5, 20, 5, 20)));

        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loginButton.setBackground(new Color(51, 103, 194));
            }

            public void mouseExited(MouseEvent evt) {
                loginButton.setBackground(new Color(66, 133, 244));
            }
        });

        panel.add(loginButton, gbc);

        add(panel);
        setVisible(true);
		
		connection = MySQLConnection.getConnection();
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				String Username = usernameField.getText();
				String Password = passwordField.getText();

				utils.Auth Auth = new Auth();
				boolean success = Auth.authenticate(Username, Password);

				if (success) {
					JOptionPane.showMessageDialog(null, "Login successful!","Authentification",JOptionPane.INFORMATION_MESSAGE, icon1);

					Connection connection = MySQLConnection.getConnection();
	                new MenuPrincipale(connection).setVisible(true);
	                dispose();
	            } else {
	                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE, icon2);
	            }
			}
		});

	}
}

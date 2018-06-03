package menuPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.util.Arrays;
import java.sql.*;

public class LoginScreen {
	
	

	private JFrame frame;
	private JTextField textFieldUsername;
	private JPasswordField textFieldPassword;
	private String enteredUsername;
	private char[] enteredPassword;
	private String finalPass = "";
	//private String registeredUsername;
	//private char[] registeredPassword;
	
	
	DatabaseConnection connection = new DatabaseConnection();
	
	private void getEnteredUsername() {
		enteredUsername = textFieldUsername.getText();
	}
	
	private void getEnteredPassword() {
		enteredPassword = textFieldPassword.getPassword();
	}
	
	private void resetTextFields() {
		
		textFieldUsername.setText("");
		textFieldPassword.setText("");
		
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginScreen() {
		enteredUsername = "";
		enteredPassword = new char[10];
		//registeredUsername = "";
		//registeredPassword = new char[10];
		initialize();
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 451, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcomeUser = new JLabel("Welcome user!");
		lblWelcomeUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeUser.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblWelcomeUser.setBounds(120, 11, 176, 43);
		frame.getContentPane().add(lblWelcomeUser);
		
		JLabel lblEnterDetails = new JLabel("Please enter your login details:");
		lblEnterDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEnterDetails.setBounds(77, 65, 272, 43);
		frame.getContentPane().add(lblEnterDetails);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldUsername.setBounds(151, 144, 119, 34);
		frame.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(151, 214, 119, 34);
		frame.getContentPane().add(textFieldPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsername.setBounds(161, 119, 103, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(161, 189, 103, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TransferClass transfer = TransferClass.getObj();
				getEnteredUsername();
				getEnteredPassword();
				//registeredUsername = transfer.getRegUser();
				//registeredPassword = transfer.getRegPass();
				
				//JOptionPane.showMessageDialog(null, "The passed username is " +registeredUsername);
				//JOptionPane.showMessageDialog(null, "The passed password is " +registeredPassword);
				
				if (enteredUsername.equals("") || enteredPassword.length==0) {
					JOptionPane.showMessageDialog(frame, "Please enter a value in both fields!", "Blank fields error", JOptionPane.ERROR_MESSAGE);
					resetTextFields();
					return;
					
				}
				else {
					
					finalPass = new String(enteredPassword);
					
					if (connection.selectData(enteredUsername, finalPass) == true) {
						
						JOptionPane.showMessageDialog(null, "Congratulations!");
						resetTextFields();
					
					}
					else {
						
						JOptionPane.showMessageDialog(null, "The username or password you have entered is incorrect.");
						resetTextFields();
					}
				}
				
				
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogin.setBounds(165, 255, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				RegScreen regObj = new RegScreen();
				frame.setVisible(false);
				frame.dispose();
				regObj.setVisible(true);
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegister.setBounds(151, 289, 119, 23);
		frame.getContentPane().add(btnRegister);
	}
}

package menuPackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.Arrays;
import javax.swing.SwingConstants;

public class RegScreen extends JDialog {
	

	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldAgain;
	private char[] registeredPassword = new char[10];
	private char[] passwordAgain = new char[10];
	private String registeredUsername = "";
	private boolean emptyFieldsResult;
	private boolean passwordsResult;
	private String endPass = "";
	
	private void getUsername() {
		registeredUsername = textFieldUsername.getText();
	}
	
	private void getPassword() {
		registeredPassword = passwordField.getPassword();
	}
	
	private void getPasswordAgain() {
		passwordAgain = passwordFieldAgain.getPassword();
	}
	
	private void resetText() {
		textFieldUsername.setText("");
		passwordField.setText("");
		passwordFieldAgain.setText("");
	}
	
	private boolean checkEmptyFields(String name, char[] pass, char[] passAgain) {
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter an username!");
			resetText();
			return false;
		}
		else if(pass.length == 0) {
			JOptionPane.showMessageDialog(null, "Please enter a password!");
			resetText();
			return false;
		}
		else if(passAgain.length == 0) {
			JOptionPane.showMessageDialog(null, "Please repeat your password!");
			resetText();
			return false;
		}
		else {
			
			return true;
		}
		
	}
	
	private boolean checkPasswords(char[] passFirst, char[] passRepeat) {
		
		if(Arrays.equals(passFirst, passRepeat)) {
			return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Please make sure the two passwords are the same!");
			resetText();
			return false;
		}
	}
	
	public String getRegisteredUsernameTrans() {
		return registeredUsername;
	}
	
	public char[] getPasswordAgainTrans() {
		return passwordAgain; 
	}
	

	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegScreen dialog = new RegScreen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegScreen() {
		super((java.awt.Frame) null, true);
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton registerButton = new JButton("Register");
			registerButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
			registerButton.setBounds(180, 194, 93, 23);
			contentPanel.add(registerButton);
			
			registerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					getUsername();
					getPassword();
					getPasswordAgain();
					emptyFieldsResult = checkEmptyFields(registeredUsername, registeredPassword, passwordAgain);
					
				
						
					
					if(emptyFieldsResult == true) {
						
					
						if(registeredUsername.length() < 6 || registeredUsername.length() > 20) {
							
							JOptionPane.showMessageDialog(null, "The entered username must be between 6 and 20 characters long!", "Username length error", JOptionPane.ERROR_MESSAGE);
							
						}
						else if(registeredPassword.length < 6 || registeredPassword.length > 20) {
							
							JOptionPane.showMessageDialog(null, "The entered password must be between 6 and 20 characters long!", "Password length error", JOptionPane.ERROR_MESSAGE);
							
						}
						
						else {
							passwordsResult = checkPasswords(registeredPassword, passwordAgain);
						
							if(passwordsResult == true) {
							
							JOptionPane.showMessageDialog(null, "You have successfully registered!");
							
							DatabaseConnection connect = new DatabaseConnection();
							endPass = new String(passwordAgain);
							connect.insertDetails(registeredUsername, endPass);
							
							resetText();
						}
						
						else {
							//JOptionPane.showMessageDialog(null, "There seems to have been a problem with your registration.");
							//resetText();
						}
					 }
					
					}
					
				}
			});
			registerButton.setActionCommand("OK");
			getRootPane().setDefaultButton(registerButton);
		}
		
		textFieldUsername = new JTextField();
		textFieldUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldUsername.setBounds(144, 55, 160, 20);
		contentPanel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordField.setBounds(144, 108, 160, 20);
		contentPanel.add(passwordField);
		
		passwordFieldAgain = new JPasswordField();
		passwordFieldAgain.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordFieldAgain.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordFieldAgain.setBounds(144, 163, 160, 20);
		contentPanel.add(passwordFieldAgain);
		
		JLabel lblEnterUsername = new JLabel("Please enter an username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterUsername.setBounds(125, 23, 197, 29);
		contentPanel.add(lblEnterUsername);
		
		JLabel lblEnterPassword = new JLabel("Please enter a password:");
		lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterPassword.setBounds(135, 86, 197, 23);
		contentPanel.add(lblEnterPassword);
		
		JLabel lblPasswordAgain = new JLabel("Please enter your password again:");
		lblPasswordAgain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPasswordAgain.setBounds(125, 139, 235, 23);
		contentPanel.add(lblPasswordAgain);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton backButton = new JButton("Back");
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						contentPanel.setVisible(false);
						dispose();
						LoginScreen.main(null);
						
					}
				});
				backButton.setActionCommand("Cancel");
				buttonPane.add(backButton);
			}
		}
	}
}

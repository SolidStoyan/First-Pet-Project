package menuPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.ImageIcon;

public class HubMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					HubMenu frame = new HubMenu();
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
	public HubMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGoodDayUser = new JLabel("Good day user!");
		lblGoodDayUser.setBounds(5, 5, 506, 39);
		lblGoodDayUser.setVerticalAlignment(SwingConstants.TOP);
		lblGoodDayUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoodDayUser.setFont(new Font("Tahoma", Font.ITALIC, 32));
		contentPane.add(lblGoodDayUser);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 88, 501, 295);
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton btnToCalculator = new JButton("Calculator");
		btnToCalculator.setIcon(null);
		btnToCalculator.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnToCalculator.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnToCalculator);
		
		JLabel lblNewLabel = new JLabel("You are currently in the:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(103, 48, 301, 29);
		contentPane.add(lblNewLabel);
	}
}

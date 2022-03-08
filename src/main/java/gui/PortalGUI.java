package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PortalGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtSelectAnOption;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortalGUI frame = new PortalGUI();
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
	public PortalGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton signupButton = new JButton("Sign up");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// registerGUI
				try {
					UserRegisterGUI registerGui = new UserRegisterGUI();
					registerGui.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		signupButton.setBounds(79, 115, 85, 21);
		contentPane.add(signupButton);
		
		JButton loginButton = new JButton("Log in");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// entrar a loginGUI
				try {
					UserLoginGUI loginGui = new UserLoginGUI();
					loginGui.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		loginButton.setBounds(254, 115, 85, 21);
		contentPane.add(loginButton);
		
		txtSelectAnOption = new JTextField();
		txtSelectAnOption.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSelectAnOption.setText("Select an option:");
		txtSelectAnOption.setBounds(152, 46, 121, 42);
		contentPane.add(txtSelectAnOption);
		txtSelectAnOption.setColumns(10);
	}
}

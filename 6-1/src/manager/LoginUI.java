package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginUI {

	 static String address;
	 static int port;
	 static String name;
	private JFrame frame;
	private JTextField txtManager;
	public static ManagerUI createWhiteBoard ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//check the arguments
		if (args.length == 3) {
			try {
				address = args[0];
				port = Integer.parseInt(args[1]);
				name = args[2];
			} catch (Exception e) {
				System.out.println("Argument error!");
				System.exit(1);
			}
		} else {
			address = "localhost";
			port = 3333;
			name = "manager";
			System.out.println("Starts with default arguments");
		}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new LoginUI();
				} catch (Exception e) {
					System.out.println("Failed to start Manager Login Window!");
				}
			}
		});
		//start a server management
		StartServer.start(port, name);
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Listener createListener = new Listener();

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		txtManager = new JTextField();
		txtManager.setBounds(193, 83, 199, 66);
		frame.getContentPane().add(txtManager);
		txtManager.setColumns(10);
		//set textField to user name 
		txtManager.setText(name);
		
		JLabel lblNewLabelEnterName = new JLabel("Enter your name:");
		lblNewLabelEnterName.setForeground(Color.BLUE);
		lblNewLabelEnterName.setBounds(45, 99, 152, 35);
		frame.getContentPane().add(lblNewLabelEnterName);

		
		JButton btnNewButtonLogin = new JButton("Login");
		btnNewButtonLogin.addActionListener(createListener);
		btnNewButtonLogin.addActionListener(e -> {
			if("Login".equals(e.getActionCommand())) {
				name = txtManager.getText();
				frame.dispose();
				try {
					createWhiteBoard = new ManagerUI(name);
					createWhiteBoard.setFrame(createWhiteBoard);
				} catch (Exception e1) {
					System.out.println("Failed to create a WihiteBoard window.");
				}
			}
		});
		btnNewButtonLogin.setBounds(153, 197, 117, 29);
		frame.getContentPane().add(btnNewButtonLogin);
	}
}

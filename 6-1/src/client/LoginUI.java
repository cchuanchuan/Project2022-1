package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author Xi WANG
 */
public class LoginUI {

	 static String address;
	 static int port;
	 static String name;

	private static JFrame frame;
	private JTextField txtManager;

	public static ClientUI guestUI;
	public static Connection connection;
	public static Socket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

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
			name = "client";
			System.out.println("Starts with default arguments");
		}

		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			System.out.println("Failed to connect to the Manager.");
			System.exit(1);
		}
		connection = new Connection(socket);


		EventQueue.invokeLater(() -> {
			try {
				 new LoginUI();
			} catch (Exception e) {
				System.out.println("Login window open error!");
			}
		});
		connection.start();
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

		Listener listener = new Listener() ;

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		JLabel lblNewLabelEnterName = new JLabel("Enter your name:");
		lblNewLabelEnterName.setForeground(Color.BLUE);
		lblNewLabelEnterName.setBounds(45, 99, 152, 35);
		frame.getContentPane().add(lblNewLabelEnterName);
		lblNewLabelEnterName.setVisible(true);

		txtManager = new JTextField();
		txtManager.setBounds(193, 83, 199, 66);
		frame.getContentPane().add(txtManager);
		txtManager.setColumns(10);
		//set textField to user name
		txtManager.setText(name);

		JButton btnNewButtonLogin = new JButton("Login");
		btnNewButtonLogin.addActionListener(listener);
		btnNewButtonLogin.setVisible(true);
		btnNewButtonLogin.setBounds(153, 197, 117, 29);

		btnNewButtonLogin.addActionListener(e -> {
			if(e.getActionCommand().equals("Login")) {
				name = txtManager.getText();
				try {
					connection.dataOutputStream.writeUTF("request " + name);
					int time = 0;
					while ( connection.getCurStatus().equals("wait")  &&  time < 100000) {
						TimeUnit.MILLISECONDS.sleep(100);
						time+= 100;
                    }

                    String result = connection.getCurStatus();

					switch (result) {
						case "no":
							JOptionPane.showMessageDialog(frame, "name has been used!");
							connection.setStatus();
							break;
						case "refused":
							JOptionPane.showMessageDialog(frame, "Rejected by manager!");
							frame.dispose();
							try {
								connection.dataOutputStream.writeUTF("done");
								connection.dataOutputStream.flush();
								socket.close();
								System.exit(1);
							} catch (Exception e1) {
								System.out.println("connection error");
							}
							break;
						case "yes":
							frame.dispose();
							if (guestUI == null) {
								guestUI = new ClientUI(connection,name);
							}
							break;
						default:
							JOptionPane.showMessageDialog(frame, "Time out, try again.");
							socket.close();
							System.exit(1);
							frame.dispose();
					}
				} catch (IOException | InterruptedException ioException) {
					System.out.println("Failed to login.");
				}
			}
		});


		frame.getContentPane().add(btnNewButtonLogin);
        frame.setVisible(true);

	}

}

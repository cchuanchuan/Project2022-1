package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Xi WANG
 */
public class OpenFile {

	JFrame frameOpen;
	private JTextField textFiledName;
	private JTextField txtNoSuchFile;
	private ManagerUI board;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					OpenFIle window = new OpenFIle();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public OpenFile() {initialize();}


	public OpenFile(ManagerUI board) {
		this.board = board;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameOpen = new JFrame();
		frameOpen.setTitle("Open file.");
		frameOpen.setBounds(100, 100, 450, 300);
		///////////&&&&&&&&&&&
		frameOpen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameOpen.getContentPane().setLayout(null);

		textFiledName = new JTextField();
		textFiledName.setText("whiteBoard");
		textFiledName.setBounds(148, 84, 256, 32);
		frameOpen.getContentPane().add(textFiledName);
		textFiledName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("file Path");
		lblNewLabel.setBounds(51, 92, 75, 16);
		frameOpen.getContentPane().add(lblNewLabel);
		
		JButton btnNewButtonOpen = new JButton("open");
		btnNewButtonOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String  name = textFiledName.getText();
				try {
					new Scanner(new FileInputStream(name));
				} catch (FileNotFoundException fileNotFoundException) {
					txtNoSuchFile.setVisible(true);
					JOptionPane.showMessageDialog(frameOpen,"No such file!");
					return;
				}
				try {
					board.open(name);
				} catch (IOException ioException) {
					System.out.println("IO exception");
				}
				frameOpen.dispose();

			}
		});

		btnNewButtonOpen.setBounds(162, 212, 117, 29);
		frameOpen.getContentPane().add(btnNewButtonOpen);
		
		txtNoSuchFile = new JTextField();
		txtNoSuchFile.setForeground(SystemColor.inactiveCaptionText);
		txtNoSuchFile.setEditable(false);
		txtNoSuchFile.setBackground(SystemColor.control);
		txtNoSuchFile.setText("No such file!");
		txtNoSuchFile.setBounds(136, 153, 130, 26);
		frameOpen.getContentPane().add(txtNoSuchFile);
		txtNoSuchFile.setColumns(10);
		txtNoSuchFile.setVisible(false);
	}
}

package manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveAs {
	JFrame frmSaveAS;
	private JTextField textField;
	private ManagerUI board;

	/**
	 * Create the application.
	 */
	public SaveAs() {
		initialize();
	}

    public SaveAs(ManagerUI board) {
		this.board = board;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSaveAS = new JFrame();
		frmSaveAS.setTitle("Save File");
		frmSaveAS.setBounds(100, 100, 450, 300);
		frmSaveAS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSaveAS.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("save as");
		lblNewLabel.setBounds(49, 100, 61, 16);
		frmSaveAS.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(160, 95, 219, 26);
		frmSaveAS.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButtonConfirm = new JButton("Confirm");
		btnNewButtonConfirm.addActionListener(e -> {
			String filePath = textField.getText();
			board.saveFile(filePath);
			frmSaveAS.dispose();
		});
		btnNewButtonConfirm.setBounds(161, 178, 117, 29);
		frmSaveAS.getContentPane().add(btnNewButtonConfirm);
	}
}

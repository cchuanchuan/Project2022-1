package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.*;

/**
 * @author Xi WANG
 */
public class ClientUI {

	public JFrame frame;
	public JList list;

	public static int x,y;
	int width,height;

	public Connection connection;
	static Listener listener;
	static Painter canvas;
	private String clientName;
	JTextField textFieldChat;
	JTextArea textAreaChat;


	/**
	 * Create the application.
	 */
	public ClientUI(Connection connection) {
		this.connection = connection;
		initialize();
	}

	public ClientUI(Connection connection, String name) {
		this.connection = connection;
		this.clientName = name;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("ClientUI: " + this.clientName);
		frame.setResizable(false);
		frame.setBounds(100, 100, 825, 563);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		listener = new Listener(frame);

		try {
			connection.dataOutputStream.writeUTF("start 123");
			connection.dataOutputStream.flush();
		} catch (IOException e) {
			System.out.println("IO error!");
		}

		JButton btnNewButtonLine = new JButton("line");
		btnNewButtonLine.addActionListener(listener);
		btnNewButtonLine.setBounds(119, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonLine);
		btnNewButtonLine.setActionCommand("line");

		JButton btnNewButtonCircle = new JButton(" circle");
		btnNewButtonCircle.addActionListener(listener);
		btnNewButtonCircle.setBounds(234, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonCircle);
		btnNewButtonCircle.setActionCommand("circle");


		JButton btnNewButtonTriangle = new JButton("triangle");
		btnNewButtonTriangle.addActionListener(listener);
		btnNewButtonTriangle.setBounds(347, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonTriangle);
		btnNewButtonTriangle.setActionCommand("triangle");


		JButton btnNewButtonRectangle = new JButton("rectangle");
		btnNewButtonRectangle.addActionListener(listener);
		btnNewButtonRectangle.setBounds(461, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonRectangle);
		btnNewButtonRectangle.setActionCommand("rectangle");

		JButton btnNewButtonColor = new JButton("color");
		btnNewButtonColor.addActionListener(listener);
		btnNewButtonColor.setForeground(Color.MAGENTA);
		btnNewButtonColor.setBackground(Color.WHITE);
		btnNewButtonColor.setBounds(698, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonColor);
		btnNewButtonColor.setActionCommand("color");

		JButton btnNewButtonText = new JButton("text");
		btnNewButtonText.addActionListener(listener);
		btnNewButtonText.setBounds(580, 20, 117, 29);
		frame.getContentPane().add(btnNewButtonText);
		btnNewButtonText.setActionCommand("text");
		listener.setThick(5);

		canvas = new Painter();
		canvas.setBorder(null);
		canvas.setBounds(128, 75, 569, 330);
		width = canvas.getWidth();
		height = canvas.getHeight();
		canvas.setBackground(Color.WHITE);
		canvas.setPaintRecords(listener.getHistory());
		frame.getContentPane().add(canvas);
		canvas.setLayout(null);
		frame.setVisible(true);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				Component component = e.getComponent();
				x = component.getX();
				y = component.getY();
			}
		});

		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		listener.SetGraphics(canvas.getGraphics());

		list = new JList();
//		String name1 = name;
		frame.getContentPane().add(list);

//		String[] nameList = {name1};
		JScrollPane scrollPaneList = new JScrollPane(list);

		scrollPaneList.setBounds(16, 75, 92, 293);
		scrollPaneList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPaneList);

		textFieldChat = new JTextField();
		textFieldChat.setBounds(700, 355, 115, 30);
		frame.getContentPane().add(textFieldChat);

		textAreaChat = new JTextArea();
//		textAreaChat.setEnabled(false);
		textAreaChat.setEditable(false);
		textAreaChat.setBounds(700,75,115,275);
		frame.getContentPane().add(textAreaChat);

		JButton btnNewButtonSendChat = new JButton("sendChat");
		btnNewButtonSendChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//send chat button
				try {
					connection.dataOutputStream.writeUTF("chat "+clientName+":" + textFieldChat.getText());
					connection.dataOutputStream.flush();
					textFieldChat.setText("");
				} catch (IOException e2) {
					System.out.println("IO error!");
				}
			}
		});
		btnNewButtonSendChat.setBounds(698, 400, 117, 29);
		frame.getContentPane().add(btnNewButtonSendChat);
	}
}



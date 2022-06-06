package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagerUI {

	public JFrame frmManager;
    static Listener createListener;
    int width;
    int height;
    public static int x,y;
    public JList list;
    static Painter canvas;
    static ManagerUI createWhiteBoard;
    private String filePath = "whiteBoard";
    private JTextField textFieldChat;
	private JTextArea textAreaChat;


    
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ManagerUI    window = new ManagerUI();
//					window.frmManager.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public ManagerUI() {
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public ManagerUI(String name) {
		initialize(name);
	}

	/**
	 * show the request popup window
	 */

	public int LoginRequest(String name) {
		return JOptionPane.showConfirmDialog(null, "Do you agree " + name + " to share your whiteBoard?", "Agree",
				JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Initialize the contents of the frame.
	 * @param name : userName
	 */
	private void initialize(String name) {
		frmManager = new JFrame();
		frmManager.setTitle("ManagerUI");
		frmManager.setResizable(false);
		frmManager.setBounds(100, 100, 825, 563);
		frmManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManager.getContentPane().setLayout(null);
		//Using listener to monitor the whole Window
		createListener = new Listener(frmManager);
		createListener.managerUI = this;
		//test%%%%%%%%%%
	//	createWhiteBoard = new ManagerUI(name);


		JComboBox comboBoxmenu = new JComboBox();
		comboBoxmenu.setModel(new DefaultComboBoxModel(new String[] {"new", "open", "save", "saveAs", "close"}));
		comboBoxmenu.setBounds(16, 6, 92, 57);
		frmManager.getContentPane().add(comboBoxmenu);
		comboBoxmenu.addActionListener(e ->{
			String menu = comboBoxmenu.getSelectedItem().toString();
			switch (menu) {
				case "new":
					canvas.removeAll();
					canvas.updateUI();
					createListener.clearRecords();
					try {
						ConnectionMain.heartbeart("new");
					} catch (Exception e1) {
						System.out.println("Failed to broadcast new.");
					}
					System.out.println("Succeed to new a whiteBoard.");
					break;
				case "open":
					OpenFile open = new OpenFile(createWhiteBoard);
					open.frameOpen.setVisible(true);
					break;
				case "save":
					save();
					break;
				case "saveAs":
					SaveAs saveAs = new SaveAs(createWhiteBoard);
					saveAs.frmSaveAS.setVisible(true);
					break;
				case "close":
					System.exit(1);
				default:
					throw new IllegalStateException("Unexpected value: " + menu);
			}
		});

		JButton btnNewButtonLine = new JButton("line");
		btnNewButtonLine.addActionListener(createListener);
		btnNewButtonLine.setBounds(119, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonLine);
		btnNewButtonLine.setActionCommand("line");
		
		JButton btnNewButtonCircle = new JButton(" circle");
		btnNewButtonCircle.addActionListener(createListener);
		btnNewButtonCircle.setBounds(234, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonCircle);
		btnNewButtonCircle.setActionCommand("circle");

		
		JButton btnNewButtonTriangle = new JButton("triangle");
		btnNewButtonTriangle.addActionListener(createListener);
		btnNewButtonTriangle.setBounds(347, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonTriangle);
		btnNewButtonTriangle.setActionCommand("triangle");

		
		JButton btnNewButtonRectangle = new JButton("rectangle");
		btnNewButtonRectangle.addActionListener(createListener);
		btnNewButtonRectangle.setBounds(461, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonRectangle);
		btnNewButtonRectangle.setActionCommand("rectangle");

		
		JButton btnNewButtonKick = new JButton("kick");
		btnNewButtonKick.addActionListener(e -> {
			String uName = list.getSelectedValue().toString();
			if (name.equals(uName)) {
				return;
			}
			for (int i = 0; i < StartServer.connections.size();i++) {
				Connection cn = StartServer.connections.get(i);
				if (uName.equals(cn.name)) {
					cn.kicked = true;
					try {
						cn.dataOutputStream.writeUTF(cn.name + "is kicked out!");
						cn.dataOutputStream.flush();
					} catch (IOException e1) {
						System.out.println("Kick out IO error!");
					}

					try {
						cn.socket.close();
					} catch (IOException e1) {
						System.out.println("Socket close error!");
					}

				    StartServer.connections.remove(i);
					StartServer.clientNames.remove(uName);
					JOptionPane.showMessageDialog(frmManager, uName + "has been kicked!");
				}
			}

			for (String clientName : StartServer.clientNames) {
				uName += " " + clientName;
			}

			for (Connection cn:StartServer.connections) {
				try {
					cn.dataOutputStream.writeUTF("delete " + uName);
					cn.dataOutputStream.flush();
				} catch (IOException ioException) {
					System.out.println("IO exception.");
				}
			}

			String[] m1 = uName.split(" ", 2);
			String[] m = m1[1].split(" ");
			list.setListData(m);

		});
		
		btnNewButtonKick.addActionListener(createListener);
		btnNewButtonKick.setBounds(6, 387, 117, 29);
		frmManager.getContentPane().add(btnNewButtonKick);
		btnNewButtonKick.setActionCommand("kick");

		
		JButton btnNewButtonColor = new JButton("color");
		btnNewButtonColor.addActionListener(createListener);
		btnNewButtonColor.setForeground(Color.MAGENTA);
		btnNewButtonColor.setBackground(Color.WHITE);
		btnNewButtonColor.setBounds(698, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonColor);
		btnNewButtonColor.setActionCommand("color");

		
		
//		JPanel jPanelCanvas = new JPanel();
//		jPanelCanvas.setBounds(128, 75, 569, 330);
//		frmManager.getContentPane().add(jPanelCanvas);


//
//        drawArea.setPaintRecords(createListener.getHistory());
		
		JButton btnNewButtonText = new JButton("text");
		btnNewButtonText.addActionListener(createListener);
		btnNewButtonText.setBounds(580, 20, 117, 29);
		frmManager.getContentPane().add(btnNewButtonText);
		btnNewButtonText.setActionCommand("text");
		createListener.setThick(5);

       canvas = new Painter();
       canvas.setBorder(null);
       canvas.setBounds(128, 75, 569, 330);
       width = canvas.getWidth();
       height = canvas.getHeight();
       canvas.setBackground(Color.WHITE);
       //&&&&&&&
      canvas.setPaintRecords(createListener.getRecords());
		frmManager.getContentPane().add(canvas);
		canvas.setLayout(null);
		frmManager.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				Component component = e.getComponent();
				x = component.getX();
				y = component.getY();
			}
		});
        frmManager.setVisible(true);
        canvas.addMouseListener(createListener);
        canvas.addMouseMotionListener(createListener);
        createListener.SetGraphics(canvas.getGraphics());

        list = new JList<>();
        String name1 = name;
        frmManager.getContentPane().add(list);
        String[] nameList = {name1};
        list.setListData(nameList);
        JScrollPane scrollPaneList = new JScrollPane(list);

//		JScrollPane scrollPaneList = new JScrollPane();
		scrollPaneList.setBounds(16, 75, 92, 293);
		scrollPaneList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmManager.getContentPane().add(scrollPaneList);
		
//		textFieldChat.setEditable(false);
//		textFieldChat.setBounds(708, 75, 107, 313);
		textFieldChat = new JTextField();
		textFieldChat.setBounds(700, 355, 115, 30);
		frmManager.getContentPane().add(textFieldChat);

		textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		textAreaChat.setBounds(700,75,115,275);
		frmManager.getContentPane().add(textAreaChat);

		JButton btnNewButtonSendChat = new JButton("sendChat");
		btnNewButtonSendChat.addActionListener(e -> {
			////send chat button
			ConnectionMain.sendMessage("chat manager:"+ getTextFieldChat().getText());
			this.textAreaChat.append("manager: "+ getTextFieldChat().getText()+"\n");
			this.textFieldChat.setText("");
		});
		btnNewButtonSendChat.setBounds(698, 400, 117, 29);
		frmManager.getContentPane().add(btnNewButtonSendChat);
	}


	public void setFrame(ManagerUI createWhiteBoard) {
		ManagerUI.createWhiteBoard = createWhiteBoard;
	}

	public void open(String fileName) throws IOException {
		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open a file.");
			return;
		}
		createListener.clearRecords();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			createListener.updateRecords(line);
		}
		ConnectionMain.heartbeart("new");
        ArrayList<String> records = createListener.getRecords();
        ConnectionMain.partHeartBeat(records);
        canvas.repaint();
        scanner.close();

	}

	public void saveFile(String filePath) {
		this.filePath = "/Users/mac/Downloads/" + filePath;
		this.save();
	}

	private void save() {
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("FIle not Found.");
			return;
		}
		ArrayList<String> records = createListener.getRecords();
		for (String record: records) {
			printWriter.println(record);
		}
		printWriter.flush();
		printWriter.close();
		System.out.println("Succeed to save a file");
	}

	public JTextArea getTextAreaChat() {
		return textAreaChat;
	}

	public JTextField getTextFieldChat() {
		return textFieldChat;
	}
}

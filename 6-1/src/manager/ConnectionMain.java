package manager;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ConnectionMain {

	public static synchronized int check(String name) {
		return LoginUI.createWhiteBoard.LoginRequest(name);
	}

	public static void addClient(String[] clients) {
		LoginUI.createWhiteBoard.list.setListData(clients);
	}

	public static void clientOut(String clients) {

		String[] list = clients.split(" ", 2);
		JOptionPane.showMessageDialog(LoginUI.createWhiteBoard.frmManager, list[0] + "leaved!");
		String[] users = list[1].split(" ");
		LoginUI.createWhiteBoard.list.setListData(users);

	}



	public static void heartbeart(String msg) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Connection c:StartServer.connections) {
			try {
				c.dataOutputStream.writeUTF(msg);
				c.dataOutputStream.flush();
			} catch (IOException e) {
				System.out.println("ConnectionMain heartbeat IOException!");
			}

		}
	}

	public static void sendMessage(String msg) {
		for (Connection c:StartServer.connections) {
			try {
				c.dataOutputStream.writeUTF(msg);
				c.dataOutputStream.flush();
			} catch (IOException e) {
				System.out.println("ConnectionMain sendMessage IOException!");
			}
		}
	}

	public static void receiveMessage(String msg) {
		ManagerUI.createListener.receiveMessage(msg);
		for (Connection c:StartServer.connections) {
			try {
				c.dataOutputStream.writeUTF(msg);
				c.dataOutputStream.flush();
			} catch (IOException e) {
				System.out.println("ConnectionMain sendMessage IOException!");
			}
		}
	}

	public static void partHeartBeat(ArrayList<String> startRecords) {
		String[] records = startRecords.toArray(new String[startRecords.size()]);
		for (String msg: records) {
			for (Connection c: StartServer.connections) {
				try {
					c.dataOutputStream.writeUTF("paint " + msg);
					c.dataOutputStream.flush();
				} catch (IOException e) {
					System.out.println("partHeartBeat IO exception!" );;
				}
			}
		}
	}




	public static void areaRepaint(String record) {
		ManagerUI.createListener.updateRecords(record);
		ManagerUI.canvas.repaint();
	}



}

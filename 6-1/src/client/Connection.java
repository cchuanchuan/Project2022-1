package client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Xi WANG
 */
public class Connection {
    public DataOutputStream dataOutputStream = null;
    public DataInputStream dataInputStream = null;
    private Socket socket;

    boolean kicked = false;
    String status;

     Connection(Socket socket) {
        setStatus();

        try {
            this.socket = socket;
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            dataInputStream = new DataInputStream(this.socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Client Failed to connect to the whiteBoard!");
        }

    }

    String getCurStatus() {
        return status;
    }

    public void setStatus() {
        status = "wait";
        return;
    }

    public void start() {
        while (true) {
            try {
                String request = dataInputStream.readUTF();
                String[] requestArray = request.split(" ", 2);

                if (requestArray[0].equals("paint")) {
                    ClientUI.listener.updateRecords(requestArray[1]);
                    if (ClientUI.canvas != null){
                        ClientUI.canvas.repaint();
                    }
                }
                if (requestArray[0].equals("chat")) {
                    LoginUI.guestUI.textAreaChat.append(requestArray[1] + "\r\n");
//                    LoginUI.guestUI.textAreaChat.setCaretPosition(LoginUI.guestUI.clientTextArea.getDocument().getLength());
                }
                if (requestArray[0].equals("userList") && LoginUI.guestUI != null) {
                    LoginUI.guestUI.list.setListData(requestArray[1].split(" "));
                }
                if (requestArray[0].equals("delete")) {
                    String[] clients = requestArray[1].split(" ", 2);
                    String[] clientList = clients[1].split(" ");
                    JOptionPane.showMessageDialog(LoginUI.guestUI.frame, clients[0] + "is kicked out by manager!");
                    LoginUI.guestUI.list.setListData(clientList);
                }

                if (requestArray[0].equals("kick")) {
                    kicked = true;
                    JOptionPane.showMessageDialog(LoginUI.guestUI.frame,"You are kicked out by manager!");
                }



                if (requestArray[0].equals("clientLeave")) {
                    String[] clients = requestArray[1].split(" ", 2);
                    String[] clientList = clients[1].split(" ");
                    JOptionPane.showMessageDialog(LoginUI.guestUI.frame, clients[0] + "leaves.");
                    LoginUI.guestUI.list.setListData(clientList);
                }

                if (requestArray[0].equals("new")) {
                    ClientUI.canvas.removeAll();
                    ClientUI.canvas.updateUI();
                    ClientUI.listener.clearRecords();
                }

                if (requestArray[0].equals("answer")) {
                    switch (requestArray[1]) {
                        case "no":
                            status = "no";
                            break;
                        case "yes":
                            status = "yes";
                            break;
                        case "refused":
                            status = "refused";
                            break;
                    }
                }

            } catch (IOException e) {
                try{
                if (!kicked) {
                    JOptionPane.showMessageDialog(LoginUI.guestUI.frame, "Disconnected");
                   }
                /////%%%%%%%%
                    System.exit(0);
                } catch (Exception e1){
                    System.out.println("Disconnected error.");
                    System.exit(0);
                }
            }
        }
    }






}

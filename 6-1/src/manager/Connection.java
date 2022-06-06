package manager;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * control the connection of the clients
 * @author Xi WANG
 */
public class Connection extends Thread {
   public Socket socket;
   public String name;
   public DataInputStream dataInputStream;
   public DataOutputStream dataOutputStream;

   public boolean kicked = false;

    /**
     * constructor
     */
   public Connection(Socket socket) {
       this.socket = socket;
   }

   @Override
   public void run() {
       try {
           dataInputStream = new DataInputStream(socket.getInputStream());
           dataOutputStream = new DataOutputStream(socket.getOutputStream());
           String inputString;
           while ((inputString = dataInputStream.readUTF()) != null) {
              String[] str = inputString.split(" ", 2);
              //%%%%%%
              if (str[0].equals("start")) {
                  ArrayList<String> startRecords = LoginUI.createWhiteBoard.createListener.getRecords();
                  try {
                      ConnectionMain.partHeartBeat(startRecords);
                  } catch (Exception e) {
                      System.out.println("IO error!");
                  }
                  String str1 = "userList ";
                  for (String name: StartServer.clientNames) {
                      //%%%%%%%
                      str1 += " "+ name;
                  }

                  String[] k = str1.split(" ", 2);
                  String[] clients = k[1].split(" ");
                  ConnectionMain.addClient(clients);
                  ConnectionMain.heartbeart(str1);
              } else if (str[0].equals("request")) {
                  String user = str[1];
                  name = user;
                  if (StartServer.clientNames.contains(user)) {
                      dataOutputStream.writeUTF("answer no");
                      dataOutputStream.flush();
                  } else {
                      int respond = ConnectionMain.check(str[1]) ;
                      if (respond == JOptionPane.YES_OPTION) {
                          if(StartServer.clientNames.contains(user)) {
                             try {
                                 dataOutputStream.writeUTF("answer no");
                                 dataOutputStream.flush();
                                 StartServer.connections.remove(this);
                                 socket.close();
                                 break;
                             } catch (IOException e){
                                 StartServer.connections.remove(this);
                                 System.out.println("IO error");
                             }
                          } else {
                              StartServer.clientNames.add(user);
                              dataOutputStream.writeUTF("answer yes");
                              dataOutputStream.flush();
                          }
                      } else if (respond == JOptionPane.CLOSED_OPTION || respond == JOptionPane.NO_OPTION || respond == JOptionPane.CANCEL_OPTION) {
                          dataOutputStream.writeUTF("answer refused");
                          dataOutputStream.flush();
                          StartServer.connections.remove(this);
                      }
                  }
               } else if (str[0].equals("paint")){
                   ConnectionMain.heartbeart(inputString);
                   ConnectionMain.areaRepaint(str[1]);
               } else if(str[0].equals("done")) {
                   socket.close();
                   break;
               }else if (str[0].equals("chat")) {
                  ConnectionMain.receiveMessage(inputString);
              }
              if (str[0].equals("new")) {
                  ManagerUI.canvas.removeAll();
                  ManagerUI.canvas.updateUI();
                  ManagerUI.createListener.clearRecords();
              }
           }
       }catch (Exception e) {
           System.out.println(this.name + " " + " failed to connect!");
           if (!this.kicked) {
               clientLeave();
           }
       }
   }

    private void clientLeave() {
       StartServer.connections.remove(this);
       StartServer.clientNames.remove(name);
       String str = "clientLeave " + name;
       for (int i = 0; i < StartServer.clientNames.size(); i++) {
           str += " " + StartServer.clientNames.get(i);
       }
        for (Connection c: StartServer.connections) {
            try {
                c.dataOutputStream.writeUTF(str);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Client leave IO Exception!");
            }
        }
        String n = str.split(" ", 2)[1];
        ConnectionMain.clientOut(n);
    }

}

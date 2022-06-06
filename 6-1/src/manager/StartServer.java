package manager;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xi WANG
 */
public class StartServer {

	public static List<Connection> connections = new ArrayList<>();
	public static List<String> clientNames = new ArrayList<>();
	public static int clientNum = 0;

	  protected static void start(int port, String clientName) {
	  	   Connection connection = null;
		   ServerSocket serverSocket = null;
		   clientNames.add(clientName);
		   try {
			   serverSocket = new ServerSocket(port);
			   Socket clientSocket;
			   while (true) {
			       clientSocket = serverSocket.accept();
			       clientNum++;
				   System.out.println(clientNum + " " + clientName + "requests for connection.");
				   connection = new Connection(clientSocket);
				   connections.add(connection);
				   connection.start();

			   }
		   } catch (IOException e) {
			   System.out.println("Connection error.");
			   System.exit(1);
		   }
	   }

}

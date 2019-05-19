package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket clientSocket;
	private String hostName;
	private int serverPort;
	private DataInputStream reader;
	private DataOutputStream writer;
	private Protocol protocol;

	private static Client client;

	private Client() throws IOException {
		protocol = new Protocol();
	}

	public void register(String Ip, int port, int posX, int posY) throws IOException {
		this.serverPort = port;
		this.hostName = Ip;
		clientSocket = new Socket(Ip, port);
		writer = new DataOutputStream(clientSocket.getOutputStream());

		writer.writeUTF(protocol.RegisterPacket(posX, posY));

	}

	public void sendToServer(String message) {
		if (message.equals("exit"))
			System.exit(0);
		else {
			try {
				Socket s = new Socket(hostName, serverPort);
				System.out.println(message);
				writer = new DataOutputStream(s.getOutputStream());
				writer.writeUTF(message);
			} catch (IOException ex) {

			}
		}

	}

	public Socket getSocket() {
		return clientSocket;
	}

	public String getIP() {
		return hostName;
	}

	public static Client getGameClient() {
		if (client == null)

			try {
				client = new Client();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		return client;
	}

	public void closeAll() {
		try {
			reader.close();
			writer.close();
			clientSocket.close();
		} catch (IOException ex) {

		}
	}
}

package client;

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

	public Client() {
		protocol = new Protocol();
	}

	public void register(String ip, int port, int x, int y) throws IOException {
		this.serverPort = port;
		this.hostName = ip;
		clientSocket = new Socket(ip, port);
		writer = new DataOutputStream(clientSocket.getOutputStream());

		writer.writeUTF(protocol.RegisterPacket(x, y));
	}

	public void sendToServer(String message) {
		if (message.equals("exit"))
			System.exit(0);
		else {
			try {
				Socket s = new Socket(hostName, serverPort);
//				System.out.println(message);
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

	public void closeAll() {
		try {
			reader.close();
			writer.close();
			clientSocket.close();
		} catch (IOException ex) {

		}
	}
}

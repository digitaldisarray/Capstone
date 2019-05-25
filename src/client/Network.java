package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Network {
	public static class mesh {
		// The default port that the Window library should use.
		private static final int DEFAULT_PORT = 4965;

		// References to threads for master-slave network.
		private static Server server;
		private static Client client;
		private static ConcurrentHashMap<String, String> stringCache;
		private static ConcurrentHashMap<String, Double> doubleCache;
		private static ConcurrentHashMap<String, Integer> intCache;
		private static boolean running = false;

		/**
		 * Initializes the data structures of the mesh.
		 */
		private static void initialize() {
			stringCache = new ConcurrentHashMap<String, String>();
			doubleCache = new ConcurrentHashMap<String, Double>();
			intCache = new ConcurrentHashMap<String, Integer>();
			running = true;
		}

		/**
		 * Starts a mesh at the default port.
		 */
		public static void start() {
			start(DEFAULT_PORT);
		}

		/**
		 * Starts a mesh at the given port.
		 * 
		 * @param port - the port number to listen on
		 */
		public static void start(int port) {
			if (!running) {
				initialize();
				server = new Server(port);
				server.start();
			}
		}

		/**
		 * Joins the mesh at the given IP address, with the default port.
		 * 
		 * @param ip - the IP address of the server hosting the mesh
		 */
		public static void join(String ip) {
			join(ip, DEFAULT_PORT);
		}

		/**
		 * Joins the mesh at the given IP address and port.
		 * 
		 * @param ip   - the IP address of the server hosting the mesh
		 * @param port - the port the server is listening on
		 */
		public static void join(String ip, int port) {
			if (!running) {
				initialize();
				client = new Client(ip, port);
				client.start();
			}
		}

		/**
		 * Writes a key-value pair to the mesh, so all clients can read them.
		 * 
		 * @param key   - a unique identifier for this value
		 * @param value - an integer value
		 */
		public static void write(String key, int value) {
			if (running) {

				// If this value has not changed from its locally cached value.
				if (intCache.containsKey(key) && intCache.get(key) == value)
					return;

				// Send a new value through this instance's respective thread.
				if (server != null)
					server.put(null, key, value);
				else if (client != null)
					client.put(key, value);
			}
		}

		/**
		 * Writes a key-value pair to the mesh, so all clients can read them.
		 * 
		 * @param key   - a unique identifier for this value
		 * @param value - a double value
		 */
		public static void write(String key, double value) {
			if (running) {

				// If this value has not changed from its locally cached value.
				if (doubleCache.containsKey(key) && doubleCache.get(key) == value)
					return;

				// Send a new value through this instance's respective thread.
				if (server != null)
					server.put(null, key, value);
				else if (client != null)
					client.put(key, value);
			}
		}

		/**
		 * Writes a key-value pair to the mesh, so all clients can read them.
		 * 
		 * @param key   - a unique identifier for this value
		 * @param value - a double value
		 */
		public static void write(String key, String value) {
			if (running) {

				// If this value has not changed from its locally cached value.
				if (stringCache.containsKey(key) && stringCache.get(key).equals(value))
					return;

				// Send a new value through this instance's respective thread.
				if (server != null)
					server.put(null, key, value);
				else if (client != null)
					client.put(key, value);
			}
		}

		/**
		 * Reads an integer value from the distributed key-value store.
		 * 
		 * @param key - the unique identifier for the requested value
		 */
		public static int read(String key) {
			if (intCache.containsKey(key))
				return intCache.get(key);
			else
				return 0;
		}

		/**
		 * Reads a precise value from the distributed key-value store.
		 * 
		 * @param key - the unique identifier for the requested value
		 */
		public static double readDouble(String key) {
			if (doubleCache.containsKey(key))
				return doubleCache.get(key);
			else
				return 0;
		}

		/**
		 * Reads a string value from the distributed key-value store.
		 * 
		 * @param key - the unique identifier for the requested value
		 */
		public static String readString(String key) {
			if (stringCache.containsKey(key))
				return stringCache.get(key);
			else
				return null;
		}

		/**
		 * Thread for a server listening on the given port.
		 */
		private static class Server extends Thread {
			private int port;
			private ServerSocket master;
			private ArrayList<ServerClient> clients;

			/**
			 * Initialize this thread to listen on the given port.
			 * 
			 * @param port
			 */
			public Server(int port) {
				this.port = port;
			}

			/**
			 * Starts listening on the given port.
			 */
			public void run() {
				try {
					master = new ServerSocket(port);
					clients = new ArrayList<ServerClient>();
					System.out.println(
							"Starting mesh at " + InetAddress.getLocalHost().getHostAddress() + ", port " + port);

					// Keep listening for new clients
					while (true) {
						Socket newClient = master.accept();
						Network.mesh.message("connection from " + newClient.getInetAddress().getHostAddress());

						ServerClient client = new ServerClient(newClient, this);
						synchronized (clients) {
							clients.add(client);
							client.start();
						}
					}
				} catch (IOException e) {
					error("Could not create server at port " + port);
				}
			}

			/**
			 * 
			 * @param client - the client that is sending the new value, or null if it is
			 *               originating from the server.
			 * @param key    - unique ID of the data
			 * @param value  - the integer value
			 */
			public void put(ServerClient client, String key, int value) {
				intCache.put(key, value);
				synchronized (clients) {
					for (ServerClient c : clients) {
						if (c != client) {
							c.put(key, value);
						}
					}
				}
			}

			/**
			 * 
			 * @param client - the client that is sending the new value, or null if it is
			 *               originating from the server.
			 * @param key    - unique ID of the data
			 * @param value  - the integer value
			 */
			public void put(ServerClient client, String key, double value) {
				doubleCache.put(key, value);
				synchronized (clients) {
					for (ServerClient c : clients) {
						if (c != client) {
							c.put(key, value);
						}
					}
				}
			}

			/**
			 * 
			 * @param client - the client that is sending the new value, or null if it is
			 *               originating from the server.
			 * @param key    - unique ID of the data
			 * @param value  - the integer value
			 */
			public void put(ServerClient client, String key, String value) {
				stringCache.put(key, value);
				synchronized (clients) {
					for (ServerClient c : clients) {
						if (c != client) {
							c.put(key, value);
						}
					}
				}
			}
		}

		private static class ServerClient extends Thread {
			private Server master;
			private BufferedReader input;
			private PrintWriter output;
			private String ip;
			private boolean connected = false;
			private long bandwidth = 0;

			public ServerClient(Socket socket, Server master) {
				try {
					this.master = master;
					ip = socket.getInetAddress().getHostAddress();
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream(), true);
					connected = true;
				} catch (IOException e) {
				}
			}

			public void run() {
				if (!connected)
					return;

				// Copy all current data to client
				StringBuilder initialUpdate = new StringBuilder();
				for (String key : intCache.keySet())
					initialUpdate.append('#').append(key).append('=').append(intCache.get(key)).append('\n');
				for (String key : doubleCache.keySet())
					initialUpdate.append('%').append(key).append('=').append(doubleCache.get(key)).append('\n');
				for (String key : stringCache.keySet())
					initialUpdate.append('$').append(key).append('=').append(stringCache.get(key)).append('\n');

				output.println(initialUpdate);
				bandwidth += initialUpdate.length();
				Network.mesh.message("updated " + ip);

				// Keep reading updates from the client until disconnect, and update all other
				// clients with updates
				try {
					while (true) {
						String line = input.readLine();
						if (line == null)
							break;

						bandwidth += line.length();
						char type = line.charAt(0);
						int equals = line.indexOf('=');
						String key = line.substring(1, equals);
						if (type == '#')
							master.put(this, key, Integer.parseInt(line.substring(equals + 1)));
						else if (type == '%')
							master.put(this, key, Double.parseDouble(line.substring(equals + 1)));
						else if (type == '$')
							master.put(this, key, line.substring(equals + 1));
					}

					input.close();
					output.close();

				} catch (IOException e) {
				}
			}

			public void put(String key, int value) {
				if (connected) {
					output.println('#' + key + '=' + value);
				}
			}

			public void put(String key, double value) {
				if (connected) {
					output.println('%' + key + '=' + value);
				}
			}

			public void put(String key, String value) {
				if (connected) {
					output.println('$' + key + '=' + value);
				}
			}
		}

		private static class Client extends Thread {
			Socket socket;
			BufferedReader input;
			PrintWriter output;
			String ip;
			boolean connected = false;

			public Client(String ip, int port) {
				try {
					this.socket = new Socket(ip, port);
					this.ip = ip;

					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream(), true);
					connected = true;
				} catch (IOException e) {
				}
			}

			public void run() {
				if (!connected) {
					error("Could not connect to " + ip + ".");
					return;
				}
				try {
					while (true) {
						String line = input.readLine();
						if (line == null) {
							error("Connection to " + ip + " closed.");
							break;
						}
						if (line.length() > 0) {
							char type = line.charAt(0);
							int equals = line.indexOf('=');
							String key = line.substring(1, equals);
							if (type == '#')
								intCache.put(key, Integer.parseInt(line.substring(equals + 1)));
							else if (type == '%')
								doubleCache.put(key, Double.parseDouble(line.substring(equals + 1)));
							else if (type == '$')
								stringCache.put(key, line.substring(equals + 1));
						}
					}
					connected = false;
					input.close();
					output.close();
					socket.close();
				} catch (IOException e) {
				}
			}

			public void put(String key, int value) {
				if (connected && key != null) {
					intCache.put(key, value);
					output.println('#' + key + '=' + value);
				}
			}

			public void put(String key, double value) {
				if (connected && key != null) {
					doubleCache.put(key, value);
					output.println('%' + key + '=' + value);
				}
			}

			public void put(String key, String value) {
				if (connected && key != null) {
					stringCache.put(key, value);
					output.println('$' + key + '=' + value);
				}
			}
		}

		/**
		 * Prints a mesh message.
		 */
		private static DateFormat messageDateFormat = new SimpleDateFormat("HH:mm:ss");

		public static void message(String m) {
			System.out.println("[ " + messageDateFormat.format(new Date()) + " ] " + m);
		}

		public static void error(String e) {
			System.err.println("[ " + messageDateFormat.format(new Date()) + " ] " + e);
		}
	}
}
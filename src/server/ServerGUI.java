package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerGUI extends JFrame implements ActionListener {

	private JButton startServerButton;
	private JButton stopServerButton;
	private JLabel statusLabel;
	private JLabel ipLabel1;
	private JLabel ipLabel2;

	String publicIP;

	private Server server;

	InetAddress net;

	public ServerGUI() {
		setTitle("Bracket Server");
		setBounds(350, 300, 300, 200);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(null);
		startServerButton = new JButton("Start Server");
		startServerButton.setBounds(20, 30, 120, 25);
		startServerButton.addActionListener(this);

		stopServerButton = new JButton("Stop Server");
		stopServerButton.setBounds(150, 30, 120, 25);
		stopServerButton.addActionListener(this);

		statusLabel = new JLabel();
		statusLabel.setBounds(80, 90, 200, 25);

		ipLabel1 = new JLabel();
		ipLabel1.setBounds(80, 105, 200, 25);

		ipLabel2 = new JLabel();
		ipLabel2.setBounds(80, 120, 200, 25);

		getContentPane().add(statusLabel);
		getContentPane().add(startServerButton);
		getContentPane().add(stopServerButton);
		getContentPane().add(ipLabel1);
		getContentPane().add(ipLabel2);

		try {
			net = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		stopServerButton.setEnabled(false);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startServerButton) {
			try {
				server = new Server();
			} catch (SocketException e2) {
				e2.printStackTrace();
			}

			server.start();
			startServerButton.setEnabled(false);
			stopServerButton.setEnabled(true);

			try {
				BufferedReader sc = new BufferedReader(
						new InputStreamReader(new URL("http://bot.whatismyipaddress.com").openStream()));
				publicIP = sc.readLine().trim();
			} catch (Exception e2) {
				publicIP = "Not Found";
			}

			statusLabel.setText("Server: running");
			ipLabel1.setText("Local: " + net.getHostAddress());
			ipLabel2.setText("Public: " + publicIP);
		}

		if (e.getSource() == stopServerButton) {
			startServerButton.setEnabled(true);
			stopServerButton.setEnabled(false);

			server.stopServer();
			server = null;

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			statusLabel.setText("Server: stopped");
			ipLabel1.setText("Local: not running");
			ipLabel2.setText("Public: not running");

			System.exit(0);
		}
	}
}

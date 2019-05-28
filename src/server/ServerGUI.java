package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerGUI extends JFrame implements ActionListener {

	private JButton startServerButton;
	private JButton stopServerButton;
	private JLabel statusLabel;
	private JLabel ipLabel1;

	String publicIP;

	private Server server;

	InetAddress net;

	public ServerGUI() {
		setResizable(false);
		setTitle("A server for epic. (Epic)");
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
		ipLabel1.setBounds(50, 105, 200, 25);

		getContentPane().add(statusLabel);
		getContentPane().add(startServerButton);
		getContentPane().add(stopServerButton);
		getContentPane().add(ipLabel1);

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

			statusLabel.setText("Server: running");
			ipLabel1.setText("Maybe Correct IP: " + net.getHostAddress());
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
			ipLabel1.setText("Maybe Correct IP: not running");

			System.exit(0);
		}
	}
}

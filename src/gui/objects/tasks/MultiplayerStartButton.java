package gui.objects.tasks;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JOptionPane;

import base.Game;
import base.Launcher;
import gui.objects.ButtonTask;

public class MultiplayerStartButton implements ButtonTask {

	@Override
	public void run(Color color) {
		// Get ip and port
		String ip = "";
		int port = 0;
		try {
			ip = JOptionPane.showInputDialog("Enter IP:");

			if (!isIP(ip)) {
				JOptionPane.showMessageDialog(null, "Please enter a valid IP", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port:"));

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "The port is supposed to be a number.", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		} catch (NullPointerException e) {
			return;
		}

		try {
			int x = (int) (Math.random() * 700 + 50);
			int y = (int) (Math.random() * 500 + 50);
			Launcher.getGame().getPlayer().setX(x);
			Launcher.getGame().getPlayer().setY(y);
			Launcher.getClient().register(ip, port, x, y);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to server.", "Connection Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Set in game if connected
		Launcher.getGame().startClientThread();
		Launcher.getGame().setConnected(true);
		Launcher.getGame().setInGame(true);
	}

	/**
	 * Checks if a string is a valid ip address.
	 * 
	 * @param ip - The string to verify is an ip.
	 * @return Returns if it is an ip address.
	 */
	private boolean isIP(String ip) {
		if (ip.isEmpty() || ip.endsWith(".") || ip.startsWith(".")) {
			return false;
		}
		
		// For some reason I can't split strings so yeah
//		System.out.println("h");
//		if(ip.contains(".")) {
//			System.out.println("I");
//		}
//		
//		System.out.println(ip.split(".").toString() + " zzzzz " + ip + " zzzz " + ip.split(".").length);
//		if (ip.split(".").length != 4) {
//			return false;
//		}
//
//		System.out.println("h");
//		
//		int parsed;
//		for (String num : ip.split(".")) {
//			parsed = Integer.parseInt(num);
//			if (parsed < 0 || parsed > 255) {
//				return false;
//			}
//		}

		return true;
	}
}

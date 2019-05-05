package example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {

	public void paint(Graphics g) {

		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		double xRatio = width / 800.0;
		double yRatio = height / 600.0;

		Graphics2D g2 = (Graphics2D) g;
		g2.scale(xRatio, yRatio);

		// draw basic shape on the panel
		g2.setColor(Color.RED);
		g2.fillRect(200, 150, 400, 300);

	}

}

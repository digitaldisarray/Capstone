package base;
public class Launcher {
	private static Game game;
	
	public static void main(String[] args) {
		game = new Game("Game", 800, 600);
		game.start();
		
		
		// Setup
//		JFrame frame = new JFrame("Game");
//		frame.setBounds(300, 300, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
//		frame.getContentPane().setBackground(Color.WHITE);
//		frame.setResizable(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		
		
//		JFrame mainScreen = new JFrame("Main Screen");
//		mainScreen.setBounds(300, 300, 800, 600);
//		mainScreen.getContentPane().setBackground(Color.WHITE);
//
//		mainScreen.setResizable(true); // the window can be resized
//		mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this happens with window is closed
//		mainScreen.setVisible(true);
//
//		CustomPanel mainPanel = new CustomPanel(); // create a panel to add to window
//		mainPanel.setBackground(Color.WHITE);// set the background color of the panel to white
//
//		// add the panel to the frame
//		Container windowContainer = mainScreen.getContentPane();// get the window's container
//		windowContainer.add(mainPanel); // add the panel to the window
//
//		// make the window visible
//		mainScreen.setVisible(true);
	}
	
	public static Game getGame() {
		return game;
	}
}

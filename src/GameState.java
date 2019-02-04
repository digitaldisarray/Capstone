import java.awt.Color;
import java.awt.Graphics;

public class GameState extends State {

	public GameState() {
		
	}
	
	@Override
	public void tick() {
		// Insert game logic, updates, and networking here
	}

	@Override
	public void render(Graphics g) {
		// Insert game graphics here
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, 100, 200);
	}
	
	
	
}

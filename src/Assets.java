import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	public static BufferedImage player, dirt, grass, stone, tree;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/menu.jpg"));
		player = sheet.crop(300, 350, width, height);
		dirt = sheet.crop(300 + width * 2, 350 + height * 2, width, height);
	}
}

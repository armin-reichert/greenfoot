import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Eine Mauer.
 * 
 * @author Zwickmann
 */
public class Wall extends Actor
{
	public Wall(int width, int height) {
		GreenfootImage tile = getImage();
		GreenfootImage image = new GreenfootImage(width, height);
		for (int x = 0; x < width; x += tile.getWidth()) {
			for (int y = 0; y < height; y += tile.getHeight()) {
				image.drawImage(tile, x, y);
			}
		}
		setImage(image);
	}
}

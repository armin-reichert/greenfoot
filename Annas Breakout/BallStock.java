import greenfoot.GreenfootImage;

import java.util.Arrays;
import java.util.List;

/**
 * Displays the balls in stock.
 * 
 * @author Zwickmann
 */
public class BallStock extends BreakoutActor
{
    private final List<Ball> balls;
    private int rightEdge;

    public BallStock(List<Ball> balls) {
        this.balls = balls;
        updateImage();
    }

    public BallStock() {
        this(Arrays.asList(new NormalBall(), new Superball(), new NormalBall()));
    }

    @Override
    public void act() {
        updateImage();
        updateLocation();
    }
    
    public void setRightEdge(int x) {
    	rightEdge = x;
    	updateLocation();
    }
    
    private void updateLocation() {
        int x = rightEdge - (getImage() == null ? 0 : getImage().getWidth() / 2);
    	setLocation(x, getY());
    }

    private void updateImage() {
        if (balls.isEmpty()) {
            setImage((GreenfootImage) null);
            return;
        }
        int width = 0;
        int height = 0;
        for (Ball ball : balls) {
            width += ball.getImage().getWidth();
            height = Math.max(height, ball.getImage().getHeight());
        }
        GreenfootImage image = new GreenfootImage(width, height);
        int x = 0;
        int y = 0;
        for (Ball ball : balls) {
            image.drawImage(ball.getImage(), x, y);
            x += ball.getImage().getWidth();
        }
        setImage(image);
    }
}

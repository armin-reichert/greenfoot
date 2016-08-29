import greenfoot.GreenfootImage;
import java.awt.Color;

/**
 * Power-up, das einen neuen Zickzack-Ball ins Spiel bringt.
 * 
 * @author Zwickmann
 */
public class ExtraZickZackBallPower extends Power
{
    private static GreenfootImage IMAGE;

    static {
        GreenfootImage ball = new GreenfootImage("steel-ball.png");
        ball.scale(20, 20);
        int w = ball.getWidth();
        int h = 2 * ball.getHeight() + 30;
        IMAGE = new GreenfootImage(w, h);
        IMAGE.setColor(Color.WHITE);
        IMAGE.drawLine(10, 15, 0, 30);
        IMAGE.drawLine(0, 30, 20, 40);
        IMAGE.drawLine(20, 40, 10, 55);
        IMAGE.drawImage(ball, 0, 0);
        IMAGE.drawImage(ball, 0, 50);
    }

    public ExtraZickZackBallPower() {
        super("Extra-Flatterball", 4 * Cfg.DEFAULT_POWER_DURATION);
        setImage(IMAGE);
    }

    @Override
    void activate() {
        game().extraBall(new ZickZackBall(duration));
        game().playBall();
    }
}

import greenfoot.Greenfoot;

/**
 * Builds the Breakout levels.
 */
public class LevelBuilder
{
    public static final int MAX_LEVEL = 14;

    private static final int distX = 5;
    private static final int distY = 10;
    private static final int dx = Cfg.BALLOON_WIDTH + distX;
    private static final int dy = Cfg.BALLOON_HEIGHT + distY;
    private static final int numColumns = (Cfg.PANEL_WIDTH - 2 * Cfg.WALL_THICKNESS) / (Cfg.BALLOON_WIDTH + distX);
    private static final int restX = (Cfg.PANEL_WIDTH - 2 * Cfg.WALL_THICKNESS) - numColumns
        * (Cfg.BALLOON_WIDTH + distX);
    private static final int xmin = Cfg.PANEL_MIN_X + Cfg.BALLOON_WIDTH / 2 + restX / 2;
    private static final int ymin = Cfg.PANEL_MIN_Y + Cfg.BALLOON_HEIGHT / 2 + distY;

    private final Breakout game;
    private int level;

    private class Row
    {
        private String images;
        private int lives = 1;
        private int value = 0;
        private int powerProb = 0;
        private double speedX = 0.0;
        private double speedY = 0.0;
        private int turnDegrees = 0;

        public Row images(String images) {
            this.images = images;
            return this;
        }

        public Row lives(int lives) {
            this.lives = lives;
            return this;
        }

        public Row value(int value) {
            this.value = value;
            return this;
        }

        public Row powerProb(int powerProb) {
            this.powerProb = powerProb;
            return this;
        }

        public Row speedX(double speedX) {
            this.speedX = speedX;
            return this;
        }

        public Row speedY(double speedY) {
            this.speedY = speedY;
            return this;
        }

        public Row turnBy(int turnDegrees) {
            this.turnDegrees = turnDegrees;
            return this;
        }

        private void addToWorld(int rowIndex) {
            int x = xmin;
            int y = ymin + rowIndex * dy;
            for (int i = 0; i < numColumns; ++i) {
                Balloon balloon = new Balloon.Builder().lives(lives).imageName(alt(i, images.split(",")) + ".png").value(value)
                    .power(rndPower(powerProb)).speed(new Vector(speedX, speedY)).turnDegrees(turnDegrees).build();
                game.addObject(balloon, x, y);
                x += dx;
            }
        }
    }

    public LevelBuilder(Breakout game, int level) {
        this.game = game;
        this.level = level;
    }

    public void createLevel() {

        switch (level) {
            case 1:
            addRows(
                new Row().images("Anna").lives(1).value(10).powerProb(10),
                new Row().images("Anna").lives(2).value(10).powerProb(10));
            break;

            case 2:
            addRows(
                new Row().images("Anna,Peter").lives(1).value(10).powerProb(10),
                new Row().images("Anna,Peter").lives(2).value(10).powerProb(10));
            break;

            case 3:
            addRows(
                new Row().images("Anna,Peter,Sophia").lives(1).value(15).powerProb(10),
                new Row().images("Anna,Peter,Sophia").lives(2).value(15).powerProb(10));
            break;

            case 4:
            addRows(
                new Row().images("Anna,Peter").lives(1).value(20).powerProb(10), 
                new Row().images("Sophia,Valerie,Stanze").lives(2).value(10).powerProb(10));
            break;

            case 5:
            addRows(
                new Row().images("Anna,Peter").lives(2).value(20).powerProb(10), 
                new Row().images("Sophia,Valerie,Stanze").lives(1).value(10).powerProb(10), 
                new Row().images("Armin,Hagen").lives(2).value(5).powerProb(10));
            break;

            case 6:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10), 
                new Row().images("Armin,Hagen").value(20).powerProb(10), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)));
            break;

            case 7:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10), 
                new Row().images("Armin,Hagen").value(20).powerProb(10).speedX(rndChoice(-2, 2)), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)));
            break;

            case 8:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10).speedX(rndChoice(-3, 3)), 
                new Row().images("Armin,Hagen").value(20).powerProb(10).speedX(rndChoice(-2, 2)), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)));
            break;

            case 9:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10).speedX(rndChoice(-3, 3)).turnBy(2), 
                new Row().images("Armin,Hagen").value(20).powerProb(10).speedX(rndChoice(-2, 2)), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)));
            break;

            case 10:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10).speedX(rndChoice(-3, 3)).turnBy(2), 
                new Row().images("Armin,Hagen").value(20).powerProb(10).speedX(rndChoice(-2, 2)).turnBy(-2), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)));
            break;

            case 11:
            addRows(
                new Row().images("Anna,Peter").value(30).powerProb(10).speedX(rndChoice(-3, 3)).turnBy(2), 
                new Row().images("Armin,Hagen").value(20).powerProb(10).speedX(rndChoice(-2, 2)).turnBy(-2), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-1, 1)).turnBy(2));
            break;

            case 12:
            addRows(
                new Row().images("Anna,Peter,Sophia").value(15).powerProb(10).speedX(rndChoice(-3, 3)).speedY(rndInt(2, 5)));
            break;

            case 13:
            addRows(
                new Row().images("Anna,Peter").value(20).powerProb(10).speedX(rndChoice(-3, 3)).speedY(rndInt(2, 5)), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedY(rndInt(2, 5)));
            break;

            case 14:
            addRows(
                new Row().images("Anna,Peter").value(20).powerProb(10).speedX(rndChoice(-3, 3)).speedY(rndInt(2, 5)), 
                new Row().images("Sophia,Valerie,Stanze").value(10).powerProb(10).speedX(rndChoice(-3, 3)).speedY(rndInt(2, 5)), 
                new Row().images("Armin,Hagen").value(5).powerProb(10).speedX(rndChoice(-3, 3)).speedY(rndInt(2, 5)));
            break;

            default:
            throw new IllegalArgumentException("No such level: " + level);
        }
    }

    private void addRows(Row... rows) {
        int rowIndex = 0;
        for (Row row : rows) {
            row.addToWorld(rowIndex++);
        }
    }

    private static String alt(int i, String... options) {
        return options[i % options.length];
    }

    private static int rndInt(int von, int bis) {
        return Greenfoot.getRandomNumber(bis - von) + von;
    }

    private static Power rndPower(int prozent) {
        if (rndInt(0, 100) < prozent) {
            int n = rndInt(0, 1000) % 9;
            int duration = rndInt(300, 600);
            switch (n) {
                case 0:
                return new BombPower(duration, 100 * rndInt(1, 3));
                case 1:
                return new PointsDistractionPower(-10 * rndInt(1, 10));
                case 2:
                return new BigPaddlePower(duration);
                case 3:
                return new SmallPaddlePower(duration);
                case 4:
                return new ExtraballPower();
                case 5:
                return new ExtraSuperballPower();
                case 6:
                return new SuperballPower(duration);
                case 7:
                return new ExtraSlowballPower();
                case 8:
                return new ExtraZickZackBallPower();
            }
        }
        return null;
    }

    private static <T> T rndChoice(T... values) {
        int randomIndex = rndInt(0, 100) % values.length;
        return values[randomIndex];
    }

}

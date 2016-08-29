import java.awt.Font;

/**
 * Power-down, das zum Punktabzug führt.
 * 
 * @author Zwickmann
 */
public class PointsDistractionPower extends Power
{
	private final int distraction;

	public PointsDistractionPower(int distraction) {
		super("Punkt-Abzug", 0);
		this.distraction = distraction;
		TextView textView = new TextView.Builder(true).text("" + distraction)
				.font(Resources.TEXTFONT.deriveFont(Font.PLAIN, 30)).build();
		setImage(textView.getImage());
	}

	@Override
	void activate() {
		game().changeScore(distraction);
	}
}

import java.awt.Font;

public class Resources
{
	public static Font TEXTFONT = new Font("Arial Black", Font.PLAIN, Cfg.NORMAL_FONTSIZE);

	static {
		try {
			TEXTFONT = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("fonts/cooperblack.ttf"))
					.deriveFont(Font.PLAIN, Cfg.NORMAL_FONTSIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

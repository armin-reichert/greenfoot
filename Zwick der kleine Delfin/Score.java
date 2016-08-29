import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Write a description of class Score here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
@XmlRootElement
public class Score
{
	private Date date;
	private String playerName;
	private int points;
	private int level;

	public Score() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}

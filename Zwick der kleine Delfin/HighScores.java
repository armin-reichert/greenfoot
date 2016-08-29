import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A high-score management class.
 * 
 * @author Prof. Zwickmann
 * @version 1.10.2014
 */
@XmlRootElement(namespace = "http://zwickmann.com/")
public class HighScores
{
	private final List<Score> scores;

	public HighScores() {
		scores = new ArrayList<Score>();
	}

	private static File getHighScoresFile() {
		return new File(System.getProperty("user.home"), "highscores.xml");
	}

	public static HighScores read() {
		File highscoresFile = getHighScoresFile();
		if (!highscoresFile.exists()) {
			new HighScores().write();
		}
		return JAXB.unmarshal(highscoresFile, HighScores.class);
	}

	public void write() {
		File highscoresFile = getHighScoresFile();
		JAXB.marshal(this, highscoresFile);
	}

	@XmlElement(name = "score")
	public List<Score> getScores() {
		return scores;
	}

	public void addScore(Date date, String playerName, int points, int level) {
		Score score = new Score();
		score.setDate(date);
		score.setPlayerName(playerName);
		score.setPoints(points);
		score.setLevel(level);
		scores.add(score);
	}

	public List<Score> getScoresDescending() {
		List<Score> scoresDescending = new ArrayList<Score>(scores);
		Collections.sort(scoresDescending, new Comparator<Score>() {
			public int compare(Score x, Score y) {
				if (x.getPoints() < y.getPoints()) {
					return 1;
				} else if (x.getPoints() > y.getPoints()) {
					return -1;
				}
				int delta = x.getDate().compareTo(y.getDate());
				if (delta != 0) {
					return -delta;
				}
				return x.getPlayerName().compareTo(y.getPlayerName());
			}
		});
		return scoresDescending;
	}

	public int getPointsRank(int points) {
		final List<Score> scores = getScoresDescending();
		int rank = 1;
		for (int i = 0; i < scores.size(); ++i) {
			if (i > 0 && scores.get(i).getPoints() != scores.get(i - 1).getPoints()) {
				++rank;
			}
			if (points >= scores.get(i).getPoints()) {
				break;
			}
		}
		return rank;
	}

	public int[] getRanking() {
		final List<Score> scores = getScoresDescending();
		final int[] ranking = new int[scores.size()];
		int rank = 1;
		for (int i = 0; i < scores.size(); ++i) {
			if (i > 0 && scores.get(i).getPoints() != scores.get(i - 1).getPoints()) {
				++rank;
			}
			ranking[i] = rank;
		}
		return ranking;
	}
}

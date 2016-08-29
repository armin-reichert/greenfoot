import greenfoot.GreenfootImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Shows the highscores table in a JFrame.
 * 
 * @author Prof. Zwickmann
 */
public class HighScoresView extends JTable
{
	private static final GreenfootImage ZWICK_IMAGE = new GreenfootImage("delfin-right.gif");

	private static final int COLUMN_TITLE = 0;
	private static final int COLUMN_WIDTH = 1;
	private static final int COLUMN_CLASS = 2;
	private static final Object[][] COLUMNS = {
			{ "", 100, String.class }, // Rank
			{ "Spieler", 200, String.class }, { "Punkte", 50, Integer.class }, { "Level", 20, Integer.class },
			{ "Datum", 200, String.class } };

	static void showDialog() {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(700, 400));
		frame.setLocationRelativeTo(null);
		frame.setTitle("Highscores");
		frame.setIconImage(ZWICK_IMAGE.getAwtImage());
		HighScoresView table = new HighScoresView();
		table.updateData();
		table.updateTableColumns();
		table.requestFocus();
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public HighScoresView() {
		setFont(new Font("Courier New", Font.BOLD, 16));
		setForeground(Color.BLUE);
		setShowVerticalLines(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	private void updateData() {
		HighScores scores = HighScores.read();
		setModel(createTableModel(scores.getScoresDescending(), scores.getRanking()));
	}

	private void updateTableColumns() {
		for (int i = 0; i < COLUMNS.length; ++i) {
			getColumnModel().getColumn(i).setPreferredWidth((Integer) COLUMNS[i][COLUMN_WIDTH]);
		}
	}

	private TableModel createTableModel(final java.util.List<Score> scoreList, final int[] ranking) {
		final DateFormat df = DateFormat.getDateTimeInstance();
		return new AbstractTableModel() {
			@Override
			public int getRowCount() {
				return scoreList.size();
			}

			@Override
			public int getColumnCount() {
				return COLUMNS.length;
			}

			@Override
			public Class<?> getColumnClass(int i) {
				return (Class<?>) COLUMNS[i][COLUMN_CLASS];
			}

			@Override
			public String getColumnName(int i) {
				return (String) COLUMNS[i][COLUMN_TITLE];
			}

			@Override
			public Object getValueAt(int row, int column) {
				Score score = scoreList.get(row);
				switch (column) {
				case 0: {
					if (row == 0 || row > 0 && ranking[row] != ranking[row - 1]) {
						return Commons.trim("" + ranking[row], 3) + ". Platz";
					} else {
						return "";
					}
				}
				case 1:
					return score.getPlayerName();
				case 2:
					return score.getPoints();
				case 3:
					return score.getLevel();
				case 4:
					return df.format(score.getDate());
				}
				throw new IllegalArgumentException("Column: " + column);
			}
		};
	}

}

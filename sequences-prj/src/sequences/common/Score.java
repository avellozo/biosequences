/*
 * Created on 08/12/2004
 */
package sequences.common;

/**
 * @author Augusto
 */
public class Score
{
	int	row, col;
	int	score;

	public Score(int row, int col, int score)
	{
		this.row = row;
		this.col = col;
		this.score = score;
	}

	public int getCol()
	{
		return this.col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	public int getRow()
	{
		return this.row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getScore()
	{
		return this.score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}
}

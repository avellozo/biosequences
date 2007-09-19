/*
 * Created on 18/10/2004
 */
package sequences.editgraph;

import java.util.Random;

/**
 * @author Augusto
 */
public class EGRandom<E extends EGRandom<E, X>, X extends Extender<E>> extends
		EditGraphImpl<E, X>
{
	int	rows, cols;
	int[][]	horizontal, vertical, diagonal;

	public EGRandom(int rows, int cols, Random random, int maxValue,
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory,
			X extender)
	{
		super(pathFactory, extender);
		this.cols = cols;
		this.rows = rows;
		int i, j;
		vertical = new int[rows][cols];
		diagonal = new int[rows][cols];

		i = 0;
		horizontal = new int[rows][cols];

		for (j = 1; j < cols; j++)
		{
			if (random.nextBoolean())
			{
				horizontal[i][j] = random.nextInt(maxValue);
			}
			else
			{
				horizontal[i][j] = -random.nextInt(maxValue);
			}
		}

		for (i = 1; i < rows; i++)
		{
			j = 0;
			if (random.nextBoolean())
			{
				vertical[i][j] = random.nextInt(maxValue);
			}
			else
			{
				vertical[i][j] = -random.nextInt(maxValue);
			}

			for (j = 1; j < cols; j++)
			{
				if (random.nextBoolean())
				{
					horizontal[i][j] = random.nextInt(maxValue);
				}
				else
				{
					horizontal[i][j] = -random.nextInt(maxValue);
				}
				if (random.nextBoolean())
				{
					vertical[i][j] = random.nextInt(maxValue);
				}
				else
				{
					vertical[i][j] = -random.nextInt(maxValue);
				}
				if (random.nextBoolean())
				{
					diagonal[i][j] = random.nextInt(maxValue);
				}
				else
				{
					diagonal[i][j] = -random.nextInt(maxValue);
				}
			}
		}
	}

	public int getRowMax()
	{
		return rows - 1;
	}

	public int getColMax()
	{
		return cols - 1;
	}

	protected int getWeightHorizontal(int row, int col)
	{
		return horizontal[row][col];
	}

	protected int getWeightVertical(int row, int col)
	{
		return vertical[row][col];
	}

	protected int getWeightDiagonal(int row, int col)
	{
		return diagonal[row][col];
	}

	public boolean isMatch(ArcDiagonal<E> arc) throws EGInvalidArcException
	{
		validateArc(arc);
		return arc.getWeight() > 0;
	}

}

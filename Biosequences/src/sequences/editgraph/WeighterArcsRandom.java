/*
 * Created on 18/10/2004
 */
package sequences.editgraph;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Augusto
 */
public class WeighterArcsRandom implements WeighterArcs
{
	int	rows, cols;
	int[][]	horizontal, vertical, diagonal;

	// Os valores das arestas vão de -maxValue a +maxValue
	public WeighterArcsRandom(int rows, int cols, Random random, int maxValue)
	{
		this.cols = cols;
		this.rows = rows;
		vertical = new int[rows][cols];
		diagonal = new int[rows][cols];
		horizontal = new int[rows][cols];

		int i, j;
		i = 0;
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

	public int getWeightHorizontal(int row, int col)
	{
		return horizontal[row][col];
	}

	public int getWeightVertical(int row, int col)
	{
		return vertical[row][col];
	}

	public int getWeightDiagonal(int row, int col)
	{
		return diagonal[row][col];
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraphSegment segment)
	{
		int i, j, w;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = segment.getRowMin() + 1; i <= segment.getRowMax(); i++)
			{
				for (j = segment.getColMin() + 1; j <= segment.getColMax(); j++)
				{
					if ((w = getWeightDiagonal(i, j)) != 0)
					{
						list.addLast(new ArcDiagonal(eg.getVertex(i, j), w));
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return list;
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraphSegment segment)
	{
		int i, j, w;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcHorizontal> list = new LinkedList<ArcHorizontal>();
		try
		{
			for (i = segment.getRowMin(); i <= segment.getRowMax(); i++)
			{
				for (j = segment.getColMin() + 1; j <= segment.getColMax(); j++)
				{
					if ((w = getWeightHorizontal(i, j)) != 0)
					{
						list.addLast(new ArcHorizontal(eg.getVertex(i, j), w));
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return list;
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraphSegment segment)
	{
		int i, j, w;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcVertical> list = new LinkedList<ArcVertical>();
		try
		{
			for (i = segment.getRowMin() + 1; i <= segment.getRowMax(); i++)
			{
				for (j = segment.getColMin(); j <= segment.getColMax(); j++)
				{
					if ((w = getWeightVertical(i, j)) != 0)
					{
						list.addLast(new ArcVertical(eg.getVertex(i, j), w));
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return list;
	}

}

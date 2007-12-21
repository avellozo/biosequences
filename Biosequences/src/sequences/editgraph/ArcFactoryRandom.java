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
public class ArcFactoryRandom implements ArcFactory
{
	int	rows, cols;
	int[][]	horizontal, vertical, diagonal;
	int		threshold;

	// Os valores das arestas v�o de -maxValue a +maxValue
	// vai at� cols-1 e rows-1
	// toda diagonal com valor maior ou igual a threshold � um match
	public ArcFactoryRandom(int rows, int cols, Random random, int maxValue, int threshold)
	{
		this.cols = cols;
		this.rows = rows;
		vertical = new int[rows][cols];
		diagonal = new int[rows][cols];
		horizontal = new int[rows][cols];
		this.threshold = threshold;

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

	public int getWeightHorizontalArc(int row, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateHorizontalArc(row, col))
		{
			throw new ExceptionInvalidVertex(new Vertex(row, col));
		}
		return horizontal[row][col];
	}

	public int getWeightVerticalArc(int row, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateVerticalArc(row, col))
		{
			throw new ExceptionInvalidVertex(new Vertex(row, col));
		}
		return vertical[row][col];
	}

	public int getWeightDiagonalArc(int row, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(row, col))
		{
			throw new ExceptionInvalidVertex(new Vertex(row, col));
		}
		return diagonal[row][col];
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		int i, j;
		ArcDiagonal arc;
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					arc = getDiagonalArc(eg.getVertex(i, j));
					if (arc.getWeight() != 0)
					{
						list.addLast(arc);
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

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		int i, j;
		ArcHorizontal arc;
		LinkedList<ArcHorizontal> list = new LinkedList<ArcHorizontal>();
		try
		{
			for (i = eg.getRowMin(); i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					arc = getHorizontalArc(eg.getVertex(i, j));
					if (arc.getWeight() != 0)
					{
						list.addLast(arc);
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

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		int i, j;
		ArcVertical arc;
		LinkedList<ArcVertical> list = new LinkedList<ArcVertical>();
		try
		{
			for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					arc = getVerticalArc(eg.getVertex(i, j));
					if (arc.getWeight() != 0)
					{
						list.addLast(arc);
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

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		return (endVertex != null && canCreateDiagonalArc(endVertex.getRow(), endVertex.getCol()));
	}

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		return (endVertex != null && (endVertex.getRow() < rows) && (endVertex.getCol() < cols)
			&& (endVertex.getRow() >= 0) && (endVertex.getCol() > 0));
	}

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		return (endVertex != null && (endVertex.getRow() < rows) && (endVertex.getCol() < cols)
			&& (endVertex.getRow() > 0) && (endVertex.getCol() >= 0));
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i > 0) && (j > 0));
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i >= 0) && (j > 0));
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i > 0) && (j >= 0));
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		int w = getWeightDiagonalArc(endVertex.getRow(), endVertex.getCol());
		return new ArcDiagonal(endVertex, w, w >= threshold);
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateHorizontalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcHorizontal(endVertex, getWeightHorizontalArc(endVertex.getRow(), endVertex.getCol()));
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateVerticalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcVertical(endVertex, getWeightVerticalArc(endVertex.getRow(), endVertex.getCol()));
	}

}

/*
 * Created on 18/10/2004
 */
package sequences.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.factories.ArcDiagonalFactory;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 */
public class ArcDiagonalFactoryRandom implements ArcDiagonalFactory
{
	int		rows, cols;
	int[][]	diagonal;
	int		threshold;

	// Os valores das arestas vão de -maxValue a +maxValue
	// vai até cols-1 e rows-1
	// toda diagonal com valor maior ou igual a threshold é um match
	public ArcDiagonalFactoryRandom(int rows, int cols, Random random, int maxValue, int threshold)
	{
		this.cols = cols;
		this.rows = rows;
		diagonal = new int[rows][cols];
		this.threshold = threshold;

		int i, j;

		for (i = 1; i < rows; i++)
		{
			for (j = 1; j < cols; j++)
			{
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

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		return (endVertex != null && canCreateDiagonalArc(endVertex.getRow(), endVertex.getCol()));
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i > 0) && (j > 0));
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
}

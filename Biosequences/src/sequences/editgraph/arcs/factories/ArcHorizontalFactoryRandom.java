/*
 * Created on 18/10/2004
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 */
public class ArcHorizontalFactoryRandom implements ArcHorizontalFactory
{
	int		rows, cols;
	int[][]	horizontal;

	// Os valores das arestas vão de -maxValue a +maxValue
	// vai até cols-1 e rows-1
	public ArcHorizontalFactoryRandom(int rows, int cols, Random random, int maxValue)
	{
		this.cols = cols;
		this.rows = rows;
		horizontal = new int[rows][cols];

		int i, j;
		for (i = 0; i < rows; i++)
		{
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

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		return (endVertex != null && (endVertex.getRow() < rows) && (endVertex.getCol() < cols)
			&& (endVertex.getRow() >= 0) && (endVertex.getCol() > 0));
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i >= 0) && (j > 0));
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateHorizontalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcHorizontal(endVertex, getWeightHorizontalArc(endVertex.getRow(), endVertex.getCol()));
	}

}

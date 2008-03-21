/*
 * Created on 18/10/2004
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 */
public class ArcVerticalFactoryRandom implements ArcVerticalFactory
{
	int		rows, cols;
	int[][]	vertical;

	// Os valores das arestas vão de -maxValue a +maxValue
	// vai até cols-1 e rows-1
	// toda diagonal com valor maior ou igual a threshold é um match
	public ArcVerticalFactoryRandom(int rows, int cols, Random random, int maxValue)
	{
		this.cols = cols;
		this.rows = rows;
		vertical = new int[rows][cols];

		int i, j;
		for (i = 1; i < rows; i++)
		{
			for (j = 0; j < cols; j++)
			{
				if (random.nextBoolean())
				{
					vertical[i][j] = random.nextInt(maxValue);
				}
				else
				{
					vertical[i][j] = -random.nextInt(maxValue);
				}
			}
		}
	}

	public int getWeightVerticalArc(int row, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateVerticalArc(row, col))
		{
			throw new ExceptionInvalidVertex(new Vertex(row, col));
		}
		return vertical[row][col];
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

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		return (endVertex != null && (endVertex.getRow() < rows) && (endVertex.getCol() < cols)
			&& (endVertex.getRow() > 0) && (endVertex.getCol() >= 0));
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		return ((i < rows) && (j < cols) && (i > 0) && (j >= 0));
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

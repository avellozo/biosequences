/*
 * Criado em 09/08/2004
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.ArcDiagonalFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public abstract class ArcDiagonalFactoryMatchMismatch implements ArcDiagonalFactory
{
	protected int	match, mismatch;

	public ArcDiagonalFactoryMatchMismatch(int match, int mismatch)
	{
		this.match = match;
		this.mismatch = mismatch;
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return new ArcDiagonal(endVertex, getWeightDiagonalArc(endVertex.getRow(), endVertex.getCol()),
			isMatch(endVertex));
	}

	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return isMatch(endVertex.getRow(), endVertex.getCol());
	}

	public abstract boolean isMatch(int i, int j) throws ExceptionInvalidVertex;

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		return (endVertex != null && (canCreateDiagonalArc(endVertex.getRow(), endVertex.getCol())));
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		return isMatch(i, j) ? match : mismatch;
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

}
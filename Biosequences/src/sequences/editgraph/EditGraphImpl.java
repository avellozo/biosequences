/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import java.util.LinkedList;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public abstract class EditGraphImpl<E extends EditGraphImpl<E, X>, X extends Extender<E>>
		implements EditGraph<E, X>
{
	int												iMin	= 0, jMin = 0;

	OptimumPathFactory<E, ? extends OptimumPath<E>>	optimumPathFactory;

	X												extender;

	public EditGraphImpl(
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory,
			X extender)
	{
		this.optimumPathFactory = pathFactory;
		this.extender = extender;
	}

	public <P extends OptimumPath<E>> P getOptimumPath(VertexRange<E> range,
			boolean local, OptimumPathFactory<E, P> pathFactory)
			throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		if (range == null)
		{
			throw new EGInvalidRangeException(
				"Impossible to calculate optimum path to null range");
		}
		if (pathFactory == null)
		{
			throw new EGInvalidEditGraphException("Edit graph without path factory: impossible to create path.");
		}
		return pathFactory.createPath(range, local);
	}

	public OptimumPath<E> getOptimumPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return getOptimumPath(range, local, getOptimumPathFactory());
	}

	// get maximum path from up left corner to down right corner
	public OptimumPath<E> getOptimumPath(boolean local) throws EGInvalidEditGraphException
	{
		try
		{
			return getOptimumPath(getFullVertexRange(), local);
		}
		catch (EGInvalidRangeException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public boolean isExtended()
	{
		return extender != null;
	}

	public OptimumPathFactory<E, ? extends OptimumPath<E>> getOptimumPathFactory()
	{
		return optimumPathFactory;
	}

	public void setPathFactory(
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory)
	{
		
		this.optimumPathFactory = pathFactory;
	}

	public X getExtender()
	{
		return extender;
	}

	public int getRowMin()
	{
		return iMin;
	}

	public int getColMin()
	{
		return jMin;
	}

	public abstract int getRowMax();

	public abstract int getColMax();

	// returns true if v1 dominatse v2
	public boolean dominates(Vertex<E> v1, Vertex<E> v2)
			throws EGInvalidVertexException
	{
		if ((!isValidVertex(v1)) || (!isValidVertex(v2)))
		{
			throw new EGInvalidVertexException(v1,
				"Can't do dominate comparation to vertex " + v2);
		}
		return verifyDominates(v1.getI(), v1.getJ(), v2.getI(), v2.getJ());
	}

	public boolean dominates(int row1, int col1, int row2, int col2)
			throws EGInvalidVertexException
	{
		if ((!existsVertex(row1, col1)) || (!existsVertex(row2, col2)))
		{
			throw new EGInvalidVertexException(row1, col1,
				"Can't do dominate comparation to vertex " + row2 + " " + col2);
		}
		return verifyDominates(row1, col1, row2, col2);
	}

	protected boolean verifyDominates(int row1, int col1, int row2, int col2)
	{
		return ((row1 <= row2) && (col1 <= col2));
	}

	public VertexRange<E> getFullVertexRange()
	{
		try
		{
			return new VertexRange<E>(getVertex(getRowMin(), getColMin()),
				getVertex(getRowMax(), getColMax()));
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public Vertex<E> getVertex(int row, int col)
			throws EGInvalidVertexException
	{
		return new Vertex<E>(row, col, (E) this);
	}

	// Cria uma lista somente com arcos que são positivos do grafo
	public LinkedList<ArcDiagonal<E>> getMatches()
	{
		ArcDiagonal<E> arc;
		int i, j;
		LinkedList<ArcDiagonal<E>> list = new LinkedList<ArcDiagonal<E>>();
		try
		{
			for (i = 1; i <= getRowMax(); i++)
			{
				for (j = 1; j <= getColMax(); j++)
				{
					if (isMatch(arc = getDiagonalArc(getVertex(i, j))))
					{
						list.addLast(arc);
					}
				}
			}
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
		catch (EGInvalidArcException e1)
		{
			e1.printStackTrace();
			throw new EGInternalException();
		}
		return list;
	}

	public boolean existsVertex(int row, int col)
	{
		return ((row >= getRowMin()) && (row <= getRowMax())
			&& (col >= getColMin()) && (col <= getColMax()));
	}

	public boolean isValidVertex(Vertex<E> v)
	{
		return ((v != null) && (v.getEditGraph() == this));
	}

	public boolean existsDiagonalArc(int row, int col)
	{
		return existsVertex(row - 1, col - 1);
	}

	public boolean existsHorizontalArc(int row, int col)
	{
		return existsVertex(row, col - 1);
	}

	public boolean existsVerticalArc(int row, int col)
	{
		return existsVertex(row - 1, col);
	}

	public boolean existsExtendedArc(VertexRange<E> range)
	{
		return (isExtended() && getExtender().existsExtendedArc(range));
	}

	protected abstract int getWeightHorizontal(int row, int col);

	protected abstract int getWeightVertical(int row, int col);

	protected abstract int getWeightDiagonal(int row, int col);

	public int getWeightDiagonalArc(int row, int col)
			throws EGInvalidArcException
	{
		if (!existsDiagonalArc(row, col))
		{
			throw new EGInvalidArcException(EditGraph.DIAGONAL,
				"Doesn't exist diagonal arc on vertex (" + row + ',' + col
					+ ')');
		}
		return getWeightDiagonal(row, col);
	}

	public int getWeightHorizontalArc(int row, int col)
			throws EGInvalidArcException
	{
		if (!existsHorizontalArc(row, col))
		{
			throw new EGInvalidArcException(EditGraph.HORIZONTAL,
				"Doesn't exist horizontal arc on vertex (" + row + ',' + col
					+ ')');
		}
		return getWeightHorizontal(row, col);
	}

	public int getWeightVerticalArc(int row, int col)
			throws EGInvalidArcException
	{
		if (!existsVerticalArc(row, col))
		{
			throw new EGInvalidArcException(EditGraph.VERTICAL,
				"Doesn't exist vertical arc on vertex (" + row + ',' + col
					+ ')');
		}
		return getWeightVertical(row, col);
	}

	public int getWeightExtendedArc(VertexRange<E> range)
			throws EGInvalidRangeException
	{
		if (!existsExtendedArc(range))
		{
			throw new EGInvalidRangeException("Doesn't exist extended arc on vertex range" + range);
		}
		return getExtender().getWeightExtended(range);
	}

	public ArcDiagonal<E> getDiagonalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		if (!isValidVertex(endVertex))
		{
			throw new EGInvalidVertexException(endVertex);
		}
		return new ArcDiagonal<E>(endVertex, getWeightDiagonal(
			endVertex.getI(), endVertex.getJ()));
	}

	public ArcHorizontal<E> getHorizontalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		if (!isValidVertex(endVertex))
		{
			throw new EGInvalidVertexException(endVertex);
		}
		return new ArcHorizontal<E>(endVertex, getWeightHorizontal(endVertex
			.getI(), endVertex.getJ()));
	}

	public ArcVertical<E> getVerticalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		if (!isValidVertex(endVertex))
		{
			throw new EGInvalidVertexException(endVertex);
		}
		return new ArcVertical<E>(endVertex, getWeightVertical(
			endVertex.getI(), endVertex.getJ()));
	}

	public ArcExtended<E> getExtendedArc(VertexRange<E> range) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		if (!isExtended())
		{
			throw new EGInvalidEditGraphException("Edit graph is not extended.");
		}
		return getExtender().getExtendedArc(range);
	}

	public boolean validateArc(Arc<E> arc) throws EGInvalidArcException
	{
		if (arc == null || arc.getEditGraph() != this)
		{
			throw new EGInvalidArcException("Arc invalid: " + arc);
		}
		return true;
	}
}// End of EditGraphBasic


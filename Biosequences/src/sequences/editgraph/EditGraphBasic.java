/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import java.util.List;

/**
 * @author Augusto
 * @data 09/08/2004
 */
/**
 * @author Augusto F. Vellozo
 */
public class EditGraphBasic implements EditGraph, Cloneable
{
	int			rowMin, rowMax, colMin, colMax;

	ArcFactory	arcFactory;

	public EditGraphBasic(int rowMin, int rowMax, int colMin, int colMax, ArcFactory arcFactory)
	{
		super();
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.colMin = colMin;
		this.colMax = colMax;
		this.arcFactory = arcFactory;
	}

	public int getRowMin()
	{
		return rowMin;
	}

	public int getColMin()
	{
		return colMin;
	}

	public int getRowMax()
	{
		return rowMax;
	}

	public int getColMax()
	{
		return colMax;
	}

	public ArcFactory getArcFactory()
	{
		return arcFactory;
	}

	public boolean existsVertex(int row, int col)
	{
		return ((row >= getRowMin()) && (row <= getRowMax()) && (col >= getColMin()) && (col <= getColMax()));
	}

	public boolean existsVertex(Vertex v)
	{
		return (existsVertex(v.getRow(), v.getCol()));
	}

	public boolean existsDiagonalArc(Vertex endVertex)
	{
		return (isValidVertexParam(endVertex) && existsVertex(endVertex.getRow() - 1, endVertex.getCol() - 1));
	}

	public boolean existsHorizontalArc(Vertex endVertex)
	{
		return (isValidVertexParam(endVertex) && existsVertex(endVertex.getRow(), endVertex.getCol() - 1));
	}

	public boolean existsVerticalArc(Vertex endVertex)
	{
		return (isValidVertexParam(endVertex) && existsVertex(endVertex.getRow() - 1, endVertex.getCol()));
	}

	protected boolean isValidVertexParam(Vertex v)
	{
		return ((v != null) && existsVertex(v));
	}

	public Vertex getVertex(int row, int col) throws ExceptionInvalidVertex
	{
		if (!existsVertex(row, col))
		{
			throw new ExceptionInvalidVertex(row, col);
		}
		return new Vertex(row, col);
	}

	public EditGraph getSegment(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (isValidVertexParam(beginVertex) && isValidVertexParam(endVertex) && beginVertex.dominates(endVertex))
		{
			try
			{
				EditGraphBasic eg = (EditGraphBasic) this.clone();
				eg.colMax = endVertex.getCol();
				eg.colMin = beginVertex.getCol();
				eg.rowMax = endVertex.getRow();
				eg.rowMin = beginVertex.getRow();
				return eg;
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create a edit graph's segment with begin vertex:" + beginVertex);
		}
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return arcFactory.getDiagonalArc(endVertex);
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return arcFactory.getHorizontalArc(endVertex);
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return arcFactory.getVerticalArc(endVertex);
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs()
	{
		return arcFactory.getNonZeroDiagonalArcs(this);
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs()
	{
		return arcFactory.getNonZeroHorizontalArcs(this);
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs()
	{
		return arcFactory.getNonZeroVerticalArcs(this);
	}

	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return arcFactory.isMatch(endVertex);
	}

	/*	public <P extends OptimumPath> P getOptimumPath(VertexRange range, boolean local,
	OptimumPathFactory<E, P> pathFactory) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
	if (range == null)
	{
	throw new EGInvalidRangeException("Impossible to calculate optimum path to null range");
	}
	if (pathFactory == null)
	{
	throw new EGInvalidEditGraphException("Edit graph without path factory: impossible to create path.");
	}
	return pathFactory.createPath(range, local);
	}

	public OptimumPath getOptimumPath(VertexRange range, boolean local) throws EGInvalidRangeException,
	EGInvalidEditGraphException
	{
	return getOptimumPath(range, local, getOptimumPathFactory());
	}

	// get maximum path from up left corner to down right corner
	public OptimumPath getOptimumPath(boolean local) throws EGInvalidEditGraphException
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
	*/
	/*	public OptimumPathFactory<E, ? extends OptimumPath> getOptimumPathFactory()
	{
	return optimumPathFactory;
	}

	public void setPathFactory(OptimumPathFactory<E, ? extends OptimumPath> pathFactory)
	{

	this.optimumPathFactory = pathFactory;
	}
	*/
	//public VertexesOfExtension getFullVertexRange()
	//{
	//try
	//{
	//return new VertexesOfExtension(getVertex(getRowMin(), getColMin()), getVertex(getRowMax(), getColMax()));
	//}
	//catch (EGInvalidVertexException e)
	//{
	//e.printStackTrace();
	//throw new EGInternalException();
	//}
	//}
	//
}

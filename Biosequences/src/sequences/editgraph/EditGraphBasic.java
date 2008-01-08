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
	int						rowMin, rowMax, colMin, colMax;

	ArcHorizontalFactory	arcHFactory;
	ArcVerticalFactory		arcVFactory;
	ArcDiagonalFactory		arcDFactory;
	ArcExtendedFactory		arcEFactory;

	public EditGraphBasic(int rowMin, int rowMax, int colMin, int colMax, ArcHorizontalFactory arcHFactory,
			ArcVerticalFactory arcVFactory, ArcDiagonalFactory arcDFactory, ArcExtendedFactory arcEFactory)
	{
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.colMin = colMin;
		this.colMax = colMax;
		this.arcHFactory = arcHFactory;
		this.arcVFactory = arcVFactory;
		this.arcDFactory = arcDFactory;
		this.arcEFactory = arcEFactory;
	}

	public EditGraphBasic(int rowMax, int colMax, ArcHorizontalFactory arcHFactory, ArcVerticalFactory arcVFactory,
			ArcDiagonalFactory arcDFactory, ArcExtendedFactory arcEFactory)
	{
		this(0, rowMax, 0, colMax, arcHFactory, arcVFactory, arcDFactory, arcEFactory);
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

	public ArcHorizontalFactory getArcHorizontalFactory()
	{
		return arcHFactory;
	}

	public ArcVerticalFactory getArcVerticalFactory()
	{
		return arcVFactory;
	}

	public ArcDiagonalFactory getArcDiagonalFactory()
	{
		return arcDFactory;
	}

	public ArcExtendedFactory getArcExtendedFactory()
	{
		return arcEFactory;
	}

	public boolean existsVertex(int row, int col)
	{
		return ((row >= getRowMin()) && (row <= getRowMax()) && (col >= getColMin()) && (col <= getColMax()));
	}

	public boolean existsVertex(Vertex v)
	{
		return ((v != null) && (existsVertex(v.getRow(), v.getCol())));
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

	public boolean existsExtendedArc(VertexRange vertexRange)
	{
		return (isValidVertexParam(vertexRange.getBeginVertex()) && isValidVertexParam(vertexRange.getEndVertex()));
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

	public EditGraphBasic getSegment(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (isValidVertexParam(vertexRange.getBeginVertex()) && isValidVertexParam(vertexRange.getEndVertex()))
		{
			try
			{
				EditGraphBasic eg = (EditGraphBasic) this.clone();
				eg.colMax = vertexRange.getEndVertex().getCol();
				eg.colMin = vertexRange.getBeginVertex().getCol();
				eg.rowMax = vertexRange.getEndVertex().getRow();
				eg.rowMin = vertexRange.getBeginVertex().getRow();
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
			throw new ExceptionInvalidVertex(vertexRange.getEndVertex(),
				"It's impossible to create a edit graph's segment with begin vertex:" + vertexRange.getBeginVertex());
		}
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an diagonal arc with this end vertex:" + endVertex);
		}
		return arcDFactory.getDiagonalArc(endVertex);
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsHorizontalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an horizontal arc with this end vertex:" + endVertex);
		}
		return arcHFactory.getHorizontalArc(endVertex);
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsVerticalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an vertical arc with this end vertex:" + endVertex);
		}
		return arcVFactory.getVerticalArc(endVertex);
	}

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (existsExtendedArc(vertexRange))
		{
			return arcEFactory.getExtendedArc(vertexRange);
		}
		else
		{
			throw new ExceptionInvalidVertex(vertexRange.getEndVertex(),
				"It's impossible to create an extended arc with begin vertex:" + vertexRange.getBeginVertex());
		}
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs()
	{
		return arcDFactory.getNonZeroDiagonalArcs(this);
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs()
	{
		return arcHFactory.getNonZeroHorizontalArcs(this);
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs()
	{
		return arcVFactory.getNonZeroVerticalArcs(this);
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcDFactory.getWeightDiagonalArc(i, j);
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcHFactory.getWeightHorizontalArc(i, j);
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcVFactory.getWeightVerticalArc(i, j);
	}

	public VertexRange getFullRange()
	{
		try
		{
			return new VertexRange(getVertex(getRowMin(), getColMin()), getVertex(getRowMax(), getColMax()));
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	//	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	//	{
	//		return arcFactory.isMatch(endVertex);
	//	}
	//
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
	public ArcHorizontalFactory getArcHFactory()
	{
		return arcHFactory;
	}

	public ArcVerticalFactory getArcVFactory()
	{
		return arcVFactory;
	}

	public ArcDiagonalFactory getArcDFactory()
	{
		return arcDFactory;
	}

	public ArcExtendedFactory getArcEFactory()
	{
		return arcEFactory;
	}
}

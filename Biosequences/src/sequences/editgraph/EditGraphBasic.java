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
public class EditGraphBasic implements EditGraph
{
	int				rowMin, rowMax, colMin, colMax;

	WeighterArcs	weighter;

	// OptimumPathFactory<E, ? extends OptimumPath> optimumPathFactory;

	public EditGraphBasic(int rowMin, int rowMax, int colMin, int colMax, WeighterArcs weighter)
	{
		super();
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.colMin = colMin;
		this.colMax = colMax;
		this.weighter = weighter;
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

	public WeighterArcs getWeighter()
	{
		return weighter;
	}

	public boolean existsVertex(int row, int col)
	{
		return ((row >= getRowMin()) && (row <= getRowMax()) && (col >= getColMin()) && (col <= getColMax()));
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

	public Vertex getVertex(int row, int col) throws ExceptionInvalidVertex
	{
		return new Vertex(row, col, this);
	}

	protected boolean isValidVertexParam(Vertex v)
	{
		return ((v != null) && (v.getEditGraph() == this));
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!isValidVertexParam(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcDiagonal(endVertex, getWeighter().getWeightDiagonal(endVertex.getI(), endVertex.getJ()));
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!isValidVertexParam(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcHorizontal(endVertex, getWeighter().getWeightHorizontal(endVertex.getI(), endVertex.getJ()));
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!isValidVertexParam(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcVertical(endVertex, getWeighter().getWeightVertical(endVertex.getI(), endVertex.getJ()));
	}

	// returns true if v1 dominates v2
	public boolean dominates(Vertex v1, Vertex v2) throws ExceptionInvalidVertex
	{
		if ((!isValidVertexParam(v1)) || (!isValidVertexParam(v2)))
		{
			throw new ExceptionInvalidVertex(v1, "Can't do the dominate comparation to vertex " + v2);
		}
		return verifyDominates(v1.getI(), v1.getJ(), v2.getI(), v2.getJ());
	}

	public boolean dominates(int row1, int col1, int row2, int col2) throws ExceptionInvalidVertex
	{
		if ((!existsVertex(row1, col1)) || (!existsVertex(row2, col2)))
		{
			throw new ExceptionInvalidVertex(row1, col1, "Can't do dominate comparation to vertex " + row2 + " " + col2);
		}
		return verifyDominates(row1, col1, row2, col2);
	}

	protected boolean verifyDominates(int row1, int col1, int row2, int col2)
	{
		return ((row1 <= row2) && (col1 <= col2));
	}

	public List< ? extends ArcDiagonal> getPositiveDiagonalArcs()
	{
		return weighter.getNonZeroDiagonalArcs(getFullSegment());
	}

	public List< ? extends ArcHorizontal> getPositiveHorizontalArcs()
	{
		return weighter.getNonZeroHorizontalArcs(getFullSegment());
	}

	public List< ? extends ArcVertical> getPositiveVerticalArcs()
	{
		return weighter.getNonZeroVerticalArcs(getFullSegment());
	}

	public EditGraphSegment getFullSegment()
	{
		try
		{
			return new EditGraphSegment(getVertex(getRowMin(), getColMin()), getVertex(getRowMax(), getColMax()));
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
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

/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import java.util.List;

import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.arcs.factories.ArcDiagonalFactory;
import sequences.editgraph.arcs.factories.ArcExtendedFactory;
import sequences.editgraph.arcs.factories.GapSetFactory;
import sequences.editgraph.arcs.factories.ArcHorizontalFactory;
import sequences.editgraph.arcs.factories.ArcVerticalFactory;
import sequences.editgraph.arcs.factories.GapFactory;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 * @data 09/08/2004
 */
/**
 * @author Augusto F. Vellozo
 */
public class EditGraphBasic implements EditGraphWithGapSet, EditGraphExtended, Cloneable
{
	int						rowMin, rowMax, colMin, colMax;

	ArcHorizontalFactory	arcHFactory;
	ArcVerticalFactory		arcVFactory;
	ArcDiagonalFactory		arcDFactory;
	ArcExtendedFactory		arcEFactory;
	GapSetFactory		arcGapSetFactory;

	public EditGraphBasic(int rowMin, int rowMax, int colMin, int colMax, ArcHorizontalFactory arcHFactory,
			ArcVerticalFactory arcVFactory, ArcDiagonalFactory arcDFactory, ArcExtendedFactory arcEFactory,
			GapSetFactory arcGapSetFactory)
	{
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.colMin = colMin;
		this.colMax = colMax;
		this.arcHFactory = arcHFactory;
		this.arcVFactory = arcVFactory;
		this.arcDFactory = arcDFactory;
		this.arcEFactory = arcEFactory;
		this.arcGapSetFactory = arcGapSetFactory;
	}

	public EditGraphBasic(int rowMax, int colMax, ArcHorizontalFactory arcHFactory, ArcVerticalFactory arcVFactory,
			ArcDiagonalFactory arcDFactory, ArcExtendedFactory arcEFactory, GapSetFactory arcGapSetFactory)
	{
		this(0, rowMax, 0, colMax, arcHFactory, arcVFactory, arcDFactory, arcEFactory, arcGapSetFactory);
	}

	public EditGraphBasic(int rowMax, int colMax, GapFactory gapFactory, ArcDiagonalFactory arcDFactory,
			ArcExtendedFactory arcEFactory)
	{
		this(rowMax, colMax, gapFactory, gapFactory, arcDFactory, arcEFactory, gapFactory);
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

	public GapSetFactory getArcGapSetFactory()
	{
		return arcGapSetFactory;
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
		return (isValidVertexParam(endVertex) && getArcDiagonalFactory() != null && getArcDiagonalFactory().canCreateDiagonalArc(
			endVertex));
	}

	public boolean existsHorizontalArc(Vertex endVertex)
	{
		return (isValidVertexParam(endVertex) && getArcHorizontalFactory() != null && getArcHorizontalFactory().canCreateHorizontalArc(
			endVertex));
	}

	public boolean existsVerticalArc(Vertex endVertex)
	{
		return (isValidVertexParam(endVertex) && getArcVerticalFactory() != null && getArcVerticalFactory().canCreateVerticalArc(
			endVertex));
	}

	public boolean existsExtendedArc(VertexRange vertexRange)
	{
		return (isValidVertexParam(vertexRange) && getArcExtendedFactory() != null && getArcExtendedFactory().canCreateExtendedArc(
			vertexRange));
	}

	protected boolean isValidVertexParam(Vertex v)
	{
		return ((v != null) && existsVertex(v));
	}

	protected boolean isValidVertexParam(VertexRange range)
	{
		return ((range != null) && existsVertex(range.getBeginVertex()) && existsVertex(range.getEndVertex()));
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
		return getArcHorizontalFactory().getHorizontalArc(endVertex);
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsVerticalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an vertical arc with this end vertex:" + endVertex);
		}
		return getArcVerticalFactory().getVerticalArc(endVertex);
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

	public boolean existsGapSetHorizontalArc(int beginCol, Vertex endVertex)
	{
		return isValidVertexParam(endVertex) && getArcGapSetFactory() != null
			&& getArcGapSetFactory().canCreateGapSetHorizontalArc(beginCol, endVertex);
	}

	public boolean existsGapSetVerticalArc(int beginRow, Vertex endVertex)
	{
		return isValidVertexParam(endVertex) && getArcGapSetFactory() != null
			&& getArcGapSetFactory().canCreateGapSetVerticalArc(beginRow, endVertex);
	}

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (existsGapSetHorizontalArc(beginCol, endVertex))
		{
			return getArcGapSetFactory().getGapSetHorizontalArc(beginCol, endVertex);
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an gap set horizontal arc with begin Col:" + beginCol);
		}
	}

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (existsGapSetVerticalArc(beginRow, endVertex))
		{
			return getArcGapSetFactory().getGapSetVerticalArc(beginRow, endVertex);
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex,
				"It's impossible to create an gap set vertical arc with begin row:" + beginRow);
		}
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs()
	{
		return arcDFactory.getNonZeroDiagonalArcs(this);
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs()
	{
		return getArcHorizontalFactory().getNonZeroHorizontalArcs(this);
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs()
	{
		return getArcVerticalFactory().getNonZeroVerticalArcs(this);
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcDFactory.getWeightDiagonalArc(i, j);
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return getArcHorizontalFactory().getWeightHorizontalArc(i, j);
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return getArcVerticalFactory().getWeightVerticalArc(i, j);
	}

	public int getGapSetHorizontalWeight(int beginCol, int row, int endCol) throws ExceptionInvalidVertex
	{
		return getArcGapSetFactory().getWeightGapSetHorizontalArc(beginCol, row, endCol);
	}

	public int getGapSetVerticalWeight(int beginRow, int endRow, int col) throws ExceptionInvalidVertex
	{
		return getArcGapSetFactory().getWeightGapSetVerticalArc(beginRow, endRow, col);
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

	// public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	// {
	// return arcFactory.isMatch(endVertex);
	// }
	//
	/*
	 * public <P extends OptimumPath> P getOptimumPath(VertexRange range, boolean local, OptimumPathFactory<E, P>
	 * pathFactory) throws EGInvalidRangeException, EGInvalidEditGraphException { if (range == null) { throw new
	 * EGInvalidRangeException("Impossible to calculate optimum path to null range"); } if (pathFactory == null) { throw
	 * new EGInvalidEditGraphException("Edit graph without path factory: impossible to create path."); } return
	 * pathFactory.createPath(range, local); }
	 * 
	 * public OptimumPath getOptimumPath(VertexRange range, boolean local) throws EGInvalidRangeException,
	 * EGInvalidEditGraphException { return getOptimumPath(range, local, getOptimumPathFactory()); }
	 *  // get maximum path from up left corner to down right corner public OptimumPath getOptimumPath(boolean local)
	 * throws EGInvalidEditGraphException { try { return getOptimumPath(getFullVertexRange(), local); } catch
	 * (EGInvalidRangeException e) { e.printStackTrace(); throw new EGInternalException(); } }
	 */
	/*
	 * public OptimumPathFactory<E, ? extends OptimumPath> getOptimumPathFactory() { return optimumPathFactory; }
	 * 
	 * public void setPathFactory(OptimumPathFactory<E, ? extends OptimumPath> pathFactory) {
	 * 
	 * this.optimumPathFactory = pathFactory; }
	 */
	// public VertexesOfExtension getFullVertexRange()
	// {
	// try
	// {
	// return new VertexesOfExtension(getVertex(getRowMin(), getColMin()), getVertex(getRowMax(), getColMax()));
	// }
	// catch (EGInvalidVertexException e)
	// {
	// e.printStackTrace();
	// throw new EGInternalException();
	// }
	// }
	//
}

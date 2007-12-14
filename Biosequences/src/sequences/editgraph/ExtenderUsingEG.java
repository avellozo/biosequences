package sequences.editgraph;

public class ExtenderUsingEG<E extends EditGraph<E, ? extends ExtenderUsingEG<E, EGExtender>>, EGExtender extends EditGraph<EGExtender, ? extends Extender<EGExtender>>>
		implements Extender
{

	// egExtender: edit graph over which an extension is got. The vertexes of
	// extended edit graph can be diferent of egExtender.
	// egExtended: edit graph over which an extension is done.

	final EGExtender	egExtender;
	final int			extensionPenalty;

	public ExtenderUsingEG(EGExtender egExtender, int extensionPenalty) throws ExceptionInvalidEditGraph
	{
		if (egExtender == null)
		{
			throw new ExceptionInvalidEditGraph("Can't create extender over an edit graph without edit graph extender.");
		}
		if (egExtender.getOptimumPathFactory() == null)
		{
			throw new ExceptionInvalidEditGraph("Edit graph extender without path factory: impossible to create extender.");
		}
		this.egExtender = egExtender;
		this.extensionPenalty = extensionPenalty;
	}

	public final EGExtender getEGExtender()
	{
		return egExtender;
	}

	public final int getExtensionPenalty()
	{
		return extensionPenalty;
	}

	// range: vertices in extended edit graph
	public final int getWeightExtended(EditGraphSegment range) throws EGInvalidVertexesOfExtensionException
	{
		try
		{
			return getOptimumPathEGExtender(range).getScore() - getExtensionPenalty();
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	// range: beginVertex and endVertex in extended edit graph
	public final boolean existsExtendedArc(EditGraphSegment range)
	{
		try
		{
			transformVertexRange(range);
		}
		catch (EGInvalidVertexesOfExtensionException e)
		{
			// e.printStackTrace();
			return false;
		}
		return !range.getBeginVertex().equals(range.getEndVertex());
	}

	public final ArcExtendedOverEGExtender<E, EGExtender> getExtendedArc(EditGraphSegment range)
			throws EGInvalidVertexesOfExtensionException
	{
		if (range == null)
		{
			throw new EGInvalidVertexesOfExtensionException("Invalid range: null");
		}
		try
		{
			return new ArcExtendedOverEGExtender<E, EGExtender>(range);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new EGInvalidVertexesOfExtensionException(range, "Invalid range");			
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			throw new EGInvalidVertexesOfExtensionException(range, "Invalid range");			
		}
	}

	// rangeExtended is a range in an extended graph
	public final OptimumPath<EGExtender> getOptimumPathEGExtender(EditGraphSegment rangeExtended)
			throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
	{
		return getEGExtender().getOptimumPath(transformVertexRange(rangeExtended), false);
	}

	// return weight of the vertical arc on the extender edit graph which
	// corresponds to the row,col on the extended graph
	public final int getEGExtenderWeightVerticalArc(int row, int col) throws ExceptionInvalidArc
	{
		return getEGExtender().getWeightVerticalArc(transformRow(row), transformCol(col));
	}

	public final int getEGExtenderWeightHorizontalArc(int row, int col) throws ExceptionInvalidArc
	{
		return getEGExtender().getWeightHorizontalArc(transformRow(row), transformCol(col));
	}

	public final int getEGExtenderWeightDiagonalArc(int row, int col) throws ExceptionInvalidArc
	{
		return getEGExtender().getWeightDiagonalArc(transformRow(row), transformCol(col));
	}

	// return row index of extender edit graph which corresponds to the row in
	// the extended graph
	public int transformRow(int row)
	{
		return row;
	}

	public int transformCol(int col)
	{
		return col;
	}

	// return extended row
	public int transformRowEGExtender(int rowEGExtender)
	{
		return rowEGExtender;
	}

	public int transformColEGExtender(int colEGExtender)
	{
		return colEGExtender;
	}

	public Vertex transformVertexEGExtender(Vertex<EGExtender> vEGExtender, E eg) throws ExceptionInvalidVertex
	{
		if (vEGExtender == null)
		{
			throw new ExceptionInvalidVertex("Invalid vertex: null");
		}
		Vertex v = eg.getVertex(transformRowEGExtender(vEGExtender.getRow()), transformColEGExtender(vEGExtender
			.getCol()));
		return v;
	}

	public Vertex<EGExtender> transformVertex(Vertex v) throws ExceptionInvalidVertex
	{
		if (v == null)
		{
			throw new ExceptionInvalidVertex("Invalid vertex: null");
		}
		return getEGExtender().getVertex(transformRow(v.getRow()), transformCol(v.getCol()));
	}

	// beginVertex, endVertex: vertices in extended edit graph
	public EditGraphSegment<EGExtender> transformVertexRange(EditGraphSegment range) throws EGInvalidVertexesOfExtensionException
	{
		if (range == null)
		{
			throw new EGInvalidVertexesOfExtensionException("Invalid range: null");
		}
		try
		{
			Vertex<EGExtender> bvEGExtender = getEGExtender().getVertex(transformRow(range.getBeginVertex().getRow()),
				transformCol(range.getBeginVertex().getCol()));
			Vertex<EGExtender> evEGExtender = getEGExtender().getVertex(transformRow(range.getEndVertex().getRow()),
				transformCol(range.getEndVertex().getCol()));
			return new EditGraphSegment<EGExtender>(bvEGExtender, evEGExtender);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new EGInvalidVertexesOfExtensionException(range, "Invalid range to transform.");
		}
	}

	// //beginVertex, endVertex: vertices in extended edit graph
	// public VertexRange
	// transformVertexRangeExtender(VertexRange<EGExtender> rangeEGExtender, E
	// eg) throws EGInvalidVertexException
	// {
	// if (rangeEGExtender==null)
	// {
	// throw new EGInvalidVertexException("Invalid range: null");
	// }
	// Vertex bv =
	// eg.getVertex(transformRowExtender(rangeEGExtender.getBeginVertex().getI()),
	// rangeEGExtender.getBeginVertex().getJ());
	// Vertex ev =
	// eg.getVertex(transformRowExtender(rangeEGExtender.getEndVertex().getI()),
	// rangeEGExtender.getEndVertex().getJ());
	// return new VertexRange(bv, ev);
	// }

}

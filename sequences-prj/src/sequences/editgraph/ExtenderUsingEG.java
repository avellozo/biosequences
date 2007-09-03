package sequences.editgraph;

public class ExtenderUsingEG<E extends EditGraph<E, ? extends ExtenderUsingEG<E, EGExtender>>, EGExtender extends EditGraph<EGExtender, ? extends Extender<EGExtender>>>
		implements Extender<E>
{

	// egExtender: edit graph over which an extension is got. The vertexes of
	// extended edit graph can be diferent of egExtender.
	// egExtended: edit graph over which an extension is done.

	final EGExtender	egExtender;
	final int			extensionPenalty;

	public ExtenderUsingEG(EGExtender egExtender, int extensionPenalty) throws EGInvalidEditGraphException
	{
		if (egExtender == null)
		{
			throw new EGInvalidEditGraphException("Can't create extender over an edit graph without edit graph extender.");
		}
		if (egExtender.getOptimumPathFactory() == null)
		{
			throw new EGInvalidEditGraphException("Edit graph extender without path factory: impossible to create extender.");
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
	public final int getWeightExtended(VertexRange<E> range) throws EGInvalidRangeException
	{
		try
		{
			return getOptimumPathEGExtender(range).getScore() - getExtensionPenalty();
		}
		catch (EGInvalidEditGraphException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	// range: beginVertex and endVertex in extended edit graph
	public final boolean existsExtendedArc(VertexRange<E> range)
	{
		try
		{
			transformVertexRange(range);
		}
		catch (EGInvalidRangeException e)
		{
			// e.printStackTrace();
			return false;
		}
		return !range.getBeginVertex().equals(range.getEndVertex());
	}

	public final ArcExtendedOverEGExtender<E, EGExtender> getExtendedArc(VertexRange<E> range)
			throws EGInvalidRangeException
	{
		if (range == null)
		{
			throw new EGInvalidRangeException("Invalid range: null");
		}
		try
		{
			return new ArcExtendedOverEGExtender<E, EGExtender>(range);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInvalidRangeException(range, "Invalid range");			
		}
		catch (EGInvalidEditGraphException e)
		{
			e.printStackTrace();
			throw new EGInvalidRangeException(range, "Invalid range");			
		}
	}

	// rangeExtended is a range in an extended graph
	public final OptimumPath<EGExtender> getOptimumPathEGExtender(VertexRange<E> rangeExtended)
			throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return getEGExtender().getOptimumPath(transformVertexRange(rangeExtended), false);
	}

	// return weight of the vertical arc on the extender edit graph which
	// corresponds to the row,col on the extended graph
	public final int getEGExtenderWeightVerticalArc(int row, int col) throws EGInvalidArcException
	{
		return getEGExtender().getWeightVerticalArc(transformRow(row), transformCol(col));
	}

	public final int getEGExtenderWeightHorizontalArc(int row, int col) throws EGInvalidArcException
	{
		return getEGExtender().getWeightHorizontalArc(transformRow(row), transformCol(col));
	}

	public final int getEGExtenderWeightDiagonalArc(int row, int col) throws EGInvalidArcException
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

	public Vertex<E> transformVertexEGExtender(Vertex<EGExtender> vEGExtender, E eg) throws EGInvalidVertexException
	{
		if (vEGExtender == null)
		{
			throw new EGInvalidVertexException("Invalid vertex: null");
		}
		Vertex<E> v = eg.getVertex(transformRowEGExtender(vEGExtender.getI()), transformColEGExtender(vEGExtender
			.getJ()));
		return v;
	}

	public Vertex<EGExtender> transformVertex(Vertex<E> v) throws EGInvalidVertexException
	{
		if (v == null)
		{
			throw new EGInvalidVertexException("Invalid vertex: null");
		}
		return getEGExtender().getVertex(transformRow(v.getI()), transformCol(v.getJ()));
	}

	// beginVertex, endVertex: vertices in extended edit graph
	public VertexRange<EGExtender> transformVertexRange(VertexRange<E> range) throws EGInvalidRangeException
	{
		if (range == null)
		{
			throw new EGInvalidRangeException("Invalid range: null");
		}
		try
		{
			Vertex<EGExtender> bvEGExtender = getEGExtender().getVertex(transformRow(range.getBeginVertex().getI()),
				transformCol(range.getBeginVertex().getJ()));
			Vertex<EGExtender> evEGExtender = getEGExtender().getVertex(transformRow(range.getEndVertex().getI()),
				transformCol(range.getEndVertex().getJ()));
			return new VertexRange<EGExtender>(bvEGExtender, evEGExtender);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInvalidRangeException(range, "Invalid range to transform.");
		}
	}

	// //beginVertex, endVertex: vertices in extended edit graph
	// public VertexRange<E>
	// transformVertexRangeExtender(VertexRange<EGExtender> rangeEGExtender, E
	// eg) throws EGInvalidVertexException
	// {
	// if (rangeEGExtender==null)
	// {
	// throw new EGInvalidVertexException("Invalid range: null");
	// }
	// Vertex<E> bv =
	// eg.getVertex(transformRowExtender(rangeEGExtender.getBeginVertex().getI()),
	// rangeEGExtender.getBeginVertex().getJ());
	// Vertex<E> ev =
	// eg.getVertex(transformRowExtender(rangeEGExtender.getEndVertex().getI()),
	// rangeEGExtender.getEndVertex().getJ());
	// return new VertexRange<E>(bv, ev);
	// }

}

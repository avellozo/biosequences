package sequences.editgraph;

public class ArcExtendedOverEGExtender<E extends EditGraph<E, ? extends ExtenderUsingEG<E, EGExtender>>, EGExtender extends EditGraph<EGExtender, ? extends Extender<EGExtender>>>
		extends ArcExtended<E>
{

	OptimumPath<EGExtender>	pathEGExtender;

	public ArcExtendedOverEGExtender(VertexRange<E> range) throws EGInvalidRangeException, EGInvalidVertexException, EGInvalidEditGraphException
	{
		super(range, 0);
		if (getEGExtender() == null)
		{
			throw new EGInvalidEditGraphException("Can't create a object ArcExtendedOverEGExtender without edit graph of extender");
		}
		if (getEGExtender().getOptimumPathFactory() == null)
		{
			throw new EGInvalidEditGraphException("Can't create a object ArcExtendedOverEGExtender without optimum path factory of edit graph of extender");
		}
	}

	public OptimumPath<EGExtender> getPathEGExtender()
	{
		if (pathEGExtender == null)
		{
			try
			{
				pathEGExtender = getEditGraph().getExtender().getOptimumPathEGExtender(getRange());
			}
			catch (EGInvalidRangeException e)
			{
				e.printStackTrace();
				throw new EGInternalException();
			}
			catch (EGInvalidEditGraphException e)
			{
				e.printStackTrace();
				throw new EGInternalException();
			}
		}
		return pathEGExtender;
	}

	public EGExtender getEGExtender()
	{
		return getEditGraph().getExtender().getEGExtender();
	}

	@Override
	public int getWeight()
	{
		return getPathEGExtender().getScore() - getEditGraph().getExtender().getExtensionPenalty();
	}

	public Vertex<E> transformVertexEGExtender(Vertex<EGExtender> vEGExtender) throws EGInvalidVertexException
	{
		return getEditGraph().getExtender().transformVertexEGExtender(vEGExtender, getEditGraph());
	}

	public Vertex<EGExtender> transformVertex(Vertex<E> v) throws EGInvalidVertexException
	{
		return getEditGraph().getExtender().transformVertex(v);
	}

}

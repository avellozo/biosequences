package sequences.editgraph;

public class ArcExtendedOverEGExtender<E extends EditGraph<E, ? extends ExtenderUsingEG<E, EGExtender>>, EGExtender extends EditGraph<EGExtender, ? extends Extender<EGExtender>>>
		extends ArcExtended
{

	OptimumPath<EGExtender>	pathEGExtender;

	public ArcExtendedOverEGExtender(EditGraphSegment range) throws EGInvalidVertexesOfExtensionException, ExceptionInvalidVertex, ExceptionInvalidEditGraph
	{
		super(range, 0);
		if (getEGExtender() == null)
		{
			throw new ExceptionInvalidEditGraph("Can't create a object ArcExtendedOverEGExtender without edit graph of extender");
		}
		if (getEGExtender().getOptimumPathFactory() == null)
		{
			throw new ExceptionInvalidEditGraph("Can't create a object ArcExtendedOverEGExtender without optimum path factory of edit graph of extender");
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
			catch (EGInvalidVertexesOfExtensionException e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
			catch (ExceptionInvalidEditGraph e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
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

	public Vertex transformVertexEGExtender(Vertex<EGExtender> vEGExtender) throws ExceptionInvalidVertex
	{
		return getEditGraph().getExtender().transformVertexEGExtender(vEGExtender, getEditGraph());
	}

	public Vertex<EGExtender> transformVertex(Vertex v) throws ExceptionInvalidVertex
	{
		return getEditGraph().getExtender().transformVertex(v);
	}

}

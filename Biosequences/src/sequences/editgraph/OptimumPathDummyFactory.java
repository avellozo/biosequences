package sequences.editgraph;

public class OptimumPathDummyFactory<E extends EditGraph<E, ? extends Extender>>
		implements OptimumPathFactory<E, OptimumPathDummy>
{

	public OptimumPathDummy createPath(EditGraphSegment range, boolean local)
			throws EGInvalidVertexesOfExtensionException
	{
		return new OptimumPathDummy(range, local);
	}

	public String getName()
	{
		return "Dummy";
	}

}

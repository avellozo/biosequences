package sequences.editgraph;

public class OptimumPathSimpleFactory<E extends EditGraph<E, ? extends Extender>>
		implements OptimumPathFactory<E, OptimumPathSimple>
{

	public OptimumPathSimple createPath(EditGraphSegment range, boolean local)
			throws EGInvalidVertexesOfExtensionException
	{
		return new OptimumPathSimple(range, local);
	}

	public String getName()
	{
		return "SmithWaterman without gapopen";
	}

}

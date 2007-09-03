package sequences.editgraph;

public class OptimumPathSimpleFactory<E extends EditGraph<E, ? extends Extender<E>>>
		implements OptimumPathFactory<E, OptimumPathSimple<E>>
{

	public OptimumPathSimple<E> createPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException
	{
		return new OptimumPathSimple<E>(range, local);
	}

	public String getName()
	{
		return "SmithWaterman without gapopen";
	}

}

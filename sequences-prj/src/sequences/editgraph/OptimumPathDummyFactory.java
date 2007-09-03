package sequences.editgraph;

public class OptimumPathDummyFactory<E extends EditGraph<E, ? extends Extender<E>>>
		implements OptimumPathFactory<E, OptimumPathDummy<E>>
{

	public OptimumPathDummy<E> createPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException
	{
		return new OptimumPathDummy<E>(range, local);
	}

	public String getName()
	{
		return "Dummy";
	}

}

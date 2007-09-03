package sequences.editgraph;

public interface OptimumPathFactory<E extends EditGraph<E, ? extends Extender<E>>, P extends OptimumPath<E>>
{
	public P createPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException, EGInvalidEditGraphException;
	
	public String getName();
}

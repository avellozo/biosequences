package sequences.editgraph;

public interface OptimumPathFactory<E extends EditGraph<E, ? extends Extender>, P extends OptimumPath>
{
	public P createPath(EditGraphSegment range, boolean local)
			throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph;
	
	public String getName();
}

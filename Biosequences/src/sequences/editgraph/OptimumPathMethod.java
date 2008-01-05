package sequences.editgraph;

public interface OptimumPathMethod
{
	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph;

	public boolean isLocal();

	public String getName();

}

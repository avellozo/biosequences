package sequences.editgraph;

public interface OptimumPathMethod
{
	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph;

	public String getName();

}

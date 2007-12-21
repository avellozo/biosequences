package sequences.editgraph;

public class ArcExtendedOverEG extends ArcExtended
{

	OptimumPath	optimumPath;

	public ArcExtendedOverEG(VertexRange range, OptimumPath optimumPath, int ExtensionPenalty)
			throws ExceptionInvalidVertex
	{
		super(range, optimumPath.getScore() + ExtensionPenalty);
		this.optimumPath = optimumPath;
	}

	public OptimumPath getOptimumPath()
	{
		return optimumPath;
	}

}

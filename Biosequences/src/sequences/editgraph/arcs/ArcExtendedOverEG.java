package sequences.editgraph.arcs;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

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

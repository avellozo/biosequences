package sequences.editgraph.arcs;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedOverEG extends ArcExtended
{

	OptimumPath	optimumPath;
	int			extensionPenalty;

	//	public ArcExtendedOverEG(VertexRange range, List<OptimumPath> optimumPaths, int extensionPenalty)
	//			throws ExceptionInvalidVertex
	//	{
	//		super(range, extensionPenalty);
	//		this.extensionPenalty = extensionPenalty;
	//		this.optimumPaths = optimumPaths;
	//		for (OptimumPath path : optimumPaths)
	//		{
	//			weight += path.getScore();
	//		}
	//	}
	//
	public ArcExtendedOverEG(VertexRange range, OptimumPath optimumPath, int extensionPenalty)
			throws ExceptionInvalidVertex
	{
		super(range, extensionPenalty);
		this.extensionPenalty = extensionPenalty;
		this.optimumPath = optimumPath;
		weight += optimumPath.getScore();
	}

	public OptimumPath getOptimumPath()
	{
		return optimumPath;
	}

	public int getExtensionPenalty()
	{
		return extensionPenalty;
	}

}

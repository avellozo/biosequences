package sequences.editgraph.arcs;

import java.util.ArrayList;
import java.util.List;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedOverEG extends ArcExtended
{

	List<OptimumPath>	optimumPaths;
	int					extensionPenalty;

	public ArcExtendedOverEG(VertexRange range, List<OptimumPath> optimumPaths, int extensionPenalty)
			throws ExceptionInvalidVertex
	{
		super(range, extensionPenalty);
		this.extensionPenalty = extensionPenalty;
		this.optimumPaths = optimumPaths;
		for (OptimumPath path : optimumPaths)
		{
			weight += path.getScore();
		}
	}

	public ArcExtendedOverEG(VertexRange range, OptimumPath optimumPath, int extensionPenalty)
			throws ExceptionInvalidVertex
	{
		super(range, extensionPenalty);
		this.extensionPenalty = extensionPenalty;
		this.optimumPaths = new ArrayList<OptimumPath>(1);
		optimumPaths.add(optimumPath);
		weight += optimumPath.getScore();
	}

	public List<OptimumPath> getOptimumPaths()
	{
		return optimumPaths;
	}

	public int getExtensionPenalty()
	{
		return extensionPenalty;
	}

}

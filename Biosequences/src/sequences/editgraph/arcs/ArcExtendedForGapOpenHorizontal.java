/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedForGapOpenHorizontal extends ArcExtendedOverEG
{

	public ArcExtendedForGapOpenHorizontal(VertexRange vertexRange, OptimumPath path, int gapOpenPenalty)
			throws ExceptionInvalidVertex
	{
		super(vertexRange, path, gapOpenPenalty);
		if (vertexRange.getRowsQtty() != 1)
		{
			throw new ExceptionInvalidVertex(vertexRange.toString());
		}
	}

}

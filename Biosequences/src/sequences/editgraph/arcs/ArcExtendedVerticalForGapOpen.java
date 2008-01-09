/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs;

import sequences.editgraph.ArcVerticalFactory;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedVerticalForGapOpen extends ArcExtended
{

	ArcVerticalFactory	arcVFactory;

	public ArcExtendedVerticalForGapOpen(VertexRange vertexRange, int weight, ArcVerticalFactory arcVFactory)
			throws ExceptionInvalidVertex
	{
		super(vertexRange, weight);
		this.arcVFactory = arcVFactory;
		if (vertexRange.getColsQtty() != 1)
		{
			throw new ExceptionInvalidVertex(vertexRange.toString());
		}
	}

	public ArcVerticalFactory getArcVerticalFactory()
	{
		return arcVFactory;
	}

}

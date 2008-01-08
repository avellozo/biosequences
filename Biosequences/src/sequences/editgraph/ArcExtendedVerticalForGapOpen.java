/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

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

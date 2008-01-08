/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

public class ArcExtendedHorizontalForGapOpen extends ArcExtended
{

	ArcHorizontalFactory	arcHFactory;

	public ArcExtendedHorizontalForGapOpen(VertexRange vertexRange, int weight, ArcHorizontalFactory arcHFactory)
			throws ExceptionInvalidVertex
	{
		super(vertexRange, weight);
		this.arcHFactory = arcHFactory;
		if (vertexRange.getRowsQtty() != 1)
		{
			throw new ExceptionInvalidVertex(vertexRange.toString());
		}
	}

	public ArcHorizontalFactory getArcHorizontalFactory()
	{
		return arcHFactory;
	}

}

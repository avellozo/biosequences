/*
 * Created on 08/01/2008
 */
package sequences.editgraph;

public class ArcExtendedFactoryNothing implements ArcExtendedFactory
{

	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		return false;
	}

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(vertexRange.toString());
	}

}

/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

public interface ArcFactoryExtended
{

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex;

	public boolean canCreateExtendedArc(VertexRange vertexRange);

}

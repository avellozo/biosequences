/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

public interface ArcExtendedFactory
{

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;

}

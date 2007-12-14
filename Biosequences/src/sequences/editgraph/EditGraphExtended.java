/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

public interface EditGraphExtended extends EditGraph
{

	public boolean existsExtendedArc(Vertex beginVertex, Vertex endVertex);

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;

}

/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

public interface EditGraphExtended extends EditGraph
{

	public boolean existsExtendedArc(int rowBeginVertex, int colBeginVertex, int rowEndVertex, int colEndVertex)
			throws ExceptionInvalidVertex;

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;

}

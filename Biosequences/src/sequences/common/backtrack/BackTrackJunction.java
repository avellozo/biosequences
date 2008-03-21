/*
 * Created on 21/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcJuntion;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface BackTrackJunction
{

	public ArcJuntion getArcJuntion(Vertex endVertex) throws ExceptionInvalidVertex;

	public void setJunction(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
			throws ExceptionInvalidVertex;

}
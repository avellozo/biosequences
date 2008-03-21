/*
 * Created on 21/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.EditGraphExtended;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface BackTrackExtension
{

	public ArcExtended getExtendedArc(Vertex endVertex, EditGraphExtended eg) throws ExceptionInvalidVertex;

	public void setExtension(int rowBegin, int colBegin, int rowEnd, int colEnd, int value);

}
/*
 * Created on 19/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.EditGraphWithGapSet;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface BackTrackGapSet
{
	public ArcGapSetHorizontal getArcGapHor(Vertex endVertex, EditGraphWithGapSet eg) throws ExceptionInvalidVertex;

	public ArcGapSetVertical getArcGapVer(Vertex endVertex, EditGraphWithGapSet eg) throws ExceptionInvalidVertex;

	public void setGapHor(int colBegin, int rowEnd, int colEnd, int value);

	public void setGapVer(int rowBegin, int rowEnd, int col, int value);

}

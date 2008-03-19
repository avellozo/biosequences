/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface ArcGapSetFactory
{

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex;

	public boolean canCreateGapSetHorizontalArc(int beginCol, Vertex endVertex);

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex;

	public boolean canCreateGapSetVerticalArc(int beginRow, Vertex endVertex);

}

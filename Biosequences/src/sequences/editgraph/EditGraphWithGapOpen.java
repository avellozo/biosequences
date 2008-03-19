/*
 * Created on 19/03/2008
 */
package sequences.editgraph;

import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface EditGraphWithGapOpen extends EditGraph
{
	public boolean existsGapSetVerticalArc(int beginRow, Vertex endVertex);

	public boolean existsGapSetHorizontalArc(int beginCol, Vertex endVertex);

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex;

}

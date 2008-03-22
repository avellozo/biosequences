/*
 * Created on 09/10/2007
 */
package sequences.editgraph.arcs.factories;

import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface GapSetFactory
{

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex;

	public boolean canCreateGapSetHorizontalArc(int beginCol, Vertex endVertex);

	public boolean canCreateGapSetHorizontalArc(int beginCol, int row, int endCol);

	public int getWeightGapSetHorizontalArc(int beginCol, int row, int endCol) throws ExceptionInvalidVertex;

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex;

	public boolean canCreateGapSetVerticalArc(int beginRow, Vertex endVertex);

	public boolean canCreateGapSetVerticalArc(int beginRow, int endRow, int col);

	public int getWeightGapSetVerticalArc(int beginRow, int endRow, int col) throws ExceptionInvalidVertex;

}

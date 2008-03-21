/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs;

import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcGapSetHorizontal extends ArcExtended
{

	//	public ArcExtendedForGapOpenHorizontal(VertexRange vertexRange, OptimumPath path, int gapSetPenalty)
	//			throws ExceptionInvalidVertex
	//	{
	//		super(vertexRange, path, gapSetPenalty);
	//		if (vertexRange.getRowsQtty() != 1)
	//		{
	//			throw new ExceptionInvalidVertex(vertexRange.toString());
	//		}
	//	}
	//
	public ArcGapSetHorizontal(int beginCol, Vertex endVertex, int gapWeight) throws ExceptionInvalidVertex
	{
		super(new VertexRange(new Vertex(endVertex.getRow(), beginCol), endVertex), gapWeight);
	}

	//	public ArcExtendedForGapOpenHorizontal(VertexRange vertexRange, EditGraph eg, int gapSetPenalty)
	//			throws ExceptionInvalidVertex
	//	{
	//		super(vertexRange, null, gapSetPenalty);
	//		if (vertexRange.getRowsQtty() != 1)
	//		{
	//			throw new ExceptionInvalidVertex(vertexRange.toString());
	//		}
	//		this.optimumPath = new OptimumPathImpl(eg, "Simple horizontal arcs for gap open.");
	//		int beginCol = vertexRange.getBeginVertex().getCol();
	//		int row = vertexRange.getBeginVertex().getRow();
	//		int endCol = vertexRange.getEndVertex().getCol();
	//		for (int j = beginCol + 1; j <= endCol; j++)
	//		{
	//			optimumPath.add(eg.getHorizontalArc(eg.getVertex(row, j)));
	//		}
	//	}
	//
}

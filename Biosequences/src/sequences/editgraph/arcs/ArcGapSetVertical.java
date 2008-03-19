/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcGapSetVertical extends ArcExtendedOverEG
{

	//	public ArcExtendedForGapOpenVertical(VertexRange vertexRange, OptimumPath path, int gapOpenPenalty)
	//			throws ExceptionInvalidVertex
	//	{
	//		super(vertexRange, path, gapOpenPenalty);
	//		if (vertexRange.getColsQtty() != 1)
	//		{
	//			throw new ExceptionInvalidVertex(vertexRange.toString());
	//		}
	//	}
	//
	public ArcGapSetVertical(int beginRow, Vertex endVertex, OptimumPath path, int gapOpenPenalty)
			throws ExceptionInvalidVertex
	{
		super(new VertexRange(new Vertex(beginRow, endVertex.getCol()), endVertex), path, gapOpenPenalty);
	}

	//	public ArcExtendedForGapOpenVertical(VertexRange vertexRange, EditGraph eg, int gapOpenPenalty)
	//			throws ExceptionInvalidVertex
	//	{
	//		super(vertexRange, null, gapOpenPenalty);
	//		if (vertexRange.getColsQtty() != 1)
	//		{
	//			throw new ExceptionInvalidVertex(vertexRange.toString());
	//		}
	//		this.optimumPath = new OptimumPathImpl(eg, "Simple vertical arcs for gap open.");
	//		int beginRow = vertexRange.getBeginVertex().getRow();
	//		int col = vertexRange.getBeginVertex().getCol();
	//		int endRow = vertexRange.getEndVertex().getRow();
	//		for (int i = beginRow + 1; i <= endRow; i++)
	//		{
	//			optimumPath.add(eg.getVerticalArc(eg.getVertex(i, col)));
	//		}
	//	}

}

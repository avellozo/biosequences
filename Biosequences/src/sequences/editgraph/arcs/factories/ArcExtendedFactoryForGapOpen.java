/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import sequences.editgraph.ArcExtendedFactory;
import sequences.editgraph.ArcHorizontalFactory;
import sequences.editgraph.ArcVerticalFactory;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.ArcExtendedHorizontalForGapOpen;
import sequences.editgraph.arcs.ArcExtendedVerticalForGapOpen;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedFactoryForGapOpen implements ArcExtendedFactory
{

	int						gapOpenPenalty;
	ArcHorizontalFactory	arcHFactory;
	ArcVerticalFactory		arcVFactory;

	public ArcExtendedFactoryForGapOpen(int gapOpenPenalty, ArcHorizontalFactory arcHFactory,
			ArcVerticalFactory arcVFactory)
	{
		this.gapOpenPenalty = gapOpenPenalty;
		this.arcHFactory = arcHFactory;
		this.arcVFactory = arcVFactory;
	}

	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		return (((cols == 1) || (rows == 1)) && (cols != rows));
	}

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (!canCreateExtendedArc(vertexRange))
		{
			throw new ExceptionInvalidVertex("Can't create an extended arc from " + vertexRange.getBeginVertex()
				+ " to " + vertexRange.getEndVertex() + ".");
		}
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		if (rows == 1)
		{
			int row = vertexRange.getBeginVertex().getRow();
			int beginCol = vertexRange.getBeginVertex().getCol();
			int endCol = vertexRange.getEndVertex().getCol();
			int w = 0;
			for (int j = beginCol + 1; j <= endCol; j++)
			{
				w += arcHFactory.getWeightHorizontalArc(row, j);
			}
			return new ArcExtendedHorizontalForGapOpen(vertexRange, gapOpenPenalty + w, getArcHorizontalFactory());
		}
		else
		{
			int col = vertexRange.getBeginVertex().getCol();
			int beginRow = vertexRange.getBeginVertex().getRow();
			int endRow = vertexRange.getEndVertex().getRow();
			int w = 0;
			for (int i = beginRow + 1; i <= endRow; i++)
			{
				w += arcVFactory.getWeightVerticalArc(i, col);
			}
			return new ArcExtendedVerticalForGapOpen(vertexRange, gapOpenPenalty + w, getArcVerticalFactory());
		}
	}

	public int getGapOpenPenalty()
	{
		return gapOpenPenalty;
	}

	public ArcHorizontalFactory getArcHorizontalFactory()
	{
		return arcHFactory;
	}

	public ArcVerticalFactory getArcVerticalFactory()
	{
		return arcVFactory;
	}

}

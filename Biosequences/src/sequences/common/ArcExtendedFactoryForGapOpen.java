/*
 * Created on 07/01/2008
 */
package sequences.common;

import sequences.editgraph.ArcExtendedFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.ArcExtendedForGapOpenHorizontal;
import sequences.editgraph.arcs.ArcExtendedForGapOpenVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedFactoryForGapOpen implements ArcExtendedFactory
{

	int			gapOpenPenalty;
	EditGraph	eg;			// grafo de edição com os valores das arestas verticais e horizontais

	public ArcExtendedFactoryForGapOpen(int gapOpenPenalty, EditGraph eg)
	{
		this.gapOpenPenalty = gapOpenPenalty;
		this.eg = eg;
	}

	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		if ((cols == 1) && (rows != 1))
		{
			int col = vertexRange.getBeginVertex().getCol();
			int beginRow = vertexRange.getBeginVertex().getRow();
			int endRow = vertexRange.getEndVertex().getRow();
			for (int i = beginRow + 1; i <= endRow; i++)
			{
				try
				{
					if (!eg.existsVerticalArc(eg.getVertex(i, col)))
					{
						return false;
					}
				}
				catch (ExceptionInvalidVertex e)
				{
					return false;
				}
			}
			return true;
		}
		else if ((cols != 1) && (rows == 1))
		{
			int row = vertexRange.getBeginVertex().getRow();
			int beginCol = vertexRange.getBeginVertex().getCol();
			int endCol = vertexRange.getEndVertex().getCol();
			for (int j = beginCol + 1; j <= endCol; j++)
			{
				try
				{
					if (!eg.existsHorizontalArc(eg.getVertex(row, j)))
					{
						return false;
					}
				}
				catch (ExceptionInvalidVertex e)
				{
					return false;
				}
			}
			return true;
		}
		return false;
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
			OptimumPath path = new OptimumPathImpl(eg, "Simple for get horizontal arcs for gap extension.");
			for (int j = beginCol + 1; j <= endCol; j++)
			{
				path.add(eg.getHorizontalArc(eg.getVertex(row, j)));
			}
			return new ArcExtendedForGapOpenHorizontal(vertexRange, path, gapOpenPenalty);
		}
		else
		{
			int col = vertexRange.getBeginVertex().getCol();
			int beginRow = vertexRange.getBeginVertex().getRow();
			int endRow = vertexRange.getEndVertex().getRow();
			OptimumPath path = new OptimumPathImpl(eg, "Simple for get vertical arcs for gap extension.");
			for (int i = beginRow + 1; i <= endRow; i++)
			{
				path.add(eg.getVerticalArc(eg.getVertex(i, col)));
			}
			return new ArcExtendedForGapOpenVertical(vertexRange, path, gapOpenPenalty);
		}
	}

	public int getGapOpenPenalty()
	{
		return gapOpenPenalty;
	}

	public EditGraph getEditGraph()
	{
		return eg;
	}

}

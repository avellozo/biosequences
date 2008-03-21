/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class GapConstantFactory implements GapFactory
{
	int	gapOpenPenalty;
	int	gap;

	public GapConstantFactory(int gapExtension, int gapOpenPenalty)
	{
		this.gapOpenPenalty = gapOpenPenalty;
		this.gap = gapExtension;
	}

	public int getGapOpenPenalty()
	{
		return gapOpenPenalty;
	}

	public int getGapExtension()
	{
		return gap;
	}

	public boolean canCreateGapSetHorizontalArc(int beginCol, Vertex endVertex)
	{
		return (endVertex != null && canCreateGapSetHorizontalArc(beginCol, endVertex.getRow(), endVertex.getCol()));
	}

	public boolean canCreateGapSetVerticalArc(int beginRow, Vertex endVertex)
	{
		return (endVertex != null && canCreateGapSetVerticalArc(beginRow, endVertex.getRow(), endVertex.getCol()));
	}

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (endVertex == null)
		{
			throw new ExceptionInvalidVertex("Can't create an gap set horizontal arc from column" + beginCol + " to "
				+ endVertex + ".");
		}
		return new ArcGapSetHorizontal(beginCol, endVertex, getWeightGapSetHorizontalArc(beginCol, endVertex.getRow(),
			endVertex.getCol()));
	}

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (endVertex == null)
		{
			throw new ExceptionInvalidVertex("Can't create an gap set vertical arc from row" + beginRow + " to "
				+ endVertex + ".");
		}
		return new ArcGapSetVertical(beginRow, endVertex, getWeightGapSetVerticalArc(beginRow, endVertex.getRow(),
			endVertex.getCol()));
	}

	public boolean canCreateGapSetHorizontalArc(int beginCol, int row, int endCol)
	{
		return beginCol < endCol;
	}

	public boolean canCreateGapSetVerticalArc(int beginRow, int endRow, int col)
	{
		return beginRow < endRow;
	}

	public int getWeightGapSetHorizontalArc(int beginCol, int row, int endCol) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetHorizontalArc(beginCol, row, endCol))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set horizontal arc from column" + beginCol + " to "
				+ endCol + " on row " + row + ".");
		}
		return gapOpenPenalty + (gap * (endCol - beginCol));
	}

	public int getWeightGapSetVerticalArc(int beginRow, int endRow, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetVerticalArc(beginRow, endRow, col))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set vertical arc from row" + beginRow + " to "
				+ endRow + " on col " + col + ".");
		}
		return gapOpenPenalty + (gap * (endRow - beginRow));
	}

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		return endVertex != null;
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		return true;
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return new ArcHorizontal(endVertex, getWeightHorizontalArc(endVertex.getRow(), endVertex.getCol()));
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		int i, j;
		LinkedList<ArcHorizontal> list = new LinkedList<ArcHorizontal>();
		if (gap + gapOpenPenalty != 0)
		{
			try
			{
				for (i = eg.getRowMin(); i <= eg.getRowMax(); i++)
				{
					for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
					{
						list.addLast(getHorizontalArc(eg.getVertex(i, j)));
					}
				}
			}
			catch (ExceptionInvalidVertex e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
		}
		return list;
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateHorizontalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		return gap + gapOpenPenalty;
	}

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		return endVertex != null;
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		return true;
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		return new ArcVertical(endVertex, getWeightVerticalArc(endVertex.getRow(), endVertex.getCol()));
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		int i, j;
		LinkedList<ArcVertical> list = new LinkedList<ArcVertical>();
		if (gap + gapOpenPenalty != 0)
		{
			try
			{
				for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
				{
					for (j = eg.getColMin(); j <= eg.getColMax(); j++)
					{
						list.addLast(getVerticalArc(eg.getVertex(i, j)));
					}
				}
			}
			catch (ExceptionInvalidVertex e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
		}
		return list;
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateVerticalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		return gap + gapOpenPenalty;
	}

}

/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import sequences.editgraph.ArcGapSetFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcGapOpenFactory implements ArcGapSetFactory
{

	int			gapOpenPenalty;
	EditGraph	eg;			// grafo de edição com os valores das arestas verticais e horizontais

	public ArcGapOpenFactory(int gapOpenPenalty, EditGraph eg)
	{
		this.gapOpenPenalty = gapOpenPenalty;
		this.eg = eg;
	}

	public int getGapOpenPenalty()
	{
		return gapOpenPenalty;
	}

	public EditGraph getEditGraph()
	{
		return eg;
	}

	public boolean canCreateGapSetHorizontalArc(int beginCol, Vertex endVertex)
	{
		int row = endVertex.getRow();
		int endCol = endVertex.getCol();
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
		return beginCol < endCol;
	}

	public boolean canCreateGapSetVerticalArc(int beginRow, Vertex endVertex)
	{
		int col = endVertex.getCol();
		int endRow = endVertex.getRow();
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
		return beginRow < endRow;
	}

	public ArcGapSetHorizontal getGapSetHorizontalArc(int beginCol, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetHorizontalArc(beginCol, endVertex))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set horizontal arc from column" + beginCol + " to "
				+ endVertex + ".");
		}
		int row = endVertex.getRow();
		int endCol = endVertex.getCol();
		OptimumPath path = new OptimumPathImpl(eg, "Simple horizontal arcs for gap open.");
		for (int j = beginCol + 1; j <= endCol; j++)
		{
			path.add(eg.getHorizontalArc(eg.getVertex(row, j)));
		}
		return new ArcGapSetHorizontal(beginCol, endVertex, path, gapOpenPenalty);
	}

	public ArcGapSetVertical getGapSetVerticalArc(int beginRow, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetVerticalArc(beginRow, endVertex))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set vertical arc from row" + beginRow + " to "
				+ endVertex + ".");
		}
		int col = endVertex.getCol();
		int endRow = endVertex.getRow();
		OptimumPath path = new OptimumPathImpl(eg, "Simple vertical arcs for gap open.");
		for (int i = beginRow + 1; i <= endRow; i++)
		{
			path.add(eg.getVerticalArc(eg.getVertex(i, col)));
		}
		return new ArcGapSetVertical(beginRow, endVertex, path, gapOpenPenalty);
	}

}

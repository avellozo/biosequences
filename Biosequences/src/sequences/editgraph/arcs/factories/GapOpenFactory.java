/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class GapOpenFactory implements GapFactory
{

	int						gapOpenPenalty;
	ArcHorizontalFactory	arcHorizontalFactory;
	ArcVerticalFactory		arcVerticalFactory;

	public GapOpenFactory(int gapOpenPenalty, ArcHorizontalFactory arcHorizontalFactory,
			ArcVerticalFactory arcVerticalFactory)
	{
		this.gapOpenPenalty = gapOpenPenalty;
		this.arcHorizontalFactory = arcHorizontalFactory;
		this.arcVerticalFactory = arcVerticalFactory;
	}

	public int getGapOpenPenalty()
	{
		return gapOpenPenalty;
	}

	public ArcHorizontalFactory getArcHorizontalFactory()
	{
		return arcHorizontalFactory;
	}

	public ArcVerticalFactory getArcVerticalFactory()
	{
		return arcVerticalFactory;
	}

	public boolean canCreateGapSetHorizontalArc(int beginCol, Vertex endVertex)
	{
		return (endVertex != null && canCreateGapSetHorizontalArc(beginCol, endVertex.getRow(), endVertex.getCol()));
	}

	public boolean canCreateGapSetVerticalArc(int beginRow, Vertex endVertex)
	{
		return (endVertex != null && canCreateGapSetVerticalArc(beginRow, endVertex.getRow(), endVertex.getCol()));
	}

	public boolean canCreateGapSetHorizontalArc(int beginCol, int row, int endCol)
	{
		if (arcHorizontalFactory == null)
		{
			return false;
		}
		for (int j = beginCol + 1; j <= endCol; j++)
		{
			if (!arcHorizontalFactory.canCreateHorizontalArc(row, j))
			{
				return false;
			}
		}
		return beginCol < endCol;
	}

	public boolean canCreateGapSetVerticalArc(int beginRow, int endRow, int col)
	{
		if (arcVerticalFactory == null)
		{
			return false;
		}
		for (int i = beginRow + 1; i <= endRow; i++)
		{
			if (!arcVerticalFactory.canCreateVerticalArc(i, col))
			{
				return false;
			}
		}
		return beginRow < endRow;
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

	public int getWeightGapSetHorizontalArc(int beginCol, int row, int endCol) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetHorizontalArc(beginCol, row, endCol))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set horizontal arc from column" + beginCol + " to "
				+ endCol + " on row " + row + ".");
		}
		int weight = gapOpenPenalty;
		for (int j = beginCol + 1; j <= endCol; j++)
		{
			weight += arcHorizontalFactory.getWeightHorizontalArc(row, j);
		}
		return weight;
	}

	public int getWeightGapSetVerticalArc(int beginRow, int endRow, int col) throws ExceptionInvalidVertex
	{
		if (!canCreateGapSetVerticalArc(beginRow, endRow, col))
		{
			throw new ExceptionInvalidVertex("Can't create an gap set vertical arc from row" + beginRow + " to "
				+ endRow + " on col " + col + ".");
		}
		int weight = gapOpenPenalty;
		for (int i = beginRow + 1; i <= endRow; i++)
		{
			weight += arcVerticalFactory.getWeightVerticalArc(i, col);
		}
		return weight;
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		return arcHorizontalFactory != null && arcHorizontalFactory.canCreateHorizontalArc(i, j);
	}

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		return arcHorizontalFactory != null && arcHorizontalFactory.canCreateHorizontalArc(endVertex);
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		ArcHorizontal arc = arcHorizontalFactory.getHorizontalArc(endVertex);
		arc.setWeight(arc.getWeight() + gapOpenPenalty);
		return arc;
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		List< ? extends ArcHorizontal> l = arcHorizontalFactory.getNonZeroHorizontalArcs(eg);
		for (ArcHorizontal arc : l)
		{
			arc.setWeight(arc.getWeight() + gapOpenPenalty);
		}
		return l;
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcHorizontalFactory.getWeightHorizontalArc(i, j) + gapOpenPenalty;
	}

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		return arcVerticalFactory != null && arcVerticalFactory.canCreateVerticalArc(endVertex);
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		return arcVerticalFactory != null && arcVerticalFactory.canCreateVerticalArc(i, j);
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		List< ? extends ArcVertical> l = arcVerticalFactory.getNonZeroVerticalArcs(eg);
		for (ArcVertical arc : l)
		{
			arc.setWeight(arc.getWeight() + gapOpenPenalty);
		}
		return l;
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		ArcVertical arc = arcVerticalFactory.getVerticalArc(endVertex);
		arc.setWeight(arc.getWeight() + gapOpenPenalty);
		return arc;
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		return arcVerticalFactory.getWeightVerticalArc(i, j) + gapOpenPenalty;
	}

}

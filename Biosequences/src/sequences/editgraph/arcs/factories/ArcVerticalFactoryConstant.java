/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcVerticalFactoryConstant implements ArcVerticalFactory
{
	int	gap;

	public ArcVerticalFactoryConstant(int gap)
	{
		this.gap = gap;
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
		if (gap != 0)
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
		return gap;
	}

}

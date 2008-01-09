/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.ArcHorizontalFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcHorizontalFactoryConstant implements ArcHorizontalFactory
{
	int	gap;

	public ArcHorizontalFactoryConstant(int gap)
	{
		this.gap = gap;
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
		if (gap != 0)
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
		return gap;
	}

}

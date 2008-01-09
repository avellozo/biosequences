/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.ArcVerticalFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcVerticalFactoryNothing implements ArcVerticalFactory
{

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		return false;
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		return false;
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		return new LinkedList<ArcVertical>();
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(endVertex);
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(i, j);
	}

}

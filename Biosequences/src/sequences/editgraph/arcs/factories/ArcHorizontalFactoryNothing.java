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
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcHorizontalFactoryNothing implements ArcHorizontalFactory
{

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		return false;
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		return false;
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		return new LinkedList<ArcHorizontal>();
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(endVertex);
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(i, j);
	}

}

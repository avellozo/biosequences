/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

import java.util.LinkedList;
import java.util.List;

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

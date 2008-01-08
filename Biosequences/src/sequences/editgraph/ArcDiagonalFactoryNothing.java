/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

import java.util.LinkedList;
import java.util.List;

public class ArcDiagonalFactoryNothing implements ArcDiagonalFactory
{

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		return false;
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		return false;
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		return new LinkedList<ArcDiagonal>();
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(endVertex);
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		throw new ExceptionInvalidVertex(i, j);
	}

}

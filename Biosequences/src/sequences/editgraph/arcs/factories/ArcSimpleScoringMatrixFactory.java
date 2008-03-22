/*
 * Created on 22/03/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcSimpleScoringMatrixFactory implements ArcSimpleFactory
{

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean canCreateHorizontalArc(Vertex endVertex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canCreateHorizontalArc(int i, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean canCreateVerticalArc(Vertex endVertex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canCreateVerticalArc(int i, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

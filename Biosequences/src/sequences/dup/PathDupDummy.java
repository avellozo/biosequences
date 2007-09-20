/*
 * Created on 04/10/2005
 */
package sequences.dup;

import java.util.LinkedList;

import sequences.editgraph.EGGeneralException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;

/**
 * @author Augusto F. Vellozo
 */
public class PathDupDummy<E extends EditGraph<E, ? extends ExtenderDup<E>>>
		extends PathDupDP<E>
{

	public PathDupDummy(VertexRange<E> range, boolean local)
			throws EGInvalidVertexException, EGGeneralException
	{
		super(range, local);
	}


	@Override
	protected void buildMatrix()
	{
	}


	protected void buildPath()
	{
	}

	public int[][] getInversionsI()
	{
		return new int[0][0];
	}

	public int[][] getInversionsJ()
	{
		return new int[0][0];
	}

	public MatrixInt getMatrixDup()
	{
		return new MatrixIntPrimitive(0, 0);
	}

}

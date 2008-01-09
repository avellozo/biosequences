/*
 * Created on 04/10/2005
 */
package sequences.dup;

import java.util.LinkedList;

import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionGeneralEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;

/**
 * @author Augusto F. Vellozo
 */
public class PathDupDummy<E extends EditGraph<E, ? extends ExtenderDup>>
		extends PathDupDP
{

	public PathDupDummy(EditGraphSegment range, boolean local)
			throws ExceptionInvalidVertex, ExceptionGeneralEG
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

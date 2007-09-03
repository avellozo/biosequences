/*
 * Created on 04/10/2005
 */
package sequences.bim;

import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;

/**
 * @author Augusto F. Vellozo
 */
public class PathBimDummy<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		extends PathBimDP<E>
{

	public PathBimDummy(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException, EGInvalidEditGraphException
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

	public MatrixInt getMatrixBim()
	{
		return new MatrixIntPrimitive(0, 0);
	}

}

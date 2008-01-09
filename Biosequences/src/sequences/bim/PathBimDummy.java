/*
 * Created on 04/10/2005
 */
package sequences.bim;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;

/**
 * @author Augusto F. Vellozo
 */
public class PathBimDummy<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		extends PathBimDP
{

	public PathBimDummy(EditGraphSegment range, boolean local)
			throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
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

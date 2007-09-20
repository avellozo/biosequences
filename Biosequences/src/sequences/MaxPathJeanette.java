/*
 * Criado em 02/09/2004
 */
package sequences;

import java.io.PrintStream;


import sequences.bim.n3lgn.TreeJeanette;
import sequences.editgraph.EditGraphOld;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntTreeJeanette;

/**
 * @author Augusto
 * @data 02/09/2004
 */
public class MaxPathJeanette extends MaxPath
{
	public MaxPathJeanette(EditGraphOld editGraph, PrintStream out)
	{
		super (editGraph, out);
	}
	
	public MaxPathJeanette(EditGraphOld editGraph)
	{
		super (editGraph);
	}

	protected MatrixInt buildMatrix(PrintStream out)
	{
		int rowsQtty = editGraph.getRowsQtty();
		int colsQtty = editGraph.getColsQtty();
		TreeJeanette[]	trees = new TreeJeanette[colsQtty];
		MatrixIntTreeJeanette ret = new MatrixIntTreeJeanette(trees);
		TreeJeanette treeUpLeft = null, treeUp = null, treeLeft = null;
		for (int i = 0; i < rowsQtty; i++)
		{
			treeLeft = null;
			treeUpLeft = null;
			for (int j = 0; j < colsQtty; j++)
			{
				treeUp = trees[j];
				trees[j] = new TreeJeanette (treeLeft, treeUpLeft, treeUp, editGraph.getWeightHorizontal(i, j), editGraph.getWeightDiagonal(i, j), editGraph.getWeightVertical(i, j));
				treeLeft = trees[j];
				treeUpLeft = treeUp;
			}
			if (out != null)
			{
				ret.print(out, i);
			}
		}
		return ret;
	}

}

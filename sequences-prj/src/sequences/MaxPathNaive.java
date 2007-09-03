/*
 * Criado em 02/09/2004
 */
package sequences;

import java.io.PrintStream;


import sequences.editgraph.EditGraphOld;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;

/**
 * @author Augusto
 * @data 02/09/2004
 */
public class MaxPathNaive extends MaxPath
{
	public MaxPathNaive(EditGraphOld editGraph, PrintStream out)
	{
		super (editGraph, out);
	}
	
	public MaxPathNaive(EditGraphOld editGraph)
	{
		super (editGraph);
	}

	protected MatrixInt buildMatrix(PrintStream out)
	{
		int rowsQtty = editGraph.getRowsQtty();
		int colsQtty = editGraph.getColsQtty();
		int[][] m = new int[rowsQtty][colsQtty];
		MatrixInt ret = new MatrixIntPrimitive(m);
		int i, k, j;
		int upLeftValue;
		int a, b, c;
		for (i = 0; i < rowsQtty; i++)
		{
			for (k=0;k<i;k++)
			{
				//para j == 0 sempre vem de um arco vertical
				upLeftValue = m[k][0];
				m[k][0] += editGraph.getWeightVertical(i, 0);
				for (j = 1; j < colsQtty; j++)
				{
					a = m[k][j] + editGraph.getWeightVertical(i, j);
					b = upLeftValue + editGraph.getWeightDiagonal(i, j);
					c = m[k][j-1] + editGraph.getWeightHorizontal(i, j);
					upLeftValue = m[k][j];
					m[k][j] = max(a, b, c);
				}
			}
			//para k == i sempre vem de um arco horizontal
			m[i][0] = 0;
			for (j = 1; j < colsQtty; j++)
			{
				m[i][j] = m[i][j-1] + editGraph.getWeightHorizontal(i, j);
			}
			if (out != null)
			{
				ret.print(out, i);
			}
		}
		return ret;
	}

	private int max(int x, int y, int z)
	{
		return Math.max(x, Math.max(y, z));
	}


}

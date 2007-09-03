/*
 * Created on 22/10/2004
 */
package sequences.matrix;

import sequences.bim.n3lgn.TreeJeanette;

/**
 * @author Augusto
 */
public class MatrixIntTreeJeanette extends MatrixIntImpl
{
	TreeJeanette[]	trees;

	public MatrixIntTreeJeanette(TreeJeanette[] trees)
	{
		super();
		this.trees = trees;
	}

	public int getValue(int row, int col)
	{
		if (row < 0 || row >= getRowsQtty() || col < 0 || col >= getColsQtty())
		{
			throw new IndexOutOfBoundsException("Não existe o índice " + row
				+ ", " + col);
		}
		if (row >= trees[col].getNumLeafs())
		{
			return Integer.MIN_VALUE;
		}
		return trees[col].getScoringPath(row);
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("Só é possível ler os valores desta matriz.");
	}

	public int getRowsQtty()
	{
		return trees[getColsQtty() - 1].getNumLeafs();
	}

	public int getColsQtty()
	{
		return trees.length;
	}

}

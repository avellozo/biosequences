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
	int				defaultValue;

	//Normalmente dafaultValue é Integer.MIN_VALUE ou Integer.MAX_VALUE
	//As árvores são as colunas da matriz
	public MatrixIntTreeJeanette(TreeJeanette[] trees, int defaultValue)
	{
		this.trees = trees;
		this.defaultValue = defaultValue;
	}

	public int getValue(int row, int col)
	{
		if (!isValidRowCol(row, col))
		{
			throw new IndexOutOfBoundsException("Row " + row + " col " + col);
		}
		if (row >= trees[col].getNumLeafs())
		{
			return defaultValue;
		}
		return trees[col].getScoringPath(row);
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("It's impossible to write in this matrix.");
	}

	public int getRowsQtty()
	{
		return trees[getColsQtty() - 1].getNumLeafs();
	}

	public int getColsQtty()
	{
		return trees.length;
	}

	public int getIndexBeginCol()
	{
		return 0;
	}

	public int getIndexBeginRow()
	{
		return 0;
	}

	public int getIndexEndCol()
	{
		return trees.length - 1;
	}

	public int getIndexEndRow()
	{
		return trees[getColsQtty() - 1].getNumLeafs() - 1;
	}

	public int getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(int defaultValue)
	{
		this.defaultValue = defaultValue;
	}

}

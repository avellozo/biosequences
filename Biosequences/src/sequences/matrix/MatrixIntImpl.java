/*
 * Created on 22/10/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 */
public abstract class MatrixIntImpl implements MatrixInt
{

	//	public int getValue(int row, int col)
	//	{
	//		if (isValidRowCol(row, col))
	//		{
	//			return getValueFromZeroIndex(row - getIndexBeginRow(), col - getIndexBeginCol());
	//		}
	//		throw new IndexOutOfBoundsException("Row " + row + " col " + col);
	//	}
	//
	//	protected abstract int getValueFromZeroIndex(int i, int j);
	//
	//	public void setValue(int row, int col, int value)
	//	{
	//		if (isValidRowCol(row, col))
	//		{
	//			setValueFromZeroIndex(row - getIndexBeginRow(), col - getIndexBeginCol(), value);
	//		}
	//		throw new IndexOutOfBoundsException("Row " + row + " col " + col);
	//	}
	//
	//	protected abstract void setValueFromZeroIndex(int i, int j, int value);
	//
	public boolean isValidRowCol(int row, int col)
	{
		return ((row >= getIndexBeginRow()) && (row <= getIndexEndRow()) && (col >= getIndexBeginCol()) && (col <= getIndexEndCol()));
	}

	public int getRowsQtty()
	{
		return (getIndexEndRow() - getIndexBeginRow() + 1);
	}

	public int getColsQtty()
	{
		return (getIndexEndCol() - getIndexBeginCol() + 1);
	}

	public int[][] getMatrixPrimitive()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		int[][] ret = new int[getRowsQtty()][getColsQtty()];
		int val;
		for (int i = iBegin; i <= iEnd; i++)
		{
			for (int j = jBegin; j <= jEnd; j++)
			{
				ret[i - iBegin][j - jBegin] = getValue(i, j);
			}
		}
		return ret;
	}

	public ArrayInt getRow(int row)
	{
		return new RowIntOfMatrixInt(this, row);
	}

	public ArrayInt[] getRows()
	{
		ArrayInt[] ret = new ArrayInt[getRowsQtty()];
		int iEnd = getIndexEndRow();
		int iBegin = getIndexBeginRow();
		for (int i = iBegin; i <= iEnd; i++)
		{
			ret[i - iBegin] = getRow(i);
		}
		return ret;
	}

	public ElementInt getMaxValue()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		int max = getValue(iBegin, jBegin);
		int iMax = iBegin;
		int jMax = jBegin;
		int val;
		for (int i = iBegin; i <= iEnd; i++)
		{
			for (int j = jBegin; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val > max)
				{
					max = val;
					iMax = i;
					jMax = j;
				}
			}
		}
		return new ElementInt(max, iMax, jMax);
	}

	public ElementInt getMinValue()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		int min = getValue(iBegin, jBegin);
		int iMin = iBegin;
		int jMin = jBegin;
		int val;
		for (int i = iBegin; i <= iEnd; i++)
		{
			for (int j = jBegin; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val < min)
				{
					min = val;
					iMin = i;
					jMin = j;
				}
			}
		}
		return new ElementInt(min, iMin, jMin);
	}

	public ArrayInt getIndexMaxColValues()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		ArrayIntRange ret = new ArrayIntRange(jBegin, jEnd);
		int max, val;
		for (int j = jBegin; j <= jEnd; j++)
		{
			max = getValue(iBegin, j);
			ret.setValue(j, iBegin);
			for (int i = iBegin + 1; i <= iEnd; i++)
			{
				val = getValue(i, j);
				if (val > max)
				{
					ret.setValue(j, i);
					max = val;
				}
			}
		}
		return ret;
	}

	public ArrayInt getIndexMaxRowValues()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		ArrayIntRange ret = new ArrayIntRange(iBegin, iEnd);
		int max, val;
		for (int i = iBegin; i <= iEnd; i++)
		{
			max = getValue(i, jBegin);
			ret.setValue(i, jBegin);
			for (int j = jBegin + 1; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val > max)
				{
					ret.setValue(i, j);
					max = val;
				}
			}
		}
		return ret;
	}

	public ArrayInt getIndexMinColValues()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		ArrayIntRange ret = new ArrayIntRange(jBegin, jEnd);
		int min, val;
		for (int j = jBegin; j <= jEnd; j++)
		{
			min = getValue(iBegin, j);
			ret.setValue(j, iBegin);
			for (int i = iBegin + 1; i <= iEnd; i++)
			{
				val = getValue(i, j);
				if (val < min)
				{
					ret.setValue(j, i);
					min = val;
				}
			}
		}
		return ret;
	}

	public ArrayInt getIndexMinRowValues()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		ArrayIntRange ret = new ArrayIntRange(iBegin, iEnd);
		int min, val;
		for (int i = iBegin; i <= iEnd; i++)
		{
			min = getValue(i, jBegin);
			ret.setValue(i, jBegin);
			for (int j = jBegin + 1; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val < min)
				{
					ret.setValue(i, j);
					min = val;
				}
			}
		}
		return ret;
	}

	public ArrayInt getMaxColValues()
	{
		ArrayInt ret = getIndexMaxColValues();
		int jEnd = getIndexEndCol();
		for (int j = getIndexBeginCol(); j <= jEnd; j++)
		{
			ret.setValue(j, getValue(ret.getValue(j), j));
		}
		return ret;
	}

	public ArrayInt getMaxRowValues()
	{
		ArrayInt ret = getIndexMaxRowValues();
		int iEnd = getIndexEndRow();
		for (int i = getIndexBeginRow(); i <= iEnd; i++)
		{
			ret.setValue(i, getValue(i, ret.getValue(i)));
		}
		return ret;
	}

	public ArrayInt getMinColValues()
	{
		ArrayInt ret = getIndexMinColValues();
		int jEnd = getIndexEndCol();
		for (int j = getIndexBeginCol(); j <= jEnd; j++)
		{
			ret.setValue(j, getValue(ret.getValue(j), j));
		}
		return ret;
	}

	public ArrayInt getMinRowValues()
	{
		ArrayInt ret = getIndexMinRowValues();
		int iEnd = getIndexEndRow();
		for (int i = getIndexBeginRow(); i <= iEnd; i++)
		{
			ret.setValue(i, getValue(i, ret.getValue(i)));
		}
		return ret;
	}

	public String toString()
	{
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		StringBuffer str = new StringBuffer();
		str.append(getRow(iBegin));
		for (int i = iBegin + 1; i <= iEnd; i++)
		{
			str.append('\n');
			str.append(getRow(i));
		}
		return str.toString();
	}

	public boolean equals(MatrixInt m)
	{
		if (this == m)
		{
			return true;
		}
		if (m == null)
		{
			return false;
		}
		int cols = getColsQtty();
		int rows = getRowsQtty();
		if ((rows != m.getRowsQtty()) || (cols != m.getColsQtty()))
		{
			return false;
		}
		int iBegin = getIndexBeginRow();
		int iEnd = getIndexEndRow();
		int jBegin = getIndexBeginCol();
		int jEnd = getIndexEndCol();
		for (int i = iBegin; i <= iEnd; i++)
		{
			for (int j = jBegin + 1; j <= jEnd; j++)
			{
				if (getValue(i, j) != m.getValue(i, j))
				{
					return false;
				}
			}
		}
		return true;
	}

	//	int	indexEndCol, indexBeginCol, indexEndRow, indexBeginRow;
	//
	//	public MatrixIntImpl(int indexEndCol, int indexBeginCol, int indexEndRow, int indexBeginRow)
	//	{
	//		this.indexEndCol = indexEndCol;
	//		this.indexBeginCol = indexBeginCol;
	//		this.indexEndRow = indexEndRow;
	//		this.indexBeginRow = indexBeginRow;
	//	}
	//
	//	public int getIndexEndCol()
	//	{
	//		return indexEndCol;
	//	}
	//
	//	public int getIndexBeginCol()
	//	{
	//		return indexBeginCol;
	//	}
	//
	//	public int getIndexEndRow()
	//	{
	//		return indexEndRow;
	//	}
	//
	//	public int getIndexBeginRow()
	//	{
	//		return indexBeginRow;
	//	}
	//

	//	public void print(PrintStream out, int rows)
	//	{
	//		for (int i = 0; i < rows; i++)
	//		{
	//			printRow(out, i);
	//		}
	//	}
	//
	//	public void printRow(PrintStream out, int row)
	//	{
	//		int cols = getColsQtty();
	//		int j;
	//		for (j = 0; j < (cols - 1); j++)
	//		{
	//			out.print(getValue(row, j));
	//			out.print(' ');
	//		}
	//		out.println(getValue(row, j));
	//	}
	//
	// public MatrixInt getInvertedMatrix()
	// {
	// return new MatrixIntInverted(this);
	// }
}

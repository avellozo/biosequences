/*
 * Created on 22/10/2004
 */
package sequences.matrix;

import java.io.PrintStream;

/**
 * @author Augusto
 */
public abstract class MatrixIntImpl implements MatrixInt
{
	public abstract int getValue(int row, int col);

	public abstract void setValue(int row, int col, int value);

	public abstract int getRowsQtty();

	public abstract int getColsQtty();

	public int[] getMaxColValues()
	{
		int[] ret = getIndexMaxColValues();
		for (int j = 0; j < ret.length; j++)
		{
			ret[j] = getValue(ret[j], j);
		}
		return ret;
	}

	public int[] getIndexMaxColValues()
	{
		int colsQtty = getColsQtty();
		int rowsQtty = getRowsQtty();
		int value, maxValue;
		int[] ret = new int[colsQtty];
		for (int j = 0; j < colsQtty; j++)
		{
			maxValue = getValue(0, j);
			ret[j] = 0;
			for (int i = 1; i < rowsQtty; i++)
			{
				value = getValue(i, j);
				if (value > maxValue)
				{
					maxValue = value;
					ret[j] = i;
				}
			}
		}
		return ret;
	}

	public int[] getMaxRowValues()
	{
		int[] ret = getIndexMaxRowValues();
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = getValue(i, ret[i]);
		}
		return ret;
	}

	public int[] getIndexMaxRowValues()
	{
		int colsQtty = getColsQtty();
		int rowsQtty = getRowsQtty();
		int value, maxValue;
		int[] ret = new int[rowsQtty];
		for (int i = 0; i < colsQtty; i++)
		{
			maxValue = getValue(i, 0);
			ret[i] = 0;
			for (int j = 1; j < rowsQtty; j++)
			{
				value = getValue(i, j);
				if (value > maxValue)
				{
					maxValue = value;
					ret[i] = j;
				}
			}
		}
		return ret;
	}

	public void print(PrintStream out)
	{
		print(out, getRowsQtty());

	}

	public void print(PrintStream out, int rows)
	{
		for (int i = 0; i < rows; i++)
		{
			printRow(out, i);
		}
	}

	public void printRow(PrintStream out, int row)
	{
		int cols = getColsQtty();
		int j;
		for (j = 0; j < (cols - 1); j++)
		{
			out.print(getValue(row, j));
			out.print(' ');
		}
		out.println(getValue(row, j));
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
		int i, j;
		for (i = 0; i < rows; i++)
		{
			for (j = 0; j < cols; j++)
			{
				if (getValue(i, j) != m.getValue(i, j))
				{
					return false;
				}
			}
		}
		return true;
	}

	public RowInt[] getRows()
	{
		int rows = getRowsQtty();
		RowInt[] ret = new RowInt[rows];
		for (int i = 0; i < rows; i++)
		{
			ret[i] = new RowIntOfMatrixInt(this, i);
		}
		return ret;
	}

	// public MatrixInt getInvertedMatrix()
	// {
	// return new MatrixIntInverted(this);
	// }
}

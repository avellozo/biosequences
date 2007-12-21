/*
 * Created on 14/12/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 */
public class RowIntOfMatrixInt implements ArrayInt
{
	MatrixInt	m;
	int			row;

	public RowIntOfMatrixInt(MatrixInt m, int row)
	{
		this.m = m;
		this.row = row;
	}

	public int getValue(int col)
	{
		return m.getValue(row, col);
	}

	public void setValue(int col, int value)
	{
		m.setValue(row, col, value);
	}

	public int length()
	{
		return m.getColsQtty();
	}

	public int[] getArray()
	{
		ArrayInt a = m.getRow(row);
		if (a instanceof RowIntOfMatrixInt)
		{
			int l = length();
			int b = getIndexBegin();
			int[] ret = new int[l];
			for (int i = 0; i < l; i++)
			{
				ret[i] = getValue(b + i);
			}
			return ret;
		}
		return a.getArray();
	}

	public int getIndexBegin()
	{
		return m.getIndexBeginCol();
	}

	public int getIndexEnd()
	{
		return m.getIndexEndCol();
	}

	public boolean isValidIndex(int i)
	{
		return (i >= getIndexBegin() && i <= getIndexEnd());
	}

}

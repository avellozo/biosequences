/*
 * Criado em 16/06/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 16/06/2004
 */
public class RowIntArray implements RowInt
{
	int[]	row;

	public RowIntArray(int[] row)
	{
		this.row = row;
	}

	public RowIntArray(int length)
	{
		row = new int[length];
	}

	// public static RowIntArray[] createRows(int[][] m)
	// {
	// if (m == null)
	// {
	// return new RowIntArray[0];
	// }
	// RowIntArray[] ret = new RowIntArray[m.length];
	// for (int i =0; i < m.length; i++)
	// {
	// ret[i] = new RowIntArray(m[i]);
	// }
	// return ret;
	// }

	public int getValue(int col)
	{
		return row[col];
	}

	public void setValue(int col, int value)
	{
		row[col] = value;
	}

	public int length()
	{
		return row.length;
	}
}

/*
 * Criado em 03/07/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 03/07/2004
 */
public final class MatrixIntPrimitive extends MatrixIntImpl
{
	public final int[][]	matrix;

	public MatrixIntPrimitive(int[] matrix)
	{
		this.matrix = new int[1][];
		this.matrix[0] = matrix;
	}

	public MatrixIntPrimitive(int[][] matrix)
	{
		this.matrix = matrix;
	}

	public MatrixIntPrimitive(int rowsQtty, int colsQtty)
	{
		this.matrix = new int[rowsQtty][colsQtty];
	}

	public int getColsQtty()
	{
		return ((getRowsQtty() == 0) ? 0 : matrix[0].length);
	}

	public int getRowsQtty()
	{
		return matrix.length;
	}

	public final int getValue(int row, int col)
	{
		return matrix[row][col];
	}

	public final void setValue(int row, int col, int value)
	{
		matrix[row][col] = value;
	}

	public void setRow(int rowIndex, int[] row)
	{
		matrix[rowIndex] = row;
	}

	public RowInt[] getRows()
	{
		RowIntArray[] ret = new RowIntArray[matrix.length];
		for (int i = 0; i < matrix.length; i++)
		{
			ret[i] = new RowIntArray(matrix[i]);
		}
		return ret;
	}
}

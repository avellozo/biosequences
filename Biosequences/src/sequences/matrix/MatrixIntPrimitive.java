/*
 * Criado em 03/07/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 03/07/2004
 */
public class MatrixIntPrimitive extends MatrixIntImpl
{
	public int[][]	matrix;

	public MatrixIntPrimitive(int[] array)
	{
		this.matrix = new int[1][];
		this.matrix[0] = array;
	}

	public MatrixIntPrimitive(int[][] matrix)
	{
		this.matrix = matrix;
	}

	public MatrixIntPrimitive(int rowsQtty, int colsQtty)
	{
		this(new int[rowsQtty][colsQtty]);
	}

	public int getValue(int row, int col)
	{
		return matrix[row][col];
	}

	public void setValue(int row, int col, int value)
	{
		matrix[row][col] = value;
	}

	public void setRow(int rowIndex, int[] row)
	{
		matrix[rowIndex] = row;
	}

	public ArrayInt getRow(int row)
	{
		return new ArrayIntPrimitive(matrix[row]);
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
		return ((getRowsQtty() == 0) ? -1 : matrix[0].length - 1);
		//		return getColsQtty() - 1;
	}

	public int getIndexEndRow()
	{
		return matrix.length - 1;
		//		return getRowsQtty() - 1;
	}

	public int[][] getMatrixPrimitive()
	{
		return matrix;
	}

	public ElementInt getMaxValue()
	{
		int maxRow = 0;
		int maxCol = 0;
		int max = matrix[0][0];
		int iEnd = getIndexEndRow();
		int jEnd = getIndexEndCol();
		int val;
		for (int i = 0; i <= iEnd; i++)
		{
			for (int j = 0; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val > max)
				{
					max = val;
					maxRow = i;
					maxCol = j;
				}
			}
		}
		return new ElementInt(max, maxRow, maxCol);
	}

	public ElementInt getMinValue()
	{
		int minRow = 0;
		int minCol = 0;
		int min = matrix[0][0];
		int iEnd = getIndexEndRow();
		int jEnd = getIndexEndCol();
		int val;
		for (int i = 0; i <= iEnd; i++)
		{
			for (int j = 0; j <= jEnd; j++)
			{
				val = getValue(i, j);
				if (val < min)
				{
					min = val;
					minRow = i;
					minCol = j;
				}
			}
		}
		return new ElementInt(min, minRow, minCol);
	}

	//	@Override
	//	protected int getValueFromZeroIndex(int i, int j)
	//	{
	//		return getValue(i, j);
	//	}
	//
	//	@Override
	//	protected void setValueFromZeroIndex(int i, int j, int value)
	//	{
	//		setValue(i, j, value);
	//	}
	//
}

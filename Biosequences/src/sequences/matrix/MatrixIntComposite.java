/*
 * Created on 13/12/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 */
public class MatrixIntComposite extends MatrixIntImpl
{
	MatrixInt	m;
	ArrayInt	rowsValue, colsValue;

	public MatrixIntComposite(MatrixInt m, ArrayInt rowsValue, ArrayInt colsValue)
	{
		this.m = m;
		this.rowsValue = rowsValue;
		this.colsValue = colsValue;
	}

	public int getValue(int row, int col)
	{
		int rowValue = 0;
		int colValue = 0;
		if (rowsValue != null && rowsValue.isValidIndex(row))
		{
			rowValue = rowsValue.getValue(row);
		}
		if (colsValue != null && colsValue.isValidIndex(col))
		{
			colValue = colsValue.getValue(col);
		}
		return (m.getValue(row, col) + rowValue + colValue);
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("It's inpossible to write in this matrix.");
	}

	public int getRowsQtty()
	{
		return m.getRowsQtty();
	}

	public int getColsQtty()
	{
		return m.getColsQtty();
	}

	public int getIndexBeginCol()
	{
		return m.getIndexBeginCol();
	}

	public int getIndexBeginRow()
	{
		return m.getIndexBeginRow();
	}

	public int getIndexEndCol()
	{
		return m.getIndexEndCol();
	}

	public int getIndexEndRow()
	{
		return m.getIndexEndRow();
	}

}
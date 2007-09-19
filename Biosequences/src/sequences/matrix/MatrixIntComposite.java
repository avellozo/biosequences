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
	int[]		rowsValue, colsValue;

	public MatrixIntComposite(MatrixInt m, int[] rowsValue, int[] colsValue)
	{
		super();
		this.m = m;
		if (rowsValue == null)
		{
			rowsValue = new int[getRowsQtty()];
		}
		if (colsValue == null)
		{
			colsValue = new int[getColsQtty()];
		}
		this.rowsValue = rowsValue;
		this.colsValue = colsValue;
	}

	public int getValue(int row, int col)
	{
		return (m.getValue(row, col) + rowsValue[row] + colsValue[col]);
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("Só é possível ler os valores desta matriz.");
	}

	public int getRowsQtty()
	{
		return m.getRowsQtty();
	}

	public int getColsQtty()
	{
		return m.getColsQtty();
	}

}
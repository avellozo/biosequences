/*
 * Created on 14/12/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 */
public class RowIntOfMatrixInt implements RowInt
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

}

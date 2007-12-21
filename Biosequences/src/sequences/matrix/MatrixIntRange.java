package sequences.matrix;

public class MatrixIntRange extends MatrixIntImpl
{

	MatrixInt	m;
	int			rowBegin, rowEnd, colEnd, colBegin, rowBeginMapped, colBeginMapped;

	public MatrixIntRange(MatrixInt m, int rowBegin, int colBegin, int rowEnd, int colEnd, int rowBeginMapped,
			int colBeginMapped)
	{
		super();
		this.m = m;
		this.rowBegin = rowBegin;
		this.rowEnd = rowEnd;
		this.colEnd = colEnd;
		this.colBegin = colBegin;
		this.rowBeginMapped = rowBeginMapped;
		this.colBeginMapped = colBeginMapped;
		if ((!m.isValidRowCol(rowMapped(rowBegin), colMapped(colBegin)))
			|| (!m.isValidRowCol(rowMapped(rowEnd), colMapped(colEnd))))
		{
			throw new IndexOutOfBoundsException("Matrix is smaller than new matrix.");
		}
	}

	public MatrixIntRange(MatrixInt m, int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		this(m, rowBegin, colBegin, rowEnd, colEnd, m.getIndexBeginRow(), m.getIndexBeginCol());
	}

	public MatrixIntRange(MatrixInt m, int rowBegin, int colBegin)
	{
		this(m, rowBegin, colBegin, rowBegin + m.getRowsQtty() - 1, colBegin + m.getColsQtty() - 1);
	}

	public MatrixIntRange(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		this(new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1), rowBegin, colBegin, rowEnd, colEnd);
	}

	protected int rowMapped(int row)
	{
		return row - rowBegin + rowBeginMapped;
	}

	protected int colMapped(int col)
	{
		return col - colBegin + colBeginMapped;
	}

	public int getIndexBeginCol()
	{
		return colBegin;
	}

	public int getIndexBeginRow()
	{
		return rowBegin;
	}

	public int getIndexEndCol()
	{
		return colEnd;
	}

	public int getIndexEndRow()
	{
		return rowEnd;
	}

	public int getValue(int row, int col)
	{
		return m.getValue(rowMapped(row), colMapped(col));
	}

	public void setValue(int row, int col, int value)
	{
		m.setValue(rowMapped(row), colMapped(col), value);
	}

	@Override
	public ArrayInt getRow(int row)
	{
		return new ArrayIntRange(m.getRow(row), getIndexBeginRow(), getIndexEndRow());
	}

}

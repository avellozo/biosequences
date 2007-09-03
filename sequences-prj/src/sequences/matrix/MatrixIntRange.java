package sequences.matrix;

public class MatrixIntRange implements MatrixInt
{

	MatrixInt	m;
	int			iMin, jMin;

	public MatrixIntRange(MatrixInt m, int iMin, int iMax, int jMin, int jMax)
			throws Exception
	{
		this.m = m;
		this.iMin = iMin;
		this.jMin = jMin;
		if (m instanceof MatrixIntRange)
		{
			throw new Exception(
				"MatrixIntRange cn nor be created from internal matrix of the type MatrixIntRange.");
		}
	}

	public int getColsQtty()
	{
		return m.getColsQtty();
	}

	public int[] getIndexMaxColValues()
	{
		int[] internal = m.getIndexMaxColValues();
		int[] ret = new int[internal.length];
		for (int i = 0; i < internal.length; i++)
		{
			ret[i] = internal[i] + iMin;
		}
		return ret;
	}

	public int[] getIndexMaxRowValues()
	{
		int[] internal = m.getIndexMaxRowValues();
		int[] ret = new int[internal.length];
		for (int i = 0; i < internal.length; i++)
		{
			ret[i] = internal[i] + jMin;
		}
		return ret;
	}

	public int[] getMaxColValues()
	{
		return m.getMaxColValues();
	}

	public int[] getMaxRowValues()
	{
		return m.getMaxRowValues();
	}

	public RowInt[] getRows()
	{
		throw new RuntimeException("Method isn't implemented");
	}

	public int getRowsQtty()
	{
		return m.getRowsQtty();
	}

	public int getValue(int row, int col)
	{
		return m.getValue(row - iMin, col - jMin);
	}

	public void setValue(int row, int col, int value)
	{
		m.setValue(row - iMin, col - jMin, value);
	}

}

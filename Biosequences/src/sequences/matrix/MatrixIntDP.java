/*
 * Created on 22/05/2006
 */
package sequences.matrix;

/**
 * @author Augusto F. Vellozo
 */
// Matrix de inteiros que representa uma matriz de programação dinâmica
public final class MatrixIntDP implements MatrixInt
{
	private MatrixInt	m;
	boolean				local;	// true if is local alignment
	int					maxValue, rowMaxValue, colMaxValue;
	private boolean		calculated;

	public MatrixIntDP(MatrixInt m, boolean local)
	{
		this.m = m;
		this.local = local;
		calculated = false;
	}

	public MatrixInt getMatrix()
	{
		return m;
	}

	private void calculateMax()
	{
		int maxValue = getValue(0, 0);
		rowMaxValue = 0;
		colMaxValue = 0;
		int v;

		for (int i = 0; i < getRowsQtty(); i++)
		{
			for (int j = 0; j < getColsQtty(); j++)
			{
				if ((v = getValue(i, j)) > maxValue)
				{
					maxValue = v;
					rowMaxValue = i;
					colMaxValue = j;
				}
			}
		}
		calculated = true;
	}

	public final int getValue(int row, int col)
	{
		return m.getValue(row, col);
	}

	public final void setValue(int row, int col, int value)
	{
		if (isLocal() && value < 0)
		{
			value = 0;
		}
		m.setValue(row, col, value);
		calculated = false;
	}

	public int getRowsQtty()
	{
		return m.getRowsQtty();
	}

	public int getColsQtty()
	{
		return m.getColsQtty();
	}

	public int[] getMaxRowValues()
	{
		return m.getMaxRowValues();
	}

	public int[] getIndexMaxRowValues()
	{
		return m.getIndexMaxRowValues();
	}

	public int[] getMaxColValues()
	{
		return m.getMaxColValues();
	}

	public int[] getIndexMaxColValues()
	{
		return m.getIndexMaxColValues();
	}

	public String toString()
	{
		return m.toString();
	}

	public RowInt[] getRows()
	{
		return m.getRows();
	}

	public boolean isLocal()
	{
		return this.local;
	}

	public int getColMaxValue()
	{
		if (!calculated)
		{
			calculateMax();
		}
		return colMaxValue;
	}

	public int getMaxValue()
	{
		if (!calculated)
		{
			calculateMax();
		}
		return maxValue;
	}

	public int getRowMaxValue()
	{
		if (!calculated)
		{
			calculateMax();
		}
		return rowMaxValue;
	}
}
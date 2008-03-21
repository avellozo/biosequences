/*
 * Created on 20/12/2007
 */
package sequences.matrix;

public class MatrixIntPrimitiveForMax extends MatrixIntPrimitive
{
	ElementInt	max;
	ArrayInt	maxRowValues, indexMaxRowValues;
	ArrayInt	maxColValues, indexMaxColValues;

	public MatrixIntPrimitiveForMax(int rowsQtty, int colsQtty)
	{
		super(rowsQtty, colsQtty);
		max = new ElementInt(0, 0, 0);
		maxRowValues = new ArrayIntPrimitive(rowsQtty);
		indexMaxRowValues = new ArrayIntPrimitive(rowsQtty);
		maxColValues = new ArrayIntPrimitive(colsQtty);
		indexMaxColValues = new ArrayIntPrimitive(colsQtty);
	}

	@Override
	public ElementInt getMaxValue()
	{
		return max;
	}

	@Override
	public void setValue(int row, int col, int value)
	{
		super.setValue(row, col, value);
		//verifica se o maior da linha
		if (value > maxRowValues.getValue(row))
		{
			maxRowValues.setValue(row, value);
			indexMaxRowValues.setValue(row, col);
			//se foir maior da linha verifica se é o maior da matriz
			if (value > max.getValue())
			{
				max.setValue(value);
				max.setRow(row);
				max.setCol(col);
			}
		}
		if (value > maxColValues.getValue(col))
		{
			maxColValues.setValue(col, value);
			indexMaxColValues.setValue(col, row);
		}
	}

	@Override
	public ArrayInt getIndexMaxColValues()
	{
		return indexMaxColValues;
	}

	@Override
	public ArrayInt getIndexMaxRowValues()
	{
		return indexMaxRowValues;
	}

	@Override
	public ArrayInt getMaxColValues()
	{
		return maxColValues;
	}

	@Override
	public ArrayInt getMaxRowValues()
	{
		return maxRowValues;
	}

}

/*
 * Criado em 09/06/2004
 */

package sequences.matrix;

/**
 * @author Augusto
 * @data 09/06/2004
 */
public class TMColMatrixInt implements MatrixInt
{
	MatrixInt			m;
	private ArrayInt[]	rows;
	private int			stepCol;
	private int			rowsQtty;

	//Falta fazer o mínimo das colunas

	// A primeira coluna de m deve ter índice zero
	public TMColMatrixInt(MatrixInt m)
	{
		if (m.getIndexBeginCol() != 0)
		{
			throw new IndexOutOfBoundsException("Frist column index of Matrix is diferent of zero.");
		}
		this.m = m;
		setRows(m.getRows());
		setRowsQtty(m.getRowsQtty());
		setStepCol(0);
	}

	// Cria uma nova matriz com a metade das colunas da matriz original e
	// reduz o número de linhas
	//	private TMMatrixInt(TMMatrixInt tmm)
	//	{
	//		setRows(tmm.getRows());
	//		setRowsQtty(tmm.getRowsQtty());
	//		setStepCol(tmm.getStepCol());
	//	}
	//	
	//	protected TMMatrixInt createMatrixInt()
	//	{
	//		return new TMMatrixInt(this);
	//	}
	//
	// Cria uma nova matriz com a metade das colunas da matriz original
	protected TMColMatrixInt createMatrixInt()
	{
		TMColMatrixInt ret = new TMColMatrixInt(this);
		ret.setStepCol(getStepCol() + 1);
		return ret;
	}

	public ArrayInt[] getRows()
	{
		return rows;
	}

	public ArrayInt getRow(int row)
	{
		return rows[row];
	}

	protected void setRow(int index, ArrayInt row)
	{
		rows[index] = row;
	}

	protected void setRows(ArrayInt[] rows)
	{
		this.rows = rows;
	}

	protected int getStepCol()
	{
		return stepCol;
	}

	protected void setStepCol(int stepCol)
	{
		this.stepCol = stepCol;
	}

	//	private void incStepCol()
	//	{
	//		stepCol++;
	//	}
	//
	public int getValue(int row, int col)
	{
		return getRow(row).getValue(getOriginalCol(col));
	}

	public void setValue(int row, int col, int value)
	{
		getRow(row).setValue(getOriginalCol(col), value);
	}

	public int getOriginalCol(int col)
	{
		return (col << getStepCol());
	}

	protected void setRowsQtty(int rowsQtty)
	{
		this.rowsQtty = rowsQtty;
	}

	public int getRowsQtty()
	{
		return rowsQtty;
	}

	public int getColsQtty()
	{
		return ((getRowsQtty() > 0) ? ((getRow(0).length() - 1) >> getStepCol()) + 1 : 0);
	}

	public void reduce()
	{
		ArrayInt[] oldRows = getRows();
		int oldRowsQtty = getRowsQtty();

		int colsQtty = getColsQtty();
		setRows(new ArrayInt[colsQtty]);
		if (colsQtty == 0)
		{
			return;
		}
		int k = 0;
		setRow(k, oldRows[0]);
		int k1 = 1;
		ArrayInt rowK1;
		while (k1 < oldRowsQtty)
		{
			rowK1 = oldRows[k1];
			if (getValue(k, k) >= rowK1.getValue(getOriginalCol(k)))
			{
				if (k < (colsQtty - 1))
				// k = k+1
				{
					k++;
					k1++;
					setRow(k, rowK1);
				}
				else
				// remove linha k+1
				{
					k1++;
				}
			}
			else
			// remove linha k
			{
				// se k > 1 então k--
				if (k > 0)
				{
					k--;
				}
				else
				{
					setRow(k, rowK1);
					k1++;
				}
			}
		}
		setRowsQtty(k + 1);
	}

	public ArrayInt[] getRowsMaxValueColumns()
	{
		ArrayInt[] maxRowCols;
		int colsQtty = getColsQtty();

		if (getRowsQtty() <= 1)
		{
			// Cria o array que conterá os índices dos máximos nas colunas
			if (getRowsQtty() == 1)
			{
				maxRowCols = new ArrayInt[getRow(0).length()];
			}
			else
			{
				maxRowCols = new ArrayInt[0]; //???????????
			}
			for (int j = 0; j < colsQtty; j++)
			{
				maxRowCols[getOriginalCol(j)] = getRow(0);
			}
		}
		else
		{
			// Cria uma nova matriz reduzida e obtém os seus máximos
			TMColMatrixInt tmm = createMatrixInt();
			tmm.reduce();
			maxRowCols = tmm.getRowsMaxValueColumns();
			int startRow = 0; // 1ª linha onde pode estar o próximo máximo
			int max; // linha do máximo
			int i;
			ArrayInt endRow; // índice da matrix original da última linha
			// onde pode estar o próximo máximo
			for (int j = 1; j < colsQtty; j += 2)
			{
				max = startRow;
				if ((j + 1) < colsQtty)
				{
					endRow = maxRowCols[getOriginalCol(j + 1)];
				}
				else
				{
					endRow = getRow(getRowsQtty() - 1);
				}
				i = startRow;
				if ((i + 1) < getRowsQtty())
				{
					while (getRow(i) != endRow)
					{
						i++;
						if (getValue(i, j) > getValue(max, j))
						{
							max = i;
						}
					}
				}
				maxRowCols[getOriginalCol(j)] = getRow(max);
				startRow = i;
			}
		}
		return maxRowCols;
	}

	public ArrayInt getMaxColValues()
	{
		return getMaxColValues(getRowsMaxValueColumns());
	}

	protected ArrayInt getMaxColValues(ArrayInt[] rowsMax)
	{
		int cols = getColsQtty();
		ArrayIntPrimitive rowsValueMax = new ArrayIntPrimitive(cols);
		for (int i = 0; i < cols; i++)
		{
			rowsValueMax.setValue(i, rowsMax[i].getValue(i));
		}
		return rowsValueMax;
	}

	public ArrayInt getIndexMaxColValues()
	{
		return getIndexMaxColValues(getRowsMaxValueColumns());
	}

	protected ArrayInt getIndexMaxColValues(ArrayInt[] rowsMax)
	{
		int cols = getColsQtty();
		ArrayIntPrimitive rowsIndexMax = new ArrayIntPrimitive(cols);
		int k = 0;
		int iMax = getRowsQtty();
		for (int i = 0; (i < iMax) && (k < cols); i++)
		{
			if (rowsMax[k] == getRow(i))
			{
				rowsIndexMax.setValue(k, i);
				k++;
				i--;
			}
		}
		return rowsIndexMax;
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
		return getColsQtty() - 1;
	}

	public int getIndexEndRow()
	{
		return getRowsQtty() - 1;
	}

	public ArrayInt getIndexMinColValues()
	{
		// TODO Auto-generated method stub
		return m.getIndexMinColValues();
	}

	public int[][] getMatrixPrimitive()
	{
		return m.getMatrixPrimitive();
	}

	public ElementInt getMaxValue()
	{
		ArrayInt maxIndexCols = getIndexMaxColValues();
		int maxCol = 0;
		int maxRow = maxIndexCols.getValue(maxCol);
		int maxVal = getValue(maxRow, maxCol);
		int jEnd = maxIndexCols.getIndexEnd();
		int val;
		for (int j = 1; j <= jEnd; j++)
		{
			val = getValue(maxIndexCols.getValue(j), j);
			if (val > maxVal)
			{
				maxVal = val;
				maxRow = maxIndexCols.getValue(j);
				maxCol = j;
			}
		}
		return new ElementInt(maxVal, maxRow, maxCol);
	}

	public ArrayInt getMinColValues()
	{
		// TODO Auto-generated method stub
		return m.getMinColValues();
	}

	public ElementInt getMinValue()
	{
		ArrayInt minIndexCols = getIndexMinColValues();
		int minCol = 0;
		int minRow = minIndexCols.getValue(minCol);
		int minVal = getValue(minRow, minCol);
		int jEnd = minIndexCols.getIndexEnd();
		int val;
		for (int j = 1; j <= jEnd; j++)
		{
			val = getValue(minIndexCols.getValue(j), j);
			if (val < minVal)
			{
				minVal = val;
				minRow = minIndexCols.getValue(j);
				minCol = j;
			}
		}
		return new ElementInt(minVal, minRow, minCol);
	}

	public ArrayInt getIndexMaxRowValues()
	{
		return m.getIndexMaxRowValues();
	}

	public ArrayInt getIndexMinRowValues()
	{
		return m.getIndexMinRowValues();
	}

	public ArrayInt getMaxRowValues()
	{
		return m.getMaxRowValues();
	}

	public ArrayInt getMinRowValues()
	{
		return m.getMinRowValues();
	}

	public boolean isValidRowCol(int row, int col)
	{
		return m.isValidRowCol(row, col);
	}

	// public void setRow(int rowIndex, int[] row)
	// {
	// setRow(rowIndex, new RowIntArray(row));
	// }
}

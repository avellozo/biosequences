/*
 * Criado em 09/06/2004
 */

package sequences.matrix;

/**
 * @author Augusto
 * @data 09/06/2004
 */
public class TMMatrixInt extends MatrixIntImpl
{
	private RowInt[]	rows;
	private int			stepCol;
	private int			rowsQtty;

	public TMMatrixInt(MatrixInt m)
	{
		setRows(m.getRows());
		setRowsQtty(m.getRowsQtty());
		setStepCol(0);
	}

	// Cria uma nova matriz com a metade das colunas da matriz original e
	// reduz o número de linhas
	private TMMatrixInt(TMMatrixInt tmm)
	{
		setRows(tmm.getRows());
		setRowsQtty(tmm.getRowsQtty());
		setStepCol(tmm.getStepCol());
	}

	protected TMMatrixInt createMatrixInt()
	{
		return new TMMatrixInt(this);
	}

	public RowInt[] getRows()
	{
		return rows;
	}

	public RowInt getRow(int row)
	{
		return rows[row];
	}

	protected void setRow(int index, RowInt row)
	{
		rows[index] = row;
	}

	protected void setRows(RowInt[] rows)
	{
		this.rows = rows;
	}

	private int getStepCol()
	{
		return stepCol;
	}

	private void setStepCol(int stepCol)
	{
		this.stepCol = stepCol;
	}

	private void incStepCol()
	{
		stepCol++;
	}

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

	public void setRowsQtty(int rowsQtty)
	{
		this.rowsQtty = rowsQtty;
	}

	public int getRowsQtty()
	{
		return rowsQtty;
	}

	public int getColsQtty()
	{
		return ((getRowsQtty() > 0) ? ((getRow(0).length() - 1) >> getStepCol()) + 1
			: 0);
	}

	public void reduce()
	{
		RowInt[] oldRows = getRows();
		int oldRowsQtty = getRowsQtty();

		incStepCol();
		int colsQtty = getColsQtty();
		setRows(new RowInt[colsQtty]);
		if (colsQtty == 0)
		{
			return;
		}
		int k = 0;
		setRow(k, oldRows[0]);
		int k1 = 1;
		RowInt rowK1;
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

	public RowInt[] getRowsMaxValueColumns()
	{
		RowInt[] maxRowCols;
		int colsQtty = getColsQtty();

		if (getRowsQtty() <= 1)
		{
			// Cria o array que conterá os índices dos máximos nas colunas
			if (getRowsQtty() == 1)
			{
				maxRowCols = new RowInt[getRow(0).length()];
			}
			else
			{
				maxRowCols = new RowInt[0];
			}
			for (int j = 0; j < colsQtty; j++)
			{
				maxRowCols[getOriginalCol(j)] = getRow(0);
			}
		}
		else
		{
			// Cria uma nova matriz reduzida e obtém os seus máximos
			TMMatrixInt tmm = createMatrixInt();
			tmm.reduce();
			maxRowCols = tmm.getRowsMaxValueColumns();
			int startRow = 0; // 1ª linha onde pode estar o próximo máximo
			int max; // linha do máximo
			int i;
			RowInt endRow; // índice da matrixBim original da última linha
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

	public int[] getMaxColValues()
	{
		return getMaxColValues(getRowsMaxValueColumns());
	}

	public int[] getMaxColValues(RowInt[] rowsMax)
	{
		int cols = getColsQtty();
		int[] rowsValueMax = new int[cols];
		for (int i = 0; i < cols; i++)
		{
			rowsValueMax[i] = rowsMax[i].getValue(i);
		}
		return rowsValueMax;
	}

	public int[] getIndexMaxColValues()
	{
		return getIndexMaxColValues(getRowsMaxValueColumns());
	}

	public int[] getIndexMaxColValues(RowInt[] rowsMax)
	{
		int cols = getColsQtty();
		int[] rowsIndexMax = new int[cols];
		int k = 0;
		int iMax = getRowsQtty();
		for (int i = 0; (i < iMax) && (k < cols); i++)
		{
			if (rowsMax[k] == getRow(i))
			{
				rowsIndexMax[k] = i;
				k++;
				i--;
			}
		}
		return rowsIndexMax;
	}

	// public void setRow(int rowIndex, int[] row)
	// {
	// setRow(rowIndex, new RowIntArray(row));
	// }
}

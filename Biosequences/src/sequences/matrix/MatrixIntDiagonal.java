/*
 * Created on 22/10/2004
 */
package sequences.matrix;

import sequences.editgraph.EditGraph;

/**
 * @author Augusto
 */
public class MatrixIntDiagonal extends MatrixIntImpl
{
	EditGraph	editGraph;
	int			zeroRowColValue;

	public MatrixIntDiagonal(EditGraph editGraph, int zeroRowColValue)
	{
		this.editGraph = editGraph;
		this.zeroRowColValue = zeroRowColValue;
	}

	public MatrixIntDiagonal(EditGraph editGraph)
	{
		this(editGraph, 0);
	}

	public int getValue(int row, int col)
	{
		try
		{
			return editGraph.getWeightDiagonalArc(row, col);
		}
		catch (Exception e)
		{
			return zeroRowColValue;
		}
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("Só é possível ler os valores desta matriz.");
	}

	public int getRowsQtty()
	{
		return editGraph.getRowMax() + 1;
	}

	public int getColsQtty()
	{
		return editGraph.getColMax() + 1;
	}

	public EditGraph getEditGraph()
	{
		return this.editGraph;
	}

	public void setEditGraph(EditGraph editGraph)
	{
		this.editGraph = editGraph;
	}

	public int getZeroRowColValue()
	{
		return this.zeroRowColValue;
	}

	public void setZeroRowColValue(int zeroRowCol)
	{
		this.zeroRowColValue = zeroRowCol;
	}
}
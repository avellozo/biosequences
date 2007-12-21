/*
 * Created on 22/10/2004
 */
package sequences.matrix;

import sequences.editgraph.EditGraph;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.Vertex;

/**
 * @author Augusto
 */
public class MatrixIntEditGraph extends MatrixIntImpl
{
	EditGraph	eg;

	public MatrixIntEditGraph(EditGraph editGraph)
	{
		this.eg = editGraph;
	}

	public int getValue(int row, int col)
	{
		Vertex v = new Vertex(row, col);
		try
		{
			return eg.getDiagonalArc(v).getWeight();
		}
		catch (ExceptionInvalidVertex e)
		{
			throw new ArrayIndexOutOfBoundsException(v.toString());
		}
	}

	public void setValue(int row, int col, int value)
	{
		throw new RuntimeException("It's inpossible to write in this matrix.");
	}

	public int getRowsQtty()
	{
		return eg.getRowMax() + 1;
	}

	public int getColsQtty()
	{
		return eg.getColMax() + 1;
	}

	public EditGraph getEditGraph()
	{
		return this.eg;
	}

	public void setEditGraph(EditGraph editGraph)
	{
		this.eg = editGraph;
	}

	public int getIndexBeginCol()
	{
		return eg.getColMin();
	}

	public int getIndexBeginRow()
	{
		return eg.getRowMin();
	}

	public int getIndexEndCol()
	{
		return eg.getColMax();
	}

	public int getIndexEndRow()
	{
		return eg.getRowMax();
	}

}
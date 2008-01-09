/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto F. Vellozo
 */
public final class Vertex implements Comparable<Vertex>
{
	final int	row, col;

	public Vertex(int i, int j)
	{
		this.row = i;
		this.col = j;
	}

	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return col;
	}

	//return true if this vertex object dominates v
	public boolean dominates(Vertex v) throws ExceptionInvalidVertex
	{
		if (v == null)
		{
			throw new ExceptionInvalidVertex(v);
		}
		return ((getRow() <= v.getRow()) && (getCol() <= v.getCol()));
	}

	public boolean equals(Vertex v)
	{
		if (v == null)
		{
			return false;
		}
		return ((row == v.getRow()) && (col == v.getCol()));
	}

	public String toString()
	{
		return row + "," + col;
	}

	//
	// basic order: by i and after j
	public int compareTo(Vertex v)
	{
		if (v == null)
		{
			return 1;
		}
		return (row < v.getRow() ? -1 : (row == v.getRow() ? (col < v.getCol() ? -1 : (col == v.getCol() ? 0 : 1)) : 1));
	}

	protected boolean verifyDominates(int row1, int col1, int row2, int col2)
	{
		return ((row1 <= row2) && (col1 <= col2));
	}

	//	public ArcDiagonal getDiagonalArc() throws EGInvalidArcException
	//	{
	//		try
	//		{
	//			return getEditGraph().getDiagonalArc(this);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
	//	public ArcHorizontal getHorizontalArc() throws EGInvalidArcException
	//	{
	//		try
	//		{
	//			return getEditGraph().getHorizontalArc(this);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
	//	public ArcVertical getVerticalArc() throws EGInvalidArcException
	//	{
	//		try
	//		{
	//			return getEditGraph().getVerticalArc(this);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
	//	public boolean existsVerticalArc()
	//	{
	//		try
	//		{
	//			return getEditGraph().existsVerticalArc(i, j);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
	//	public boolean existsHorizontalArc()
	//	{
	//		try
	//		{
	//			return getEditGraph().existsHorizontalArc(i, j);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
	//	public boolean existsDiagonalArc()
	//	{
	//		try
	//		{
	//			return getEditGraph().existsDiagonalArc(i, j);
	//		}
	//		catch (EGInvalidVertexException e)
	//		{
	//			e.printStackTrace();
	//			throw new EGInternalException();
	//		}
	//	}
	//
}
/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public final class Vertex implements Comparable<Vertex>
{
	final int		i, j;
	final EditGraph	eg;

	public Vertex(int i, int j, EditGraph eg) throws ExceptionInvalidVertex
	{
		if (!eg.existsVertex(i, j))
		{
			throw new ExceptionInvalidVertex(i, j, "Can not create this vertex");
		}
		this.i = i;
		this.j = j;
		this.eg = eg;
	}

	public int getI()
	{
		return i;
	}

	public int getJ()
	{
		return j;
	}

	//	//return true if this vertex object dominates v
	//	public boolean dominates(Vertex v) throws EGInvalidVertexException
	//	{
	//		return getEditGraph().dominates(this, v);
	//	}
	//
	public boolean equals(Vertex v)
	{
		if (v == null)
		{
			return false;
		}
		return ((i == v.getI()) && (j == v.getJ()));
	}

	public String toString()
	{
		return i + " " + j;
	}

	public EditGraph getEditGraph()
	{
		return eg;
	}

	// basic order: by i and after j
	public int compareTo(Vertex v)
	{
		if (v == null)
		{
			return 1;
		}
		return (i < v.getI() ? -1 : (i == v.getI() ? (j < v.getJ() ? -1 : (j == v.getJ() ? 0 : 1)) : 1));
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
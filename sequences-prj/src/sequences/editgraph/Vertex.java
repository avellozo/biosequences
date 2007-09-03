/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public final class Vertex<E extends EditGraph<E, ? extends Extender<E>>>
		implements Comparable<Vertex<E>>
{
	final int	i, j;
	final E		eg;

	public Vertex(int i, int j, E eg) throws EGInvalidVertexException
	{
		if (!eg.existsVertex(i, j))
		{
			throw new EGInvalidVertexException(i, j,
				"Can not create this vertex");
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

	public boolean dominates(Vertex<E> v) throws EGInvalidVertexException
	{
		return getEditGraph().dominates(this, v);
	}

	public boolean equals(Vertex<E> v)
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

	public E getEditGraph()
	{
		return eg;
	}

	// basic order: by i and after j
	public int compareTo(Vertex<E> v)
	{
		if (v == null)
		{
			return 1;
		}
		return (i < v.getI() ? -1 : (i == v.getI() ? (j < v.getJ() ? -1
			: (j == v.getJ() ? 0 : 1)) : 1));
	}

	public ArcDiagonal<E> getDiagonalArc() throws EGInvalidArcException
	{
		try
		{
			return getEditGraph().getDiagonalArc(this);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public ArcHorizontal<E> getHorizontalArc() throws EGInvalidArcException
	{
		try
		{
			return getEditGraph().getHorizontalArc(this);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public ArcVertical<E> getVerticalArc() throws EGInvalidArcException
	{
		try
		{
			return getEditGraph().getVerticalArc(this);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public boolean existsVerticalArc()
	{
		return getEditGraph().existsVerticalArc(i, j);
	}

	public boolean existsHorizontalArc()
	{
		return getEditGraph().existsHorizontalArc(i, j);
	}

	public boolean existsDiagonalArc()
	{
		return getEditGraph().existsDiagonalArc(i, j);
	}

}
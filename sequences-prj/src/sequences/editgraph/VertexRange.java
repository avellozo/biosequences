package sequences.editgraph;

public class VertexRange<E extends EditGraph<E, ? extends Extender<E>>>
{

	Vertex<E>	beginVertex, endVertex;

	public VertexRange(Vertex<E> beginVertex, Vertex<E> endVertex)
			throws EGInvalidVertexException
	{
		validateVertexes(beginVertex, endVertex);
		this.beginVertex = beginVertex;
		this.endVertex = endVertex;
	}

	public boolean validateVertexes(Vertex<E> bv, Vertex<E> ev)
			throws EGInvalidVertexException
	{
		if (bv == null)
		{
			throw new EGInvalidVertexException(bv);
		}
		if (ev == null || (bv.getEditGraph() != ev.getEditGraph()))
		{
			throw new EGInvalidVertexException(ev);
		}
		if (!bv.dominates(ev))
		{
			throw new EGInvalidVertexException(bv,
				"Begin vertex is invalid to end vertex: " + ev);
		}
		return true;
	}

	public Vertex<E> getBeginVertex()
	{
		return beginVertex;
	}

	public Vertex<E> getEndVertex()
	{
		return endVertex;
	}

	@Override
	public String toString()
	{
		return "(" + getBeginVertex() + ") (" + getEndVertex() + ")";
	}

	public E getEditGraph()
	{
		return getEndVertex().getEditGraph();
	}

	public int getRows()
	{
		return (getEndVertex().getI() - getBeginVertex().getI() + 1);
	}

	public int getCols()
	{
		return (getEndVertex().getJ() - getBeginVertex().getJ() + 1);
	}
}

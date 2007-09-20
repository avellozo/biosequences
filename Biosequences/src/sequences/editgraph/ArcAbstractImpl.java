/**
 * 
 */
package sequences.editgraph;

public abstract class ArcAbstractImpl<E extends EditGraph<E, ? extends Extender<E>>>
		implements Arc<E>
{
	Vertex<E>	endVertex;
	int			weight;

	public ArcAbstractImpl(Vertex<E> endVertex, int weight)
			throws EGInvalidVertexException
	{
		if (endVertex == null)
		{
			throw new EGInvalidVertexException(endVertex);
		}
		this.endVertex = endVertex;
		this.weight = weight;
	}

	public Vertex<E> getEndVertex()
	{
		return this.endVertex;
	}

	public int getWeight()
	{
		return this.weight;
	}

	// private void setWeight(int weight)
	// {
	// this.weight = weight;
	// }
	//
	public E getEditGraph()
	{
		return getEndVertex().getEditGraph();
	}

	public String toString()
	{
		return "Arc: endVertex:" + getEndVertex() + " weight " + weight;
	}
}
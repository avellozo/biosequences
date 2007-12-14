/**
 * 
 */
package sequences.editgraph;

public abstract class ArcAbstractImpl implements Arc
{
	Vertex	endVertex;
	int		weight;

	public ArcAbstractImpl(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		if (endVertex == null)
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		this.endVertex = endVertex;
		this.weight = weight;
	}

	public Vertex getEndVertex()
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
	public EditGraph getEditGraph()
	{
		return getEndVertex().getEditGraph();
	}

	public String toString()
	{
		return "Arc: endVertex:" + getEndVertex() + " weight " + weight;
	}
}
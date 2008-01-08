/**
 * 
 */
package sequences.editgraph;

public class ArcVertical extends ArcAbstractImpl
{
	public ArcVertical(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
	}

	public Vertex getBeginVertex()
	{
		return new Vertex(getEndVertex().getRow() - 1, getEndVertex().getCol());
	}

	public String toString()
	{
		return "Arc Vertical: endVertex:" + getEndVertex() + " weight " + weight;
	}
}
/**
 * 
 */
package sequences.editgraph;

public class ArcHorizontal extends ArcAbstractImpl
{
	public ArcHorizontal(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
	}

	public Vertex getBeginVertex()
	{
		return new Vertex(getEndVertex().getRow(), getEndVertex().getCol() - 1);
	}

	public String toString()
	{
		return "Arc Horizontal: endVertex:" + getEndVertex() + " weight " + weight;
	}
}
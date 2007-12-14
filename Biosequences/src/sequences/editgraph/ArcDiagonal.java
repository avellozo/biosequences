/**
 * 
 */
package sequences.editgraph;

public class ArcDiagonal extends ArcAbstractImpl
{
	public ArcDiagonal(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
	}

	public Vertex getBeginVertex()
	{
		return new Vertex(getEndVertex().getRow() - 1, getEndVertex().getCol() - 1);
	}

}
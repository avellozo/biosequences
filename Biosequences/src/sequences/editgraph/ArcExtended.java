/**
 * 
 */
package sequences.editgraph;

public class ArcExtended extends ArcAbstractImpl
{
	Vertex	beginVertex;

	//	EditGraphExtended	eg;

	public ArcExtended(Vertex beginVertex, Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		if (beginVertex == null)
		{
			throw new ExceptionInvalidVertex(beginVertex);
		}
		if (!beginVertex.dominates(endVertex))
		{
			throw new ExceptionInvalidVertex(beginVertex, "Begin vertex is invalid to end vertex: " + endVertex);
		}
	}

	public Vertex getBeginVertex()
	{
		return beginVertex;
	}

	public int getRowsOfExtension()
	{
		return (getEndVertex().getRow() - getBeginVertex().getRow() + 1);
	}

	public int getColsOfExtension()
	{
		return (getEndVertex().getCol() - getBeginVertex().getCol() + 1);
	}

	@Override
	public String toString()
	{
		return "Extended " + super.toString();
	}
}
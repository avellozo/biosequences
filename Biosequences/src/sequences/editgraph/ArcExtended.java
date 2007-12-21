/**
 * 
 */
package sequences.editgraph;

public class ArcExtended extends ArcAbstractImpl
{
	VertexRange	vertexRange;

	//	EditGraphExtended	eg;

	public ArcExtended(VertexRange vertexRange, int weight) throws ExceptionInvalidVertex
	{
		super(vertexRange.getEndVertex(), weight);
		this.vertexRange = vertexRange;
	}

	public Vertex getBeginVertex()
	{
		return vertexRange.getBeginVertex();
	}

	public int getRowsOfExtension()
	{
		return vertexRange.getRowsQtty();
	}

	public int getColsOfExtension()
	{
		return vertexRange.getColsQtty();
	}

	@Override
	public String toString()
	{
		return "Extended of Vertex " + vertexRange.getBeginVertex() + " " + super.toString();
	}
}
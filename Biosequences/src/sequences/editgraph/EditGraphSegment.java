package sequences.editgraph;

public class EditGraphSegment
{

	Vertex	beginVertex, endVertex;

	public EditGraphSegment(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (beginVertex == null)
		{
			throw new ExceptionInvalidVertex(beginVertex);
		}
		if (endVertex == null || (beginVertex.getEditGraph() != endVertex.getEditGraph()))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		this.beginVertex = beginVertex;
		this.endVertex = endVertex;
	}

	public Vertex getBeginVertex()
	{
		return beginVertex;
	}

	public Vertex getEndVertex()
	{
		return endVertex;
	}

	@Override
	public String toString()
	{
		return "(" + getBeginVertex() + ") (" + getEndVertex() + ")";
	}

	public EditGraph getEditGraph()
	{
		return getEndVertex().getEditGraph();
	}

	public int getRowsOfExtension()
	{
		return (getEndVertex().getI() - getBeginVertex().getI() + 1);
	}

	public int getColsOfExtension()
	{
		return (getEndVertex().getJ() - getBeginVertex().getJ() + 1);
	}

	public int getRowMax()
	{
		return getEndVertex().getI();
	}

	public int getRowMin()
	{
		return getBeginVertex().getI();
	}

	public int getColMax()
	{
		return getEndVertex().getJ();
	}

	public int getColMin()
	{
		return getBeginVertex().getJ();
	}
}

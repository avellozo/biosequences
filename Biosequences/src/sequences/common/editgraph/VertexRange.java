/*
 * Created on 17/12/2007
 */
package sequences.editgraph;

import sequences.editgraph.exception.ExceptionInvalidVertex;

public class VertexRange
{

	Vertex	beginVertex, endVertex;

	public VertexRange(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		this.beginVertex = beginVertex;
		this.endVertex = endVertex;
		if (beginVertex == null)
		{
			throw new ExceptionInvalidVertex(beginVertex);
		}
		if (endVertex == null)
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		if (!beginVertex.dominates(endVertex))
		{
			throw new ExceptionInvalidVertex("Begin vertex " + beginVertex + " is invalid to end vertex " + endVertex);
		}
	}

	/**
	 * @return Returns the beginVertex.
	 */
	public Vertex getBeginVertex()
	{
		return beginVertex;
	}

	/**
	 * @return Returns the endVertex.
	 */
	public Vertex getEndVertex()
	{
		return endVertex;
	}

	public int getRowsQtty()
	{
		return getEndVertex().getRow() - getBeginVertex().getRow() + 1;
	}

	public int getColsQtty()
	{
		return getEndVertex().getCol() - getBeginVertex().getCol() + 1;
	}

	public String toString()
	{
		return getBeginVertex() + " " + getEndVertex();
	}
}

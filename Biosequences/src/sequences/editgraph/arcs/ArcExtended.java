/**
 * 
 */
package sequences.editgraph.arcs;

import sequences.editgraph.Arc;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtended implements Arc
{
	VertexRange	vertexRange;
	int			weight;

	public ArcExtended(VertexRange vertexRange, int weight) throws ExceptionInvalidVertex
	{
		if (vertexRange == null)
		{
			throw new ExceptionInvalidVertex("Vertex Range = null");
		}
		this.vertexRange = vertexRange;
		this.weight = weight;
	}

	public Vertex getBeginVertex()
	{
		return vertexRange.getBeginVertex();
	}

	public Vertex getEndVertex()
	{
		return vertexRange.getEndVertex();
	}

	public int getWeight()
	{
		return this.weight;
	}

	public String toString()
	{
		return "Arc Extended of Vertex " + vertexRange.getBeginVertex() + " to " + getEndVertex() + " weight " + weight;
	}

	public int getRowsOfExtension()
	{
		return vertexRange.getRowsQtty();
	}

	public int getColsOfExtension()
	{
		return vertexRange.getColsQtty();
	}

}
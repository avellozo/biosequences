/*
 * Created on 19/03/2008
 */
package sequences.common;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcJuntion;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class BackTrackJunctionHorizontal
{
	int	colEnd, colBegin;

	public BackTrackJunctionHorizontal(int colBegin, int colEnd)
	{
		this.colEnd = colEnd;
		this.colBegin = colBegin;
	}

	public ArcJuntion getArcJuntion(Vertex endVertex, EditGraph eg) throws ExceptionInvalidVertex
	{
		if (endVertex.getCol() != this.colEnd)
		{
			throw new ExceptionInvalidVertex(endVertex, "It's impossible junction to this end column:"
				+ endVertex.getCol());
		}
		try
		{
			return new ArcJuntion(new VertexRange(new Vertex(endVertex.getRow(), colBegin), endVertex));
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void setJunction(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
			throws ExceptionInvalidVertex
	{
		if (colEnd != this.colEnd)
		{
			throw new ExceptionInvalidVertex("It's impossible junction to this end column:" + colEnd);
		}
		if (colBegin != this.colBegin)
		{
			throw new ExceptionInvalidVertex("It's impossible junction to this begin column:" + colBegin);
		}
	}

}

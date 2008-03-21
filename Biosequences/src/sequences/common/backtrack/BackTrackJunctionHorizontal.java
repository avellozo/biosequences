/*
 * Created on 19/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcJuntion;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class BackTrackJunctionHorizontal implements BackTrackJunction
{
	int	colEnd, colBegin;

	public BackTrackJunctionHorizontal(int colBegin, int colEnd)
	{
		this.colEnd = colEnd;
		this.colBegin = colBegin;
	}

	/* (non-Javadoc)
	 * @see sequences.common.BackTrackJunction#getArcJuntion(sequences.editgraph.Vertex, sequences.editgraph.EditGraph)
	 */
	public ArcJuntion getArcJuntion(Vertex endVertex) throws ExceptionInvalidVertex
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

	/* (non-Javadoc)
	 * @see sequences.common.BackTrackJunction#setJunction(int, int, int, int, int)
	 */
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

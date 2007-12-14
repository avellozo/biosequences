/**
 * 
 */
package sequences.editgraph;

public class ArcDiagonal extends ArcAbstractImpl
{
	public ArcDiagonal(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		if (!getEditGraph().existsDiagonalArc(endVertex.getI(), endVertex.getJ()))
		{
			throw new ExceptionInvalidVertex(this.endVertex, "Can not create diagonal arc on the vertex "
				+ this.endVertex);
		}
	}

	public Vertex getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI() - 1, getEndVertex().getJ() - 1);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

}
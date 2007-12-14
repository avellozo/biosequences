/**
 * 
 */
package sequences.editgraph;

public class ArcHorizontal extends ArcAbstractImpl
{
	public ArcHorizontal(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		if (!getEditGraph().existsHorizontalArc(endVertex.getI(), endVertex.getJ()))
		{
			throw new ExceptionInvalidVertex(this.endVertex, "Can not create horizontal arc on the vertex "
				+ this.endVertex);
		}
	}

	public Vertex getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI(), getEndVertex().getJ() - 1);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}
}
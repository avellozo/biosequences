/**
 * 
 */
package sequences.editgraph;

public class ArcVertical extends ArcAbstractImpl
{
	public ArcVertical(Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		if (!getEditGraph().existsVerticalArc(endVertex.getI(), endVertex.getJ()))
		{
			throw new ExceptionInvalidVertex(this.endVertex, "Can not create vertical arc on the vertex "
				+ this.endVertex);
		}
	}

	public Vertex getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI() - 1, getEndVertex().getJ());
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}
}
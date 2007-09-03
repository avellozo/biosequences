/**
 * 
 */
package sequences.editgraph;

public class ArcVertical<E extends EditGraph<E, ? extends Extender<E>>> extends
		ArcAbstractImpl<E>
{
	public ArcVertical(Vertex<E> endVertex, int weight)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		super(endVertex, weight);
		if (!getEditGraph().existsVerticalArc(endVertex.getI(),
			endVertex.getJ()))
		{
			throw new EGInvalidArcException(EditGraph.VERTICAL,
				"Can not create vertical arc on the vertex " + this.endVertex);
		}
	}

	public Vertex<E> getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI() - 1,
				getEndVertex().getJ());
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}
}
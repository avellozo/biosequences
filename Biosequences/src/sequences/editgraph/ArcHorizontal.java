/**
 * 
 */
package sequences.editgraph;

public class ArcHorizontal<E extends EditGraph<E, ? extends Extender<E>>>
		extends ArcAbstractImpl<E>
{
	public ArcHorizontal(Vertex<E> endVertex, int weight)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		super(endVertex, weight);
		if (!getEditGraph().existsHorizontalArc(endVertex.getI(),
			endVertex.getJ()))
		{
			throw new EGInvalidArcException(EditGraph.HORIZONTAL,
				"Can not create horizontal arc on the vertex " + this.endVertex);
		}
	}

	public Vertex<E> getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI(),
				getEndVertex().getJ() - 1);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}
}
/**
 * 
 */
package sequences.editgraph;

public class ArcDiagonal<E extends EditGraph<E, ? extends Extender<E>>> extends
		ArcAbstractImpl<E>
{
	public ArcDiagonal(Vertex<E> endVertex, int weight)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		super(endVertex, weight);
		if (!getEditGraph().existsDiagonalArc(endVertex.getI(),
			endVertex.getJ()))
		{
			throw new EGInvalidArcException(EditGraph.DIAGONAL,
				"Can not create diagonal arc on the vertex " + this.endVertex);
		}
	}

	public Vertex<E> getBeginVertex()
	{
		try
		{
			return getEditGraph().getVertex(getEndVertex().getI() - 1,
				getEndVertex().getJ() - 1);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public boolean isMatch()
	{
		try
		{
			return getEditGraph().isMatch(this);
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}
}
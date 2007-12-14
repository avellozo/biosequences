/*
 * Created on 17/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public interface Extender<E extends EditGraph<E, ? extends Extender>>
{
	public int getWeightExtended(EditGraphSegment range)
			throws EGInvalidVertexesOfExtensionException;

	public boolean existsExtendedArc(EditGraphSegment range);

	public ArcExtended getExtendedArc(EditGraphSegment range)
			throws EGInvalidVertexesOfExtensionException;

}
// public Arc getExtendedArc(Vertex beginVertex)
// throws EGInvalidArcException
// {
// return new ExtendedArc(beginVertex);
// }
//
// protected boolean existsExtendedArc(Vertex beginVertex)
// {
// return (isExtended() && (beginVertex != null)
// && beginVertex.dominates(this) && !beginVertex.equals(this) && (beginVertex
// .getEditGraph() == getEditGraph()));
// }
//
// ArcBasic only exists if there is the vertexEnd
// public class ExtendedArc extends ArcBasic
// {
// Vertex beginVertex;
// EGPath optimumPath = null;
//
// public ExtendedArc(Vertex beginVertex) throws EGInvalidArcException
// {
// super(Arc.EXTENDED, 0);
// if (!existsExtendedArc(beginVertex))
// {
// throw new EGInvalidArcException(Arc.EXTENDED,
// "Can not create extended arc on the vertex "
// + VertexBasic.this.toString() + " form vertex "
// + beginVertex);
// }
// this.beginVertex = beginVertex;
// }
//
// public Vertex getBeginVertex()
// {
// return beginVertex;
// }
//
// public Vertex getBeginVertexExtended()
// {
// try
// {
// return getEditGraph().getExtendedGraph().getVertex(
// getRowsQtty() - 1 - getEndVertex().getI(),
// getBeginVertex().getJ());
// }
// catch (EGInvalidVertexException e)
// {
// e.printStackTrace();
// throw new RuntimeException("getBeginVertexExtended");
// }
// }
//
// public Vertex getEndVertexExtended()
// {
// try
// {
// return getEditGraph().getExtendedGraph().getVertex(
// getRowsQtty() - 1 - getBeginVertex().getI(),
// getEndVertex().getJ());
// }
// catch (EGInvalidVertexException e)
// {
// e.printStackTrace();
// throw new RuntimeException("getEndVertexExtended");
// }
// }
//
// public EGPath getOptimumPath()
// {
// if (optimumPath == null)
// {
// try
// {
// optimumPath = getEditGraph().getExtendedGraph()
// .getOptimumPath(getBeginVertexExtended(),
// getEndVertexExtended(), false);
// }
// catch (EGInvalidVertexException e)
// {
// e.printStackTrace();
// throw new RuntimeException("getOptimumPath");
// }
// }
// return optimumPath;
// }
//
// public int getWeight()
// {
// return (weight = getOptimumPath().getScore());
// }
// }

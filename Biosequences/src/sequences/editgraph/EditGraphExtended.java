/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto F. Vellozo
 */
public interface EditGraphExtended extends EditGraph
{

	public boolean existsExtendedArc(VertexRange vertexRange);

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex;

}
/*
 * Created on 09/10/2007
 */
package sequences.editgraph.arcs.factories;

import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface ArcExtendedFactory
{

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex;

	public boolean canCreateExtendedArc(VertexRange vertexRange);

}

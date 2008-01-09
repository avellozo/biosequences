package sequences.dup;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedDup<E extends EditGraph<E, ? extends ExtenderDup>> extends ArcExtended
{

	public ArcExtendedDup(EditGraphSegment range, int weight) throws ExceptionInvalidVertex, EGInvalidVertexesOfExtensionException
	{
		super(range, weight);
	}

}

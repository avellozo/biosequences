package sequences.dup;

import sequences.editgraph.ArcExtended;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphSegment;

public class ArcExtendedDup<E extends EditGraph<E, ? extends ExtenderDup>> extends ArcExtended
{

	public ArcExtendedDup(EditGraphSegment range, int weight) throws ExceptionInvalidVertex, EGInvalidVertexesOfExtensionException
	{
		super(range, weight);
	}

}

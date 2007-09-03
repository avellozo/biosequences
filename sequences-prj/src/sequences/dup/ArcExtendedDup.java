package sequences.dup;

import sequences.editgraph.ArcExtended;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;

public class ArcExtendedDup<E extends EditGraph<E, ? extends ExtenderDup<E>>> extends ArcExtended<E>
{

	public ArcExtendedDup(VertexRange<E> range, int weight) throws EGInvalidVertexException, EGInvalidRangeException
	{
		super(range, weight);
	}

}

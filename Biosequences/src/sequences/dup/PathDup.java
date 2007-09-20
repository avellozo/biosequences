package sequences.dup;

import sequences.editgraph.EGGeneralException;
import sequences.editgraph.EGInternalException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.VertexRange;

public class PathDup<E extends EditGraph<E, ? extends ExtenderDup<E>>> extends OptimumPathImpl<E>
{

	protected ExtenderDup	extender;

	public PathDup(VertexRange<E> range, boolean local) throws EGInvalidVertexException, EGGeneralException
	{
		super(range, local);
		if (!getEditGraph().isExtended())
		{
			throw new EGGeneralException("Edit graph must be extended to calculate a dup path.");
		}
		extender = getEditGraph().getExtender();
		finishTime();
	}

	public ExtenderDup getExtender()
	{
		return extender;
	}

}

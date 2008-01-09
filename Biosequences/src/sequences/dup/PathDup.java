package sequences.dup;

import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionGeneralEG;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class PathDup<E extends EditGraph<E, ? extends ExtenderDup>> extends OptimumPathImpl
{

	protected ExtenderDup	extender;

	public PathDup(EditGraphSegment range, boolean local) throws ExceptionInvalidVertex, ExceptionGeneralEG
	{
		super(range, local);
		if (!getEditGraph().isExtended())
		{
			throw new ExceptionGeneralEG("Edit graph must be extended to calculate a dup path.");
		}
		extender = getEditGraph().getExtender();
		finishTime();
	}

	public ExtenderDup getExtender()
	{
		return extender;
	}

}

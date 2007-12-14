package sequences.dup;

import sequences.editgraph.ExceptionGeneralEG;
import sequences.editgraph.ExceptionInternalEG;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.EditGraphSegment;

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

package sequences.bim;

import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.VertexRange;

public class PathBim<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		extends OptimumPathImpl<E>
{

	protected int							inversionPenalty;
	protected ExtenderUsingEGInvertedRows	extender;

	public PathBim(VertexRange<E> range, boolean local)
			throws EGInvalidEditGraphException, EGInvalidRangeException
	{
		super(range, local);
		if (!getEditGraph().isExtended())
		{
			throw new EGInvalidEditGraphException("Edit graph must be extended to calculate a bim path.");
		}
		extender = getEditGraph().getExtender();
		inversionPenalty = extender.getExtensionPenalty();
		finishTime();
	}

	public ExtenderUsingEGInvertedRows getExtender()
	{
		return extender;
	}

	public int getInversionPenalty()
	{
		return inversionPenalty;
	}

}

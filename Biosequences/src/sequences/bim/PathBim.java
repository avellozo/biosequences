package sequences.bim;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class PathBim<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		extends OptimumPathImpl
{

	protected int							inversionPenalty;
	protected ExtenderUsingEGInvertedRows	extender;

	public PathBim(EditGraphSegment range, boolean local)
			throws ExceptionInvalidEditGraph, EGInvalidVertexesOfExtensionException
	{
		super(range, local);
		if (!getEditGraph().isExtended())
		{
			throw new ExceptionInvalidEditGraph("Edit graph must be extended to calculate a bim path.");
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

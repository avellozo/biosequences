package sequences.dup;

import sequences.editgraph.ExceptionGeneralEG;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.EditGraphSegment;

public class PathDupDummyFactory<E extends EditGraph<E, ? extends ExtenderDup>>
		implements OptimumPathFactory<E, PathDupDummy>
{

	public PathDupDummy createPath(EditGraphSegment range, boolean local)
			throws ExceptionInvalidVertex, ExceptionGeneralEG
	{
		return new PathDupDummy(range, local);
	}

	public String getName()
	{
		return "Dup Dummy";
	}

}

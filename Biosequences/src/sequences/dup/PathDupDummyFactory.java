package sequences.dup;

import sequences.editgraph.EGGeneralException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.VertexRange;

public class PathDupDummyFactory<E extends EditGraph<E, ? extends ExtenderDup<E>>>
		implements OptimumPathFactory<E, PathDupDummy<E>>
{

	public PathDupDummy<E> createPath(VertexRange<E> range, boolean local)
			throws EGInvalidVertexException, EGGeneralException
	{
		return new PathDupDummy<E>(range, local);
	}

	public String getName()
	{
		return "Dup Dummy";
	}

}

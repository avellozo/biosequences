package sequences.bim;

import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.VertexRange;

public class PathBimDummyFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimDummy<E>>
{

	public PathBimDummy<E> createPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return new PathBimDummy<E>(range, local);
	}

	public String getName()
	{
		return "Bim Dummy";
	}

}

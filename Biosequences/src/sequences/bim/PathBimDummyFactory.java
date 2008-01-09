package sequences.bim;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class PathBimDummyFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathMethod<E, PathBimDummy>
{

	public PathBimDummy createPath(EditGraphSegment range, boolean local)
			throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
	{
		return new PathBimDummy(range, local);
	}

	public String getName()
	{
		return "Bim Dummy";
	}

}

package sequences.bim;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.EditGraphSegment;

public class PathBimDummyFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimDummy>
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

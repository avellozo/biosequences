package sequences.bim.n4;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDP;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.EditGraphSegment;

public class PathBimN4Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimDP>
{

	public PathBimDP createPath(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
	{
		return new PathBimDP(range, local);
	}

	public String getName()
	{
		return "BIM n^4";
	}

}

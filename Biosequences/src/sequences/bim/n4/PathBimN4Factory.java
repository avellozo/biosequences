package sequences.bim.n4;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDP;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class PathBimN4Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathMethod<E, PathBimDP>
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

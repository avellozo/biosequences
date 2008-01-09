package sequences.bim.n3lgn;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class PathBimN3lgnFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathMethod<E, PathBimN3lgn>
{

	public PathBimN3lgn createPath(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
	{
		return new PathBimN3lgn(range, local);
	}

	public String getName()
	{
		return "N^3 log n";
	}

}

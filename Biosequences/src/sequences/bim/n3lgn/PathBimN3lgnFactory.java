package sequences.bim.n3lgn;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.EditGraphSegment;

public class PathBimN3lgnFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimN3lgn>
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

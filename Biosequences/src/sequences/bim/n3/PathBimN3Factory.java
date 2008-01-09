package sequences.bim.n3;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.EditGraphSegment;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class PathBimN3Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathMethod<E, PathBimN3>
{

	public PathBimN3 createPath(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
	{
		return new PathBimN3(range, local);
	}

	public String getName()
	{
		return "N^3 psi";
	}

}

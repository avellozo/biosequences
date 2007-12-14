package sequences.bim.n3;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.EditGraphSegment;

public class PathBimN3Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimN3>
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

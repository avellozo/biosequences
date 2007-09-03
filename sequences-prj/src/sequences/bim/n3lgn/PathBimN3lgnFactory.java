package sequences.bim.n3lgn;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.VertexRange;

public class PathBimN3lgnFactory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimN3lgn<E>>
{

	public PathBimN3lgn<E> createPath(VertexRange<E> range, boolean local) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return new PathBimN3lgn<E>(range, local);
	}

	public String getName()
	{
		return "N^3 log n";
	}

}

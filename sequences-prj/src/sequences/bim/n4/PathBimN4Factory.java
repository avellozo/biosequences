package sequences.bim.n4;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDP;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.VertexRange;

public class PathBimN4Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimDP<E>>
{

	public PathBimDP<E> createPath(VertexRange<E> range, boolean local) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return new PathBimDP<E>(range, local);
	}

	public String getName()
	{
		return "BIM n^4";
	}

}

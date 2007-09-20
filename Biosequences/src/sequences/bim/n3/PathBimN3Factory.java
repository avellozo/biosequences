package sequences.bim.n3;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.VertexRange;

public class PathBimN3Factory<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		implements OptimumPathFactory<E, PathBimN3<E>>
{

	public PathBimN3<E> createPath(VertexRange<E> range, boolean local) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		return new PathBimN3<E>(range, local);
	}

	public String getName()
	{
		return "N^3 psi";
	}

}

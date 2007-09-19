/*
 * Created on 09/08/2006
 */
package sequences.editgraph;

public class OptimumPathDummy <E extends EditGraph<E, ? extends Extender<E>>>
extends OptimumPathImpl<E>
{

	public OptimumPathDummy(VertexRange<E> range, boolean local) throws EGInvalidRangeException
	{
		super(range, local);
	}

}

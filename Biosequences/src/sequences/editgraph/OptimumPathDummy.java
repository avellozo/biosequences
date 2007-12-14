/*
 * Created on 09/08/2006
 */
package sequences.editgraph;

public class OptimumPathDummy <E extends EditGraph<E, ? extends Extender>>
extends OptimumPathImpl
{

	public OptimumPathDummy(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException
	{
		super(range, local);
	}

}

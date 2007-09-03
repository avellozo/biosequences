/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;

public class ExtenderDupt<E extends EditGraph<E, ? extends ExtenderDup<E>>> extends ExtenderDup<E>
{

	public ExtenderDupt(boolean onlyInTheSeq)
	{
		super(onlyInTheSeq, true);
	}

	@Override
	public int getWeightExtended(VertexRange<E> range) throws EGInvalidRangeException
	{
		// TODO Auto-generated method stub
		return super.getWeightExtended(range);
	}

}

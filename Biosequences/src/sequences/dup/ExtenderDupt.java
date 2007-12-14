/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphSegment;

public class ExtenderDupt<E extends EditGraph<E, ? extends ExtenderDup>> extends ExtenderDup
{

	public ExtenderDupt(boolean onlyInTheSeq)
	{
		super(onlyInTheSeq, true);
	}

	@Override
	public int getWeightExtended(EditGraphSegment range) throws EGInvalidVertexesOfExtensionException
	{
		// TODO Auto-generated method stub
		return super.getWeightExtended(range);
	}

}

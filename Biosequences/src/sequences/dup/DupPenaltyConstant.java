/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.VertexRange;

public class DupPenaltyConstant implements DupPenaltyCalculator
{
	int	penalty;

	public DupPenaltyConstant(int penalty)
	{
		this.penalty = penalty;
	}

	public int getExtensionPenalty(VertexRange range, boolean seqBaseInTheSameSeq, boolean tandem)
	{
		return penalty;
	}

}

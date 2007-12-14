/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.EditGraphSegment;

public class DupPenaltyConstant implements DupPenaltyCalculator
{
	int penalty;

	/**
	 * @param penalty
	 */
	public DupPenaltyConstant(int penalty)
	{
		super();
		this.penalty = penalty;
	}

	public int getExtensionPenalty(EditGraphSegment range, boolean seqBaseInTheSameSeq, int beginPosSeqBase,
			int endPosSeqBase)
	{
		return penalty;
	}

}

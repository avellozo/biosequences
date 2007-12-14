/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.EditGraphSegment;

public interface DupPenaltyCalculator
{
	public int getExtensionPenalty(EditGraphSegment range, boolean seqBaseInTheSameSeq, int beginPosSeqBase, int endPosSeqBase);
}

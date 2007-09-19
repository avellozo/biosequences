/*
 * Created on 02/04/2007
 */
package sequences.dup;

import sequences.editgraph.VertexRange;

public interface DupPenaltyCalculator
{
	public int getExtensionPenalty(VertexRange range, boolean seqBaseInTheSameSeq, int beginPosSeqBase, int endPosSeqBase);
}

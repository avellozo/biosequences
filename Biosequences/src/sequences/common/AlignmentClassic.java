/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.ArcFactorySimpleSequences;
import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.ExceptionInvalidEditGraph;

public class AlignmentClassic extends AlignmentImpl
{
	Sequence	seq1, seq2;

	public AlignmentClassic(Sequence seq1, Sequence seq2, int match, int mismatch, int gap, boolean local)
			throws ExceptionInvalidEditGraph
	{
		super(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new ArcFactorySimpleSequences(seq1, seq2, match,
			mismatch, gap)), new OptimumPathMethodClassic(local));
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	public Sequence getSeq1()
	{
		return seq1;
	}

	public Sequence getSeq2()
	{
		return seq2;
	}

}

/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.arcs.factories.ArcDiagonalFactorySequences;
import sequences.editgraph.arcs.factories.ArcExtendedFactoryNothing;
import sequences.editgraph.arcs.factories.GapConstantFactory;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class AlignmentClassic extends AlignmentImpl<EditGraphBasic>
{
	Sequence	seq1, seq2;

	// type is global, local or semiglobal
	public AlignmentClassic(Sequence seq1, Sequence seq2, int match, int mismatch, int gap, byte type)
			throws ExceptionInvalidEditGraph
	{
		super(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new GapConstantFactory(gap, 0),
			new ArcDiagonalFactorySequences(seq1, seq2, match, mismatch), new ArcExtendedFactoryNothing()),
			new MethodClassic(type));
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	public AlignmentClassic(Sequence seq1, Sequence seq2, int match, int mismatch, int gap, int gapOpen, byte type)
			throws ExceptionInvalidEditGraph
	{
		super(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new GapConstantFactory(gap, gapOpen),
			new ArcDiagonalFactorySequences(seq1, seq2, match, mismatch), new ArcExtendedFactoryNothing()),
			new MethodClassicWithGapOpen(type));
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

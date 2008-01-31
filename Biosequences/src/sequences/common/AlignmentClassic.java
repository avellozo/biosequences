/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.arcs.factories.ArcDiagonalFactoryNothing;
import sequences.editgraph.arcs.factories.ArcDiagonalFactorySequences;
import sequences.editgraph.arcs.factories.ArcExtendedFactoryNothing;
import sequences.editgraph.arcs.factories.ArcHorizontalFactoryConstant;
import sequences.editgraph.arcs.factories.ArcHorizontalFactoryNothing;
import sequences.editgraph.arcs.factories.ArcVerticalFactoryConstant;
import sequences.editgraph.arcs.factories.ArcVerticalFactoryNothing;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public class AlignmentClassic extends AlignmentImpl
{
	Sequence	seq1, seq2;

	//type is global, local or semiglobal
	public AlignmentClassic(Sequence seq1, Sequence seq2, int match, int mismatch, int gap, char type)
			throws ExceptionInvalidEditGraph
	{
		super(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new ArcHorizontalFactoryConstant(gap),
			new ArcVerticalFactoryConstant(gap), new ArcDiagonalFactorySequences(seq1, seq2, match, mismatch),
			new ArcExtendedFactoryNothing()), new MethodClassic(type));
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	public AlignmentClassic(Sequence seq1, Sequence seq2, int match, int mismatch, int gap, int gapOpen, char type)
			throws ExceptionInvalidEditGraph
	{
		super(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new ArcHorizontalFactoryNothing(),
			new ArcVerticalFactoryNothing(), new ArcDiagonalFactorySequences(seq1, seq2, match, mismatch),
			new ArcExtendedFactoryForGapOpen(gapOpen, new EditGraphBasic(seq1.getLength(), seq2.getLength(),
				new ArcHorizontalFactoryConstant(gap), new ArcVerticalFactoryConstant(gap),
				new ArcDiagonalFactoryNothing(), new ArcExtendedFactoryNothing()))), new MethodClassicWithGapOpen(type));
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

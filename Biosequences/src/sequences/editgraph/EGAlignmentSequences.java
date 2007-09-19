/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import sequences.common.Sequence;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public class EGAlignmentSequences<E extends EGAlignmentSequences<E, X>, X extends Extender<E>>
		extends EGAlignment<E, X>
{
	protected Sequence	seq1, seq2;

	public EGAlignmentSequences(Sequence seq1, Sequence seq2, int match,
			int mismatch, int gap,
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory,
			X extender)
	{
		super(match, mismatch, gap, pathFactory, extender);
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	public int getRowMax()
	{
		return seq1.getLength();
	}

	public int getColMax()
	{
		return seq2.getLength();
	}

	public boolean isMatch(int row, int col) throws EGInvalidArcException
	{
		if (!existsDiagonalArc(row, col))
		{
			throw new EGInvalidArcException(EditGraph.DIAGONAL,
				"Doesn't exist diagonal arc on vertex (" + row + ',' + col
					+ ')');
		}
		return (getSeq1().getLetter(row) == getSeq2().getLetter(col));
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

/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import sequences.common.Sequence;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public class WeighterArcsSimpleSequences extends WeighterArcsSimple
{
	protected Sequence	seq1, seq2;

	public WeighterArcsSimpleSequences(Sequence seq1, Sequence seq2, int match, int mismatch, int gap)
	{
		super(match, mismatch, gap);
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	//	public int getRowMax()
	//	{
	//		return seq1.getLength();
	//	}
	//
	//	public int getColMax()
	//	{
	//		return seq2.getLength();
	//	}
	//
	public Sequence getSeq1()
	{
		return seq1;
	}

	public Sequence getSeq2()
	{
		return seq2;
	}

	@Override
	protected boolean isMatch(int row, int col)
	{

		return (getSeq1().getLetter(row) == getSeq2().getLetter(col));
	}
}

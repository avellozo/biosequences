/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import sequences.common.Sequence;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public class ArcFactorySimpleSequences extends ArcFactorySimple
{
	protected Sequence	seq1, seq2;

	// Sequência 1 nas linhas e seq 2 nas colunas
	public ArcFactorySimpleSequences(Sequence seq1, Sequence seq2, int match, int mismatch, int gap)
	{
		super(match, mismatch, gap);
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

	@Override
	public boolean isMatch(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		return (getSeq1().getLetter(i) == getSeq2().getLetter(j));
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		return ((i <= getSeq1().getLength()) && (j <= getSeq2().getLength()) && (i > 0) && (j > 0));

	}
}

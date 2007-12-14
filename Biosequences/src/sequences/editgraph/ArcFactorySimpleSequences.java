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

	public boolean existsDiagonalArc(Vertex endVertex)
	{
		return ((endVertex.getRow() <= getSeq1().getLength()) && (endVertex.getCol() <= getSeq2().getLength())
			&& (endVertex.getRow() > 0) && (endVertex.getCol() > 0));
	}

	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return (getSeq1().getLetter(endVertex.getRow()) == getSeq2().getLetter(endVertex.getCol()));
	}
}

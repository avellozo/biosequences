/*
 * Created on 08/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.LinkedList;
import java.util.List;

import sequences.common.ScoringMatrix;
import sequences.common.Sequence;
import sequences.editgraph.ArcDiagonalFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcDiagonalFactoryScoringMatrix implements ArcDiagonalFactory
{

	protected Sequence	seq1, seq2;
	ScoringMatrix		scoringMatrix;
	int					threshold;

	// Sequência 1 nas linhas e seq 2 nas colunas
	//threshold: value greater or equal than threshold is a match
	public ArcDiagonalFactoryScoringMatrix(Sequence seq1, Sequence seq2, ScoringMatrix scoringMatrix, int threshold)
	{
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.scoringMatrix = scoringMatrix;
		this.threshold = threshold;
	}

	public boolean canCreateDiagonalArc(Vertex endVertex)
	{
		return (endVertex != null && (canCreateDiagonalArc(endVertex.getRow(), endVertex.getCol())));
	}

	public boolean canCreateDiagonalArc(int i, int j)
	{
		return ((i <= getSeq1().getLength()) && (j <= getSeq2().getLength()) && (i > 0) && (j > 0));
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		int i, j;
		ArcDiagonal arc;
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					arc = getDiagonalArc(eg.getVertex(i, j));
					if (arc.getWeight() != 0)
					{
						list.addLast(arc);
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return list;
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		int w = getWeightDiagonalArc(endVertex.getRow(), endVertex.getCol());
		return new ArcDiagonal(endVertex, w, w >= threshold);
	}

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		return scoringMatrix.getScore(getSeq1().getLetter(i), getSeq2().getLetter(j));
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

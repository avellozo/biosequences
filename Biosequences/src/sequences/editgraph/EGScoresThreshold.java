/*
 * Created on 09/12/2004
 */
package sequences.editgraph;

import java.util.LinkedList;
import java.util.List;

import sequences.common.MatchesWeight;
import sequences.common.Score;

/**
 * @author Augusto
 */
public class EGScoresThreshold<E extends EGScoresThreshold<E, X>, X extends Extender<E>>
		extends EditGraphImpl<E, X>
{
	LinkedList<ArcDiagonal<E>>	positiveArcs	= new LinkedList<ArcDiagonal<E>>();
	int							threshold, match, mismatch, gap;
	int							rows, cols;
	ArcDiagonal[][]				diagonals;
	MatchesWeight				matchesWeight;
	boolean						invertedRows;

	public EGScoresThreshold(List<Score> scores, int threshold, int match,
			int mismatch, int gap, int rows, int cols,
			MatchesWeight matchesWeight, boolean invertedRows,
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory,
			X extender) throws EGInvalidArcException, EGInvalidVertexException
	{
		super(pathFactory, extender);
		this.threshold = threshold;
		this.match = match;
		this.mismatch = mismatch;
		this.gap = gap;
		this.rows = rows;
		this.cols = cols;
		this.matchesWeight = matchesWeight;
		this.invertedRows = invertedRows;
		this.positiveArcs = buildPositiveArcsList(scores);
		this.diagonals = null;
	}

	@Override
	public LinkedList<ArcDiagonal<E>> getMatches()
	{
		return positiveArcs;
	}

	public int getRowMax()
	{
		return rows - 1;
	}

	public int getColMax()
	{
		return cols - 1;
	}

	protected int getWeightHorizontal(int row, int col)
	{
		return gap;
	}

	protected int getWeightVertical(int row, int col)
	{
		return gap;
	}

	public boolean isInvertedRows()
	{
		return invertedRows;
	}

	protected int getWeightDiagonal(int row, int col)
	{
		ArcDiagonal<E> arc;
		try
		{
			arc = getDiagonalArc(getVertex(row, col));
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
		if (arc != null)
		{
			return arc.getWeight();
		}
		return mismatch;
	}

	public ArcDiagonal<E> getDiagonalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException
	{
		if (!isValidVertex(endVertex))
		{
			throw new EGInvalidVertexException(endVertex);
		}
		if (diagonals == null)
		{
			diagonals = new ArcDiagonal[getRowMax() + 1][getColMax() + 1];
			for (ArcDiagonal<E> arc : getMatches())
			{
				diagonals[arc.getEndVertex().getI()][arc.getEndVertex().getJ()] = arc;
			}
		}
		ArcDiagonal<E> arc = (ArcDiagonal<E>) diagonals[endVertex.getI()][endVertex
			.getJ()];
		if (arc == null)
		{
			arc = new ArcDiagonal<E>(endVertex, getMismatchWeight());
		}
		return arc;
	}

	protected LinkedList<ArcDiagonal<E>> buildPositiveArcsList(
			List<Score> scores) throws EGInvalidArcException,
			EGInvalidVertexException
	{
		int i, j, w;
		LinkedList<ArcDiagonal<E>> positiveArcs = new LinkedList<ArcDiagonal<E>>();
		for (Score score : scores)
		{
			w = transformScoreToWeight(score);
			if (w > 0)
			{
				positiveArcs.addLast(new ArcDiagonal<E>(getVertex(
					transformRow(score.getRow()), score.getCol() + 1), w));
			}
		}
		return positiveArcs;
	}

	public int transformRow(int rowOfScore)
	{
		if (isInvertedRows())
		{
			return rows - rowOfScore; // because rowOfScore begins at 0
		}
		return rowOfScore + 1;
	}

	protected int transformScoreToWeight(Score score)
	{
		return (score.getScore() >= threshold ? getMatchWeight(score)
			: getMismatchWeight());
	}

	protected int getMatchWeight(Score score)
	{
		if (matchesWeight != null)
		{
			return matchesWeight.getWeight(score.getScore());
		}
		return getMatchWeight();
	}

	protected int getMatchWeight()
	{
		return this.match;
	}

	public int getMismatchWeight()
	{
		return this.mismatch;
	}

	public boolean isMatch(ArcDiagonal<E> arc) throws EGInvalidArcException
	{
		validateArc(arc);
		return arc.getWeight() != getMismatchWeight();
	}
}
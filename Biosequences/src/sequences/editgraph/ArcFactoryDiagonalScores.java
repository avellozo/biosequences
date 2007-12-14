/*
 * Created on 12/12/2007
 */
package sequences.editgraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sequences.common.Score;

public class ArcFactoryDiagonalScores extends ArcFactorySimple
{

	int							scoreThresholdForMatch;
	List<Score>					scores;
	HashMap<String, Integer>	matches;

	public ArcFactoryDiagonalScores(List<Score> scores, int scoreThresholdForMatch, int match, int mismatch, int gap)
	{
		super(match, mismatch, gap);
		this.scoreThresholdForMatch = scoreThresholdForMatch;
		this.scores = scores;
	}

	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		if (matches == null)
		{
			buildMatches();
		}
		return (matches.get(endVertex.toString()) != null);
	}

	protected void buildMatches()
	{
		matches = new HashMap<String, Integer>(scores.size(), 1);
		for (Score score : scores)
		{
			if (score.getScore() >= scoreThresholdForMatch)
			{
				matches.put(new Vertex(score.getRow() + 1, score.getCol() + 1).toString(),
					transformScoreToWeight(score));
			}
		}
	}

	@Override
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		int iMin = eg.getRowMin();
		int iMax = eg.getRowMax();
		int jMin = eg.getColMin();
		int jMax = eg.getColMax();
		int i, j, w;
		LinkedList<ArcDiagonal> nonZeroArcs = new LinkedList<ArcDiagonal>();
		for (Score score : scores)
		{
			i = score.getRow() + 1;
			j = score.getCol() + 1;
			if ((i > iMin) && (i <= iMax) && (j > jMin) && (j <= jMax))
			{
				w = transformScoreToWeight(score);
				if (w != 0)
				{
					try
					{
						nonZeroArcs.addLast(new ArcDiagonal(eg.getVertex(i, j), w));
					}
					catch (ExceptionInvalidVertex e)
					{
						e.printStackTrace();
						throw new ExceptionInternalEG();
					}
				}
			}
		}
		return nonZeroArcs;
	}

	protected int transformScoreToWeight(Score score)
	{
		return (score.getScore() >= scoreThresholdForMatch ? match : mismatch);
	}

	public boolean existsDiagonalArc(Vertex endVertex)
	{
		return true;
	}

}

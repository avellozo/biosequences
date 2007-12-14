/*
 * Created on 13/12/2007
 */
package sequences.editgraph;

import java.util.List;

import sequences.common.MatchesWeight;
import sequences.common.Score;

public class ArcFactoryDiagonalScoresWithScale extends ArcFactoryDiagonalScores
{
	MatchesWeight	matchesWeightScale;

	public ArcFactoryDiagonalScoresWithScale(List<Score> scores, int scoreThresholdForMatch,
			MatchesWeight matchesWeightScale, int mismatch, int gap)
	{
		super(scores, scoreThresholdForMatch, 0, mismatch, gap);
		this.matchesWeightScale = matchesWeightScale;
	}

	@Override
	protected int transformScoreToWeight(Score score)
	{
		return ((score.getScore() >= scoreThresholdForMatch) ? matchesWeightScale.getWeight(score.getScore())
			: mismatch);
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcDiagonal(endVertex, getWeightDiagonal(endVertex));
	}

	public int getWeightDiagonal(Vertex endVertex)
	{
		if (matches == null)
		{
			buildMatches();
		}
		Integer matchVal = matches.get(endVertex.toString());
		return ((matchVal != null) ? matchVal : mismatch);
	}
}

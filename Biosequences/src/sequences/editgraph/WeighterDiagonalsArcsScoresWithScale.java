/*
 * Created on 13/12/2007
 */
package sequences.editgraph;

import java.util.List;

import sequences.common.MatchesWeight;
import sequences.common.Score;

public class WeighterDiagonalsArcsScoresWithScale extends WeighterDiagonalArcsScores
{
	MatchesWeight	matchesWeightScale;

	public WeighterDiagonalsArcsScoresWithScale(List<Score> scores, int scoreThresholdForMatch,
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

	@Override
	public int getWeightDiagonal(int row, int col)
	{
		if (matches == null)
		{
			buildMatches();
		}
		Integer matchVal = matches.get(transformRowColToString(row, col));
		return ((matchVal != null) ? matchVal : mismatch);
	}
}

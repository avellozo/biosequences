/*
 * Created on 13/12/2007
 */
package sequences.editgraph;

import java.util.List;

import sequences.common.MatchesWeight;
import sequences.common.Score;

public class ArcDiagonalFactoryScoresWithScale extends ArcDiagonalFactoryScores
{
	MatchesWeight	matchesWeightScale;

	public ArcDiagonalFactoryScoresWithScale(List<Score> scores, int scoreThresholdForMatch,
			MatchesWeight matchesWeightScale, int mismatch)
	{
		super(scores, scoreThresholdForMatch, 0, mismatch);
		this.matchesWeightScale = matchesWeightScale;
	}

	@Override
	protected int transformScoreToWeight(Score score)
	{
		return ((score.getScore() >= scoreThresholdForMatch) ? matchesWeightScale.getWeight(score.getScore())
			: mismatch);
	}

	//	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	//	{
	//		if (!canCreateDiagonalArc(endVertex))
	//		{
	//			throw new ExceptionInvalidVertex(endVertex);
	//		}
	//		if (matches == null)
	//		{
	//			buildMatches();
	//		}
	//		Integer matchVal = matches.get(endVertex.toString());
	//		return new ArcDiagonal(endVertex, ((matchVal != null) ? matchVal : mismatch), (matchVal != null));
	//	}
	//
	@Override
	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex
	{
		if (!canCreateDiagonalArc(i, j))
		{
			throw new ExceptionInvalidVertex(i, j);
		}
		if (matches == null)
		{
			buildMatches();
		}
		Vertex endVertex = new Vertex(i, j);
		Integer matchVal = matches.get(endVertex.toString());
		return ((matchVal != null) ? matchVal : mismatch);
		//		return new ArcDiagonal(endVertex, ((matchVal != null) ? matchVal : mismatch), (matchVal != null));
	}

}

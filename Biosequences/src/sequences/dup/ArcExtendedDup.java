package sequences.dup;

import sequences.editgraph.OptimumPath;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtendedOverEG;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedDup extends ArcExtendedOverEG
{
	//	public static final char	SET_A		= 'A';
	//	public static final char	SET_B		= 'B';
	//	public static final char	SET_C		= 'C';
	//	public static final char	SET_D		= 'D';
	//	public static final char	SET_UNKNOWN	= 'U';
	//
	//	char						set;
	boolean					inSameSequence;
	DupPenaltyCalculator	duplicationPenalty;

	public ArcExtendedDup(VertexRange vertexRange, OptimumPath optimumPath, DupPenaltyCalculator duplicationPenalty,
			boolean inSameSequence) throws ExceptionInvalidVertex
	{
		super(vertexRange, optimumPath, duplicationPenalty.getExtensionPenalty(vertexRange, inSameSequence,
			optimumPath.getLast().getEndVertex().getCol()));
		this.inSameSequence = inSameSequence;
		this.duplicationPenalty = duplicationPenalty;
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		if (((cols == 1) || (rows == 1)) && (cols != rows))
		{
			throw new ExceptionInvalidVertex(vertexRange.toString());
		}
	}

	public boolean isInSameSequence()
	{
		return inSameSequence;
	}

	public DupPenaltyCalculator getDuplicationPenalty()
	{
		return duplicationPenalty;
	}

}

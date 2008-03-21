/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.common.backtrack.BackTrack;
import sequences.common.backtrack.BackTrackBasic;
import sequences.common.backtrack.BackTrackGapSetMatrixes;
import sequences.editgraph.EditGraphWithGapSet;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class MethodClassicWithGapOpen extends MethodClassic implements OptimumPathMethod
{
	public MethodClassicWithGapOpen(byte type)
	{
		super(type);
	}

	public OptimumPath createPath(VertexRange vertexRange, EditGraphWithGapSet eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();

		BackTrack bt = new BackTrackBasic(iMin, jMin, iMax, jMax, type, new BackTrackGapSetMatrixes(iMin, jMin, iMax,
			jMax), null, null);

		int i, j;

		try
		{
			i = iMin;
			j = jMin;
			bt.setBeginPath(i, j, 0);
			// Calcula a linha iMin
			for (j = jMin + 1; j <= jMax; j++)
			{
				bt.updateHorizontal(i, j, eg);
				bt.updateGapSetHorizontal(i, j, eg);
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
				for (j = jMin; j <= jMax; j++)
				{
					if (j != jMin)
					{
						bt.updateDiagonal(i, j, eg);
						bt.updateHorizontal(i, j, eg);
						bt.updateGapSetHorizontal(i, j, eg);
					}
					bt.updateVertical(i, j, eg);
					bt.updateGapSetVertical(i, j, eg);
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
		try
		{
			return bt.getOptimumPath(eg);
		}
		catch (ExceptionInvalidBackTrack e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
	}

	public String getName()
	{
		return "Classic Alignment with gap open penalty";
	}

}

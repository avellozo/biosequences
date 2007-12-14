package sequences.dup;

import sequences.editgraph.ArcExtended;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Extender;
import sequences.editgraph.VertexRange;

public class ExtenderDup<E extends EditGraph<E, ? extends ExtenderDup>> implements Extender
{
	DupPenaltyCalculator	dupPenalty;
	boolean					onlyRetroactive;
	EditGraph				egTxT, egSxT, egSxS;

	/**
	 * @param onlyRetroactive
	 * @param egTxT
	 * @param egSxT
	 * @param egSxS
	 */
	public ExtenderDup(boolean onlyRetroactive, EditGraph egTxT, EditGraph egSxT, EditGraph egSxS,
			DupPenaltyCalculator dupPenalty)
	{
		this.onlyRetroactive = onlyRetroactive;
		this.egTxT = egTxT;
		this.egSxT = egSxT;
		this.egSxS = egSxS;
		this.dupPenalty = dupPenalty;
	}

	public boolean existsExtendedArc(VertexRange range)
	{
		if (range == null)
		{
			return false;
		}
		int c = range.getCols();
		int r = range.getRows();
		return ((c == 1) || (r == 1)) && ((c != 1) || (r != 1));
	}

	public ArcExtended getExtendedArc(VertexRange range) throws EGInvalidRangeException
	{
		if (range == null)
		{
			throw new EGInvalidRangeException("Invalid range: null");
		}
		try
		{
			return new ArcExtendedDup(range, getWeightExtended(range));
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInvalidRangeException(range, "Invalid range");
		}
	}

	public DupPenaltyCalculator getDupPenalty()
	{
		return dupPenalty;
	}

	public int getWeightExtended(VertexRange range) throws EGInvalidRangeException
	{
		if (!existsExtendedArc(range))
		{
			throw new EGInvalidRangeException(range);
		}
		int cols = range.getEditGraph().getColMax()-range.getEditGraph().getColMin()+1;
		int rows = range.getEditGraph().getRowMax()-range.getEditGraph().getRowMin()+1;
		int rangej1 = range.getBeginVertex().getJ();
		int rangej2 = range.getEndVertex().getJ();
		if (range.getRows() == 1)
		{
			//Verify in egTxT
			int i=0 , j=0;
			int[][] M = new int[range.getCols()][cols];
			M[i][j]=0
		}
	}

}

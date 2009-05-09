/*
 * Created on 22/03/2008
 */
package sequences.dup;

import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.factories.ArcExtendedFactory;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtDupModel1Factory implements ArcExtendedFactory
{
	EditGraph				egSeq1xSeq1, egSeq1xSeq2, egSeq2xSeq1, egSeq2xSeq2;
	DupPenaltyCalculator	duplicationPenalty;
	boolean					searchForward;

	// seq1 é a seq das linhas
	// seq2 é a seq das colunas
	public ArcExtDupModel1Factory(EditGraph egSeq1xSeq1, EditGraph egSeq1xSeq2, EditGraph egSeq2xSeq1,
			EditGraph egSeq2xSeq2, DupPenaltyCalculator duplicationPenalty, boolean searchForward)
	{
		this.egSeq1xSeq1 = egSeq1xSeq1;
		this.egSeq1xSeq2 = egSeq1xSeq2;
		this.egSeq2xSeq1 = egSeq2xSeq1;
		this.egSeq2xSeq2 = egSeq2xSeq2;
		this.duplicationPenalty = duplicationPenalty;
		this.searchForward = searchForward;
	}

	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		if (cols == 1)
		{
			int beginColS1 = getEgSeq2xSeq1().getColMin();
			int endColS1 = getEgSeq2xSeq1().getColMax();
			int beginColS2 = getEgSeq2xSeq2().getColMin();
			int endColS2 = getEgSeq2xSeq2().getColMax();
			return (rows!=1);
		}
		else
		{
			int beginColS1 = getEgSeq2xSeq1().getColMin();
			int endColS1 = getEgSeq2xSeq1().getColMax();
			int beginColS2 = getEgSeq2xSeq2().getColMin();
			int endColS2 = getEgSeq2xSeq2().getColMax();
			return (rows==1);
		}
		return (((cols == 1) || (rows == 1)) && (cols != rows) && ());
	}

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		// TODO Auto-generated method stub
		return null;
	}

	public EditGraph getEgSeq1xSeq1()
	{
		return egSeq1xSeq1;
	}

	public EditGraph getEgSeq1xSeq2()
	{
		return egSeq1xSeq2;
	}

	public EditGraph getEgSeq2xSeq1()
	{
		return egSeq2xSeq1;
	}

	public EditGraph getEgSeq2xSeq2()
	{
		return egSeq2xSeq2;
	}

	public DupPenaltyCalculator getDuplicationPenalty()
	{
		return duplicationPenalty;
	}

	public boolean isSearchForward()
	{
		return searchForward;
	}

}

package sequences.bim;

import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Extender;
import sequences.editgraph.ExtenderUsingEG;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;

public class ExtenderUsingEGInvertedRows<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, EGInvertedRows>>, EGInvertedRows extends EditGraph<EGInvertedRows, ? extends Extender<EGInvertedRows>>>
		extends ExtenderUsingEG<E, EGInvertedRows>
{

	public ExtenderUsingEGInvertedRows(EGInvertedRows egInvertedRows,
			int extensionPenalty) throws EGInvalidEditGraphException
	{
		super(egInvertedRows, extensionPenalty);
	}

	// return row index of extender edit graph which corresponds to the row in
	// the extended graph
	@Override
	public int transformRow(int row)
	{
		return getEGExtender().getRowMax() - row + 1;
	}

	@Override
	public int transformCol(int col)
	{
		return col;
	}

	// return extended row
	@Override
	public int transformRowEGExtender(int rowEGInvertedRows)
	{
		return getEGExtender().getRowMax() - rowEGInvertedRows + 1;
	}

	@Override
	public int transformColEGExtender(int colEGInvertedRows)
	{
		return colEGInvertedRows;
	}

	// beginVertex, endVertex: vertices in extended edit graph
	@Override
	public VertexRange<EGInvertedRows> transformVertexRange(VertexRange<E> range)
			throws EGInvalidRangeException
	{
		if (range == null)
		{
			throw new EGInvalidRangeException("Invalid range: null");
		}
		try
		{
			Vertex<EGInvertedRows> bvEGExtender = getEGExtender().getVertex(
				transformRow(range.getEndVertex().getI() + 1),
				transformCol(range.getBeginVertex().getJ()));
			Vertex<EGInvertedRows> evEGExtender = getEGExtender().getVertex(
				transformRow(range.getBeginVertex().getI() + 1),
				transformCol(range.getEndVertex().getJ()));
			return new VertexRange<EGInvertedRows>(bvEGExtender, evEGExtender);
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new EGInvalidRangeException(range, "Invalid range to transform.");			
		}
	}

	// //beginVertex, endVertex: vertices in extended edit graph
	// public VertexRange<E>
	// transformVertexRangeExtender(VertexRange<EGExtender> range, E eg) throws
	// EGInvalidVertexException
	// {
	// if (range==null)
	// {
	// throw new EGInvalidVertexException("Invalid range: null");
	// }
	// Vertex<E> bv =
	// eg.getVertex(transformRowExtender(range.getEndVertex().getI()+1),
	// range.getBeginVertex().getJ());
	// Vertex<E> ev =
	// eg.getVertex(transformRowExtender(range.getBeginVertex().getI()+1),
	// range.getEndVertex().getJ());
	// return new VertexRange<E>(bv, ev);
	// }
	//
}

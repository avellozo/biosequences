package sequences.bim;

import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Extender;
import sequences.editgraph.ExtenderUsingEG;
import sequences.editgraph.Vertex;
import sequences.editgraph.EditGraphSegment;

public class ExtenderUsingEGInvertedRows<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, EGInvertedRows>>, EGInvertedRows extends EditGraph<EGInvertedRows, ? extends Extender<EGInvertedRows>>>
		extends ExtenderUsingEG<E, EGInvertedRows>
{

	public ExtenderUsingEGInvertedRows(EGInvertedRows egInvertedRows,
			int extensionPenalty) throws ExceptionInvalidEditGraph
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
	public EditGraphSegment<EGInvertedRows> transformVertexRange(EditGraphSegment range)
			throws EGInvalidVertexesOfExtensionException
	{
		if (range == null)
		{
			throw new EGInvalidVertexesOfExtensionException("Invalid range: null");
		}
		try
		{
			Vertex<EGInvertedRows> bvEGExtender = getEGExtender().getVertex(
				transformRow(range.getEndVertex().getI() + 1),
				transformCol(range.getBeginVertex().getJ()));
			Vertex<EGInvertedRows> evEGExtender = getEGExtender().getVertex(
				transformRow(range.getBeginVertex().getI() + 1),
				transformCol(range.getEndVertex().getJ()));
			return new EditGraphSegment<EGInvertedRows>(bvEGExtender, evEGExtender);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new EGInvalidVertexesOfExtensionException(range, "Invalid range to transform.");			
		}
	}

	// //beginVertex, endVertex: vertices in extended edit graph
	// public VertexRange
	// transformVertexRangeExtender(VertexRange<EGExtender> range, E eg) throws
	// EGInvalidVertexException
	// {
	// if (range==null)
	// {
	// throw new EGInvalidVertexException("Invalid range: null");
	// }
	// Vertex bv =
	// eg.getVertex(transformRowExtender(range.getEndVertex().getI()+1),
	// range.getBeginVertex().getJ());
	// Vertex ev =
	// eg.getVertex(transformRowExtender(range.getBeginVertex().getI()+1),
	// range.getEndVertex().getJ());
	// return new VertexRange(bv, ev);
	// }
	//
}

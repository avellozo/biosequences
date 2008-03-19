/*
 * Created on 19/03/2008
 */
package sequences.common;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntRange;

public class BackTrackExtension
{
	MatrixInt	extendedValue;
	MatrixInt	extendedRow;
	MatrixInt	extendedCol;

	public BackTrackExtension(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		extendedValue = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);
		extendedRow = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);
		extendedCol = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);

		if (rowBegin != 0 || colBegin != 0)
		{
			extendedValue = new MatrixIntRange(extendedValue, rowBegin, colBegin, rowEnd, colEnd);
			extendedRow = new MatrixIntRange(extendedRow, rowBegin, colBegin, rowEnd, colEnd);
			extendedCol = new MatrixIntRange(extendedCol, rowBegin, colBegin, rowEnd, colEnd);
		}
	}

	public ArcExtended getExtendedArc(Vertex endVertex, EditGraph eg) throws ExceptionInvalidVertex
	{
		int rowEnd = endVertex.getRow();
		int colEnd = endVertex.getCol();
		return eg.getExtendedArc(new VertexRange(new Vertex(extendedRow.getValue(rowEnd, colEnd), extendedCol.getValue(
			rowEnd, colEnd)), endVertex));
	}

	public void setExtension(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
	{
		extendedValue.setValue(rowEnd, colEnd, value);
		extendedRow.setValue(rowEnd, colEnd, rowBegin);
		extendedCol.setValue(rowEnd, colEnd, colBegin);
	}

}

/*
 * Created on 19/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.EditGraphWithGapSet;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcGapSetHorizontal;
import sequences.editgraph.arcs.ArcGapSetVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntRange;

public class BackTrackGapSetMatrixes implements BackTrackGapSet
{
	MatrixInt	extendedRow;
	MatrixInt	extendedCol;

	public BackTrackGapSetMatrixes(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		extendedRow = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);
		extendedCol = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);

		if (rowBegin != 0 || colBegin != 0)
		{
			extendedRow = new MatrixIntRange(extendedRow, rowBegin, colBegin, rowEnd, colEnd);
			extendedCol = new MatrixIntRange(extendedCol, rowBegin, colBegin, rowEnd, colEnd);
		}
	}

	public ArcGapSetHorizontal getArcGapHor(Vertex endVertex, EditGraphWithGapSet eg) throws ExceptionInvalidVertex
	{
		return eg.getGapSetHorizontalArc(extendedCol.getValue(endVertex.getRow(), endVertex.getCol()), endVertex);
	}

	public ArcGapSetVertical getArcGapVer(Vertex endVertex, EditGraphWithGapSet eg) throws ExceptionInvalidVertex
	{
		return eg.getGapSetVerticalArc(extendedRow.getValue(endVertex.getRow(), endVertex.getCol()), endVertex);
	}

	public void setGapHor(int colBegin, int rowEnd, int colEnd, int value)
	{
		extendedCol.setValue(rowEnd, colEnd, colBegin);
	}

	public void setGapVer(int rowBegin, int rowEnd, int col, int value)
	{
		extendedRow.setValue(rowEnd, col, rowBegin);
	}

}

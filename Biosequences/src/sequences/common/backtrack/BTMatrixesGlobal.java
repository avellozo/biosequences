/*
 * Created on 21/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Vertex;
import sequences.matrix.MatrixByteRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntRange;

public class BTMatrixesGlobal implements BackTrackMatrixes
{
	MatrixInt					m;
	protected MatrixByteRange	arcsType;

	public BTMatrixesGlobal(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		m = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);
		if (rowBegin != 0 || colBegin != 0)
		{
			m = new MatrixIntRange(m, rowBegin, colBegin, rowEnd, colEnd);
		}
		arcsType = new MatrixByteRange(rowBegin, colBegin, rowEnd, colEnd);
	}

	public MatrixByteRange getMatrixArcsType()
	{
		return arcsType;
	}

	public MatrixInt getMatrixValues()
	{
		return m;
	}

	public Vertex getOptimumVertex()
	{
		return new Vertex(m.getIndexEndRow(), m.getIndexEndRow());
	}

	public boolean isBeginOptimumPath(int row, int col)
	{
		return arcsType.getValue(row, col) == BackTrack.BEGIN_PATH;
	}

	public void updateValue(int row, int col, int value, byte type)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == BackTrack.EMPTY)
		{
			m.setValue(row, col, value);
			arcsType.setValue(row, col, type);
		}
	}

	public byte getArcType(int row, int col)
	{
		return arcsType.getValue(row, col);
	}

	public int getValue(int row, int col)
	{
		return m.getValue(row, col);
	}

	public void setBeginOptimumPath(int row, int col, int value)
	{
		m.setValue(row, col, value);
		arcsType.setValue(row, col, BackTrack.BEGIN_PATH);
	}

}

/*
 * Created on 21/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Vertex;
import sequences.matrix.MatrixByteRange;
import sequences.matrix.MatrixInt;

public interface BackTrackMatrixes
{

	public MatrixInt getMatrixValues();

	public MatrixByteRange getMatrixArcsType();

	public Vertex getOptimumVertex();

	public boolean isBeginOptimumPath(int row, int col);

	public void setBeginOptimumPath(int row, int col, int value);

	public void updateValue(int row, int col, int value, byte type);

	public int getValue(int row, int col);

	public byte getArcType(int row, int col);
}
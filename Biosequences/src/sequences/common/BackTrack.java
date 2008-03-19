/*
 * Created on 18/03/2008
 */
package sequences.common;

import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixInt;

public interface BackTrack
{

	public MatrixInt getMatrix();

	public void setVertical(int row, int col, int value) throws ExceptionInvalidVertex;

	public void setHorizontal(int row, int col, int value) throws ExceptionInvalidVertex;

	public void setDiagonal(int row, int col, int value) throws ExceptionInvalidVertex;

	public void setExtended(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
			throws ExceptionInvalidVertex;

	public void setJunction(int rowBegin, int colBegin, int rowEnd, int colEnd) throws ExceptionInvalidVertex;

	public OptimumPath getOptimumPath(EditGraph eg) throws ExceptionInvalidBackTrack;

}

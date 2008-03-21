/*
 * Created on 18/03/2008
 */
package sequences.common.backtrack;

import sequences.common.ExceptionInvalidBackTrack;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface BackTrack
{

	public static final byte	VERTICAL	= 'V';

	public static final byte	HORIZONTAL	= 'H';

	public static final byte	DIAGONAL	= 'D';

	public static final byte	EXTENDED	= 'E';

	public static final byte	JUNCTION	= 'J';	//arco sem peso que junta dois vértices

	public static final byte	GAP_HOR		= 'R';	//Gap estendido horizontal

	public static final byte	GAP_VER		= 'C';	//Gap estendido vertical

	public static final byte	INVALID		= 'I';

	public static final byte	EMPTY		= 0;

	public static final byte	BEGIN_PATH	= 'B';

	public int getValue(int row, int col);

	public void setBeginPath(int row, int col, int value) throws ExceptionInvalidVertex;

	public OptimumPath getOptimumPath(EditGraph eg) throws ExceptionInvalidBackTrack;

	public void updateVertical(int row, int col, int value) throws ExceptionInvalidVertex;

	public void updateHorizontal(int row, int col, int value) throws ExceptionInvalidVertex;

	public void updateDiagonal(int row, int col, int value) throws ExceptionInvalidVertex;

	public void updateExtended(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
			throws ExceptionInvalidVertex;

	public void updateJunction(int rowBegin, int colBegin, int rowEnd, int colEnd) throws ExceptionInvalidVertex;

	public void updateGapSetVertical(int rowBegin, int rowEnd, int col, int value) throws ExceptionInvalidVertex;

	public void updateGapSetHorizontal(int colBegin, int row, int colEnd, int value) throws ExceptionInvalidVertex;

	public void updateVertical(int row, int col, EditGraph eg) throws ExceptionInvalidVertex;

	public void updateHorizontal(int row, int col, EditGraph eg) throws ExceptionInvalidVertex;

	public void updateDiagonal(int row, int col, EditGraph eg) throws ExceptionInvalidVertex;

	//	public void updateExtended(int rowBegin, int colBegin, int rowEnd, int colEnd, EditGraph eg)
	//			throws ExceptionInvalidVertex;
	//
	//	public void updateGapSetVertical(int rowBegin, int rowEnd, int col, EditGraph eg) throws ExceptionInvalidVertex;
	//
	//	public void updateGapSetHorizontal(int colBegin, int row, int colEnd, EditGraph eg) throws ExceptionInvalidVertex;

}

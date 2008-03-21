/*
 * Created on 21/03/2008
 */
package sequences.common.backtrack;

public class BTMatrixesSemiGlobal extends BTMatrixesLocal
{

	int	rowBegin;

	public BTMatrixesSemiGlobal(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		super(rowBegin, colBegin, rowEnd, colEnd);
		rowMax = rowEnd;
		this.rowBegin = rowBegin;
	}

	@Override
	public void updateValue(int row, int col, int value, byte type)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == BackTrack.EMPTY)
		{
			if (value >= 0 || row != rowBegin)
			{
				m.setValue(row, col, value);
				arcsType.setValue(row, col, type);
				if (row == rowMax && value > valMax)
				{
					valMax = value;
					colMax = col;
				}
			}
			else
			{
				m.setValue(row, col, 0);
				arcsType.setValue(row, col, BackTrack.BEGIN_PATH);
			}
		}
	}

}

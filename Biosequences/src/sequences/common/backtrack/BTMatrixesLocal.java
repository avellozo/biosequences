/*
 * Created on 20/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Vertex;

public class BTMatrixesLocal extends BTMatrixesGlobal
{
	int	rowMax, colMax, valMax = -1;

	public BTMatrixesLocal(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		super(rowBegin, colBegin, rowEnd, colEnd);
	}

	public Vertex getOptimumVertex()
	{
		return new Vertex(rowMax, colMax);
	}

	public void updateValue(int row, int col, int value, byte type)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == BackTrack.EMPTY)
		{
			if (value >= 0)
			{
				m.setValue(row, col, value);
				arcsType.setValue(row, col, type);
				if (value > valMax)
				{
					valMax = value;
					rowMax = row;
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

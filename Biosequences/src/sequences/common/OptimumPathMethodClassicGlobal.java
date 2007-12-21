/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.ExceptionInternalEG;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.VertexRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntRange;

public class OptimumPathMethodClassicGlobal implements OptimumPathMethod
{

	long		time;
	MatrixInt	m;

	char[][]	arcsType;

	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();

		if (iMin == 0 && jMin == 0)
		{
			m = new MatrixIntPrimitive(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}
		else
		{
			m = new MatrixIntRange(new MatrixIntPrimitive(vertexRange.getRowsQtty(), vertexRange.getColsQtty()), iMin,
				jMin, iMax, jMax);
		}

		int i, j;
		int wh = 0, wd = 0, wv = 0;

		try
		{
			m.setValue(iMin, jMin, 0);
			arcsType[iMin][jMin] = 0;
			// Calcula a linha zero
			for (j = jMin + 1; j <= jMax; j++)
			{
				m.setValue(iMin, j, m.getValue(iMin, j - 1) + eg.getWeightHorizontalArc(iMin, j));
				arcsType[iMin][j] = Arc.HORIZONTAL;
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
				// coluna zero
				// b[i][0] = b[i - 1][0] + eg.getWeightVertical(i, 0);
				for (j = jMin; j <= jMax; j++)
				{
					wv = m.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j);
					if (j != jMin)
					{
						wh = m.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j);
						wd = m.getValue(i - 1, j - 1) + eg.getWeightDiagonalArc(i, j);
					}
					if ((j == jMin) || ((wv > wd) && (wv >= wh)))
					{
						m.setValue(i, j, wv);
						arcsType[i][j] = Arc.VERTICAL;
					}
					else if ((wh > wv) && (wh > wd))
					{
						m.setValue(i, j, wh);
						arcsType[i][j] = Arc.HORIZONTAL;
					}
					else
					{
						m.setValue(i, j, wd);
						arcsType[i][j] = Arc.DIAGONAL;
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return null;
	}

	public String getName()
	{
		return "Global Classic";
	}

}

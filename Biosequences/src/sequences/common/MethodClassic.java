/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.ElementInt;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntPrimitiveForMax;
import sequences.matrix.MatrixIntRange;

public class MethodClassic implements OptimumPathMethod
{

	final char		GAP_HOR		= 'R';
	final char		GAP_VERT	= 'C';

	char			type;

	MatrixInt		m;

	MatrixCharRange	arcsType;

	public MethodClassic(char type)
	{
		this.type = type;
	}

	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();

		if (isGlobal())
		{
			m = new MatrixIntPrimitive(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}
		else
		{
			m = new MatrixIntPrimitiveForMax(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}

		if (iMin != 0 || jMin != 0)
		{
			m = new MatrixIntRange(m, iMin, jMin, iMax, jMax);
		}

		arcsType = new MatrixCharRange(iMin, jMin, iMax, jMax);

		int i, j;
		int w, wh = 0, wd = 0, wv = 0;

		try
		{
			i = iMin;
			j = jMin;
			m.setValue(i, j, 0);
			arcsType.setValue(i, j, Arc.INVALID);
			// Calcula a linha iMin
			for (j = jMin + 1; j <= jMax; j++)
			{
				w = m.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j);
				if (!isGlobal() && (w < 0))
				{
					m.setValue(i, j, 0);
					arcsType.setValue(i, j, Arc.INVALID);
				}
				else
				{
					m.setValue(i, j, w);
					arcsType.setValue(i, j, Arc.HORIZONTAL);
				}
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
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
						if (isLocal() && (wv < 0))
						{
							m.setValue(i, j, 0);
							arcsType.setValue(i, j, Arc.INVALID);
						}
						else
						{
							m.setValue(i, j, wv);
							arcsType.setValue(i, j, Arc.VERTICAL);
						}
					}
					else if (wh > wd)
					{
						if (isLocal() && (wh < 0))
						{
							m.setValue(i, j, 0);
							arcsType.setValue(i, j, Arc.INVALID);
						}
						else
						{
							m.setValue(i, j, wh);
							arcsType.setValue(i, j, Arc.HORIZONTAL);
						}
					}
					else
					{
						if (isLocal() && (wd < 0))
						{
							m.setValue(i, j, 0);
							arcsType.setValue(i, j, Arc.INVALID);
						}
						else
						{
							m.setValue(i, j, wd);
							arcsType.setValue(i, j, Arc.DIAGONAL);
						}
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}

		try
		{
			Vertex v;
			if (isLocal())
			{
				ElementInt e = m.getMaxValue();
				v = eg.getVertex(e.getRow(), e.getCol());
			}
			else if (isSemiGlobal())
			{
				v = eg.getVertex(iMax, m.getIndexMaxRowValues().getValue(iMax));
			}
			else
			{
				v = vertexRange.getEndVertex();
			}
			return OptimumPathImpl.buildPath(m, arcsType, v, eg, getName());
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
	}

	public String getName()
	{
		if (isLocal())
		{
			return "Classic Local Alignment";
		}
		else if (isGlobal())
		{
			return "Classic Global Alignment";
		}
		else if (isSemiGlobal())
		{
			return "Classic Semiglobal Alignment";
		}
		return "Classic Alignment";
	}

	public boolean isLocal()
	{
		return type == OptimumPathMethod.LOCAL;
	}

	public boolean isGlobal()
	{
		return type == OptimumPathMethod.GLOBAL;
	}

	public boolean isSemiGlobal()
	{
		return type == OptimumPathMethod.SEMIGLOBAL;
	}

}

/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.matrix.ElementInt;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntPrimitiveForMax;
import sequences.matrix.MatrixIntRange;

public class OptimumPathMethodClassic implements OptimumPathMethod
{

	boolean			local;

	MatrixInt		m;

	MatrixCharRange	arcsType;

	public OptimumPathMethodClassic(boolean local)
	{
		this.local = local;
	}

	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();

		if (local)
		{
			m = new MatrixIntPrimitiveForMax(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}
		else
		{
			m = new MatrixIntPrimitive(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
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
				if (local && (w < 0))
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
						if (local && (wv < 0))
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
						if (local && (wh < 0))
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
						if (local && (wd < 0))
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
		OptimumPathImpl path = new OptimumPathImpl(eg);
		try
		{
			Vertex v;
			if (local)
			{
				ElementInt e = m.getMaxValue();
				v = eg.getVertex(e.getRow(), e.getCol());
			}
			else
			{
				v = vertexRange.getEndVertex();
			}
			char c;
			Arc arc;
			while ((c = arcsType.getValue(v.getRow(), v.getCol())) != Arc.INVALID)
			{
				switch (c)
				{
					case Arc.VERTICAL:
						arc = eg.getVerticalArc(v);
						break;
					case Arc.HORIZONTAL:
						arc = eg.getHorizontalArc(v);
						break;
					case Arc.DIAGONAL:
						arc = eg.getDiagonalArc(v);
						break;
					default:
						throw new RuntimeException("Tipo de arco inválido: " + c);
				}
				path.addFirst(arc);
				v = arc.getBeginVertex();
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
		return path;
	}

	public String getName()
	{
		return "Classic Alignment";
	}

	public boolean isLocal()
	{
		return local;
	}

}

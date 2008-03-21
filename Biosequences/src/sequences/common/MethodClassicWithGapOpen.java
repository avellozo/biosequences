/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.EditGraphWithGapSet;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.factories.ArcGapOpenFactory;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.ArrayInt;
import sequences.matrix.ArrayIntPrimitive;
import sequences.matrix.ArrayIntRange;
import sequences.matrix.ElementInt;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntPrimitiveForMax;
import sequences.matrix.MatrixIntRange;

public class MethodClassicWithGapOpen extends MethodClassic implements OptimumPathMethod
{
	public MethodClassicWithGapOpen(byte type)
	{
		super(type);
	}

	public OptimumPath createPath(VertexRange vertexRange, EditGraphWithGapSet eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();
		if (!(eg instanceof EditGraphBasic))
		{
			throw new ExceptionInvalidEditGraph(eg);
		}
		ArcGapOpenFactory arcEFactory;
		if (!(((EditGraphBasic) eg).getArcExtendedFactory() instanceof ArcGapOpenFactory))
		{
			throw new ExceptionInvalidEditGraph(eg);
		}
		else
		{
			arcEFactory = (ArcGapOpenFactory) ((EditGraphBasic) eg).getArcExtendedFactory();
		}

		int gapOpenPenalty = arcEFactory.getGapOpenPenalty();
		EditGraph egForGap = arcEFactory.getEditGraph();

		if (isGlobal())
		{
			m = new MatrixIntPrimitive(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}
		else
		{
			m = new MatrixIntPrimitiveForMax(vertexRange.getRowsQtty(), vertexRange.getColsQtty());
		}

		int gapHor;
		int wNewGap;

		ArrayInt gapVert = new ArrayIntPrimitive(vertexRange.getColsQtty());

		if (iMin != 0 || jMin != 0)
		{
			m = new MatrixIntRange(m, iMin, jMin);
			if (jMin != 0)
			{
				gapVert = new ArrayIntRange(gapVert, jMin);
			}
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
			gapHor = gapOpenPenalty;
			gapVert.setValue(j, gapOpenPenalty);
			// Calcula a linha iMin
			for (j = jMin + 1; j <= jMax; j++)
			{
				w = egForGap.getWeightHorizontalArc(i, j);
				wNewGap = m.getValue(i, j - 1) + w + gapOpenPenalty;
				wh = gapHor + w;
				if (wNewGap > wh)
				{
					wh = wNewGap;
				}
				gapHor = wh;

				if (!isGlobal() && (wh < 0))
				{
					m.setValue(i, j, 0);
					arcsType.setValue(i, j, Arc.INVALID);
				}
				else
				{
					m.setValue(i, j, wh);
					arcsType.setValue(i, j, Arc.GAP_HOR);
				}
				gapVert.setValue(j, m.getValue(i, j) + gapOpenPenalty);
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
				for (j = jMin; j <= jMax; j++)
				{
					w = egForGap.getWeightVerticalArc(i, j);
					wNewGap = m.getValue(i - 1, j) + w + gapOpenPenalty;
					wv = gapVert.getValue(j) + w;
					if (wv < wNewGap)
					{
						wv = wNewGap;
					}
					gapVert.setValue(j, wv);

					if (j != jMin)
					{
						w = egForGap.getWeightHorizontalArc(i, j);
						wNewGap = m.getValue(i, j - 1) + w + gapOpenPenalty;
						wh = gapHor + w;
						if (wNewGap > wh)
						{
							wh = wNewGap;
						}
						gapHor = wh;

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
							arcsType.setValue(i, j, Arc.GAP_VERT);
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
							arcsType.setValue(i, j, Arc.GAP_HOR);
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
					if (j == jMin)
					{
						gapHor = m.getValue(i, j) + gapOpenPenalty;
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
			return OptimumPathImpl.buildPath(m, arcsType, v, eg, getName(), gapOpenPenalty, egForGap);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
	}

	public String getName()
	{
		return "Classic Alignment with gap open penalty";
	}

}

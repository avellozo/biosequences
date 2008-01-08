/*
 * Created on 21/12/2007
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.ArcExtendedFactoryForGapOpen;
import sequences.editgraph.ArcHorizontalFactory;
import sequences.editgraph.ArcVerticalFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.matrix.ArrayInt;
import sequences.matrix.ArrayIntPrimitive;
import sequences.matrix.ArrayIntRange;
import sequences.matrix.ElementInt;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntPrimitiveForMax;
import sequences.matrix.MatrixIntRange;

public class MethodClassicWithGapOpen implements OptimumPathMethod
{
	final char		GAP_HOR		= 'R';
	final char		GAP_VERT	= 'C';

	char			type;

	MatrixInt		m;

	MatrixCharRange	arcsType;

	public MethodClassicWithGapOpen(char type)
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
		if (!(eg instanceof EditGraphBasic))
		{
			throw new ExceptionInvalidEditGraph(eg);
		}
		ArcExtendedFactoryForGapOpen arcEFactory;
		if (!(((EditGraphBasic) eg).getArcExtendedFactory() instanceof ArcExtendedFactoryForGapOpen))
		{
			throw new ExceptionInvalidEditGraph(eg);
		}
		else
		{
			arcEFactory = (ArcExtendedFactoryForGapOpen) ((EditGraphBasic) eg).getArcExtendedFactory();
		}

		int gapOpenPenalty = arcEFactory.getGapOpenPenalty();
		ArcHorizontalFactory arcHFactoryForGap = arcEFactory.getArcHorizontalFactory();
		ArcVerticalFactory arcVFactoryForGap = arcEFactory.getArcVerticalFactory();

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
				w = arcHFactoryForGap.getWeightHorizontalArc(i, j);
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
					arcsType.setValue(i, j, GAP_HOR);
				}
				gapVert.setValue(j, m.getValue(iMin, j) + gapOpenPenalty);
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
				for (j = jMin; j <= jMax; j++)
				{
					w = arcVFactoryForGap.getWeightVerticalArc(i, j);
					wNewGap = m.getValue(i - 1, j) + w + gapOpenPenalty;
					wv = gapVert.getValue(j) + w;
					if (wv < wNewGap)
					{
						wv = wNewGap;
					}
					gapVert.setValue(j, wv);

					if (j != jMin)
					{
						w = arcHFactoryForGap.getWeightHorizontalArc(i, j);
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
							arcsType.setValue(i, j, GAP_VERT);
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
							arcsType.setValue(i, j, GAP_HOR);
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
						gapHor = m.getValue(i, jMin) + gapOpenPenalty;
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
		OptimumPathImpl path = new OptimumPathImpl(eg, getName());
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
			char c;
			Arc arc;
			Vertex v1;
			int mIJ, wGap;
			while ((c = arcsType.getValue(i = v.getRow(), j = v.getCol())) != Arc.INVALID)
			{
				switch (c)
				{
					case GAP_VERT:
						mIJ = m.getValue(i, j);
						wGap = gapOpenPenalty;
						arc = arcVFactoryForGap.getVerticalArc(v);
						v1 = arc.getBeginVertex();
						while (m.getValue(v1.getRow(), v1.getCol()) + arc.getWeight() + wGap != mIJ)
						{
							wGap += arc.getWeight();
							arc = arcVFactoryForGap.getVerticalArc(v1);
							v1 = arc.getBeginVertex();
						}
						arc = eg.getExtendedArc(new VertexRange(v1, v));
						break;
					case GAP_HOR:
						mIJ = m.getValue(i, j);
						wGap = gapOpenPenalty;
						arc = arcHFactoryForGap.getHorizontalArc(v);
						v1 = arc.getBeginVertex();
						while (m.getValue(v1.getRow(), v1.getCol()) + arc.getWeight() + wGap != mIJ)
						{
							wGap += arc.getWeight();
							arc = arcHFactoryForGap.getHorizontalArc(v1);
							v1 = arc.getBeginVertex();
						}
						arc = eg.getExtendedArc(new VertexRange(v1, v));
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
		return "Classic Alignment with gap open penalty";
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

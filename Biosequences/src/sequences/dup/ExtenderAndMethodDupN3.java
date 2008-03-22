/*
 * Created on 09/01/2008
 */
package sequences.dup;

import sequences.common.SequenceInternalException;
import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.factories.ArcExtendedFactory;
import sequences.editgraph.arcs.factories.GapOpenFactory;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.ArrayInt;
import sequences.matrix.ArrayIntPrimitive;
import sequences.matrix.ArrayIntRange;
import sequences.matrix.ElementInt;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntPrimitiveForMax;
import sequences.matrix.MatrixIntRange;

public class ExtenderAndMethodDupN3 implements ArcExtendedFactory, OptimumPathMethod
{

	EditGraph						egSeq1xSeq1, egSeq1xSeq2, egSeq2xSeq1, egSeq2xSeq2;
	DupPenaltyCalculator			duplicationPenalty;
	GapOpenFactory	arcEFactoryForGapOpen;
	MatrixInt						m;
	boolean							searchForward;

	MatrixCharRange					arcsType;

	char							alignType;											//Local or Global or Semi-global

	//Só verifica o conjunto C, se os conjuntos A e B devem ser verificados
	public ExtenderAndMethodDupN3(GapOpenFactory arcEFactoryForGapOpen, EditGraph egSeq1xSeq1,
			EditGraph egSeq1xSeq2, EditGraph egSeq2xSeq1, EditGraph egSeq2xSeq2,
			DupPenaltyCalculator duplicationPenalty, boolean searchForward, char alignType)
	{
		this.egSeq1xSeq1 = egSeq1xSeq1;
		this.egSeq1xSeq2 = egSeq1xSeq2;
		this.egSeq2xSeq1 = egSeq2xSeq1;
		this.egSeq2xSeq2 = egSeq2xSeq2;
		this.duplicationPenalty = duplicationPenalty;
		this.searchForward = searchForward;
		this.alignType = alignType;
		this.arcEFactoryForGapOpen = arcEFactoryForGapOpen;
	}

	//Assume que os grafos de edições estão corretos
	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		int cols = vertexRange.getColsQtty();
		int rows = vertexRange.getRowsQtty();
		return (((cols == 1) || (rows == 1)) && (cols != rows));
		//		if ((cols != 1) && (rows == 1))
		//		{
		//			if (setA)
		//			{
		//				return egSeq2xSeq2.existsVertex(vertexRange.getBeginVertex());
		//			}
		//		}
		//		else if ((cols == 1) && (rows != 1))
		//		{
		//		}
		//		return false;
	}

	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (!canCreateExtendedArc(vertexRange))
		{
			throw new ExceptionInvalidVertex("Can't create an extended arc from " + vertexRange.getBeginVertex()
				+ " to " + vertexRange.getEndVertex() + ".");
		}

		ElementInt optimumABC = null, optimumD = null;
		EditGraph egABC, egD;
		MatrixInt mABC = null, mD = null;
		MatrixCharRange arcsTypeABC = null, arcsTypeD = null;
		int gapOpenPenaltyABC = 0, gapOpenPenaltyD = 0;
		EditGraph egForGapABC = null, egForGapD = null;
		int jMin, jMax;
		int iMin, iMax;
		ArcExtended arcABC = null, arcD = null;

		if (vertexRange.getRowsQtty() == 1)
		{
			iMin = vertexRange.getBeginVertex().getCol();
			iMax = vertexRange.getEndVertex().getCol();
			egABC = getEgSeq2xSeq2();
			egD = getEgSeq2xSeq1();
		}
		else
		{
			iMin = vertexRange.getBeginVertex().getRow();
			iMax = vertexRange.getEndVertex().getRow();
			egABC = getEgSeq1xSeq1();
			egD = getEgSeq1xSeq2();
		}

		if (isSetA() || isSetB())
		{
			//monta a matrix de PD e de arcos
			if (isSetA())
			{
				jMin = egABC.getColMin();
			}
			else
			{
				jMin = iMax;
			}
			if (isSetB())
			{
				jMax = egABC.getColMax();
			}
			else
			{
				jMax = iMin;
			}
			mABC = new MatrixIntPrimitive(iMax - iMin + 1, jMax - jMin + 1);
			if (iMin != 0 || jMin != 0)
			{
				mABC = new MatrixIntRange(mABC, iMin, jMin);
			}
			arcsTypeABC = new MatrixCharRange(iMin, jMin, iMax, jMax);

			//if the extension is for gap open then build dynamic matrix with gap open
			if ((egABC instanceof EditGraphBasic)
				&& (((EditGraphBasic) egABC).getArcExtendedFactory() instanceof GapOpenFactory))
			{
				GapOpenFactory arcEFactory = (GapOpenFactory) ((EditGraphBasic) egABC).getArcExtendedFactory();
				gapOpenPenaltyABC = arcEFactory.getGapOpenPenalty();
				egForGapABC = arcEFactory.getEditGraph();

				int gapHor;
				ArrayInt gapVert = new ArrayIntPrimitive(mABC.getColsQtty());
				if (jMin != 0)
				{
					gapVert = new ArrayIntRange(gapVert, jMin);
				}

				int wNewGap;
				int i, j;
				int w, wh = 0, wd = 0, wv = 0;

				i = iMin;
				j = jMin;
				mABC.setValue(i, j, 0);
				arcsTypeABC.setValue(i, j, Arc.INVALID);
				gapHor = gapOpenPenaltyABC;
				gapVert.setValue(j, gapOpenPenaltyABC);
				// Calcula a linha iMin
				for (j = jMin + 1; j <= jMax; j++)
				{
					if (j > iMin && j <= iMax)
					{
						j = iMax;
						mABC.setValue(i, j, 0);
						arcsTypeABC.setValue(i, j, Arc.INVALID);
						if (isSetC() && (mABC.getValue(i, iMin) > mABC.getValue(i, j)))
						{
							mABC.setValue(i, j, mABC.getValue(i, iMin));
							arcsTypeABC.setValue(i, j, Arc.JUNCTION);
						}
					}
					else
					{
						w = egForGapABC.getWeightHorizontalArc(i, j);
						wNewGap = mABC.getValue(i, j - 1) + w + gapOpenPenaltyABC;
						wh = gapHor + w;
						if (wNewGap > wh)
						{
							wh = wNewGap;
						}
						if (wh < 0)
						{
							mABC.setValue(i, j, 0);
							arcsTypeABC.setValue(i, j, Arc.INVALID);
						}
						else
						{
							mABC.setValue(i, j, wh);
							arcsTypeABC.setValue(i, j, Arc.GAP_HOR);
						}
						gapHor = wh;
					}
					gapVert.setValue(j, mABC.getValue(i, j) + gapOpenPenaltyABC);
				}
				for (i = iMin + 1; i <= iMax; i++)
				{
					for (j = jMin; j <= jMax; j++)
					{
						if ((j > iMin) && (j <= iMax))
						{
							j = iMax;
						}
						w = egForGapABC.getWeightVerticalArc(i, j);
						wNewGap = mABC.getValue(i - 1, j) + w + gapOpenPenaltyABC;
						wv = gapVert.getValue(j) + w;
						if (wv < wNewGap)
						{
							wv = wNewGap;
						}
						gapVert.setValue(j, wv);

						if ((j != jMin) && (j != iMax))
						{
							w = egForGapABC.getWeightHorizontalArc(i, j);
							wNewGap = mABC.getValue(i, j - 1) + w + gapOpenPenaltyABC;
							wh = gapHor + w;
							if (wNewGap > wh)
							{
								wh = wNewGap;
							}
							gapHor = wh;

							wd = mABC.getValue(i - 1, j - 1) + egABC.getWeightDiagonalArc(i, j);
						}
						if ((j == jMin) || (j == iMax) || ((wv > wd) && (wv >= wh)))
						{
							mABC.setValue(i, j, wv);
							arcsTypeABC.setValue(i, j, Arc.GAP_VERT);
							if ((j == iMax) && isSetC() && (mABC.getValue(i, iMin) > mABC.getValue(i, j)))
							{
								mABC.setValue(i, j, mABC.getValue(i, iMin));
								arcsTypeABC.setValue(i, j, Arc.JUNCTION);
							}
						}
						else if (wh > wd)
						{
							mABC.setValue(i, j, wh);
							arcsTypeABC.setValue(i, j, Arc.GAP_HOR);
						}
						else
						{
							mABC.setValue(i, j, wd);
							arcsTypeABC.setValue(i, j, Arc.DIAGONAL);
						}
						if (j == jMin)
						{
							gapHor = mABC.getValue(i, jMin) + gapOpenPenaltyABC;
						}
					}
				}
			}
			else
			//build dynamic matrix without gap open
			{
				int i, j;
				int w, wh = 0, wd = 0, wv = 0;

				i = iMin;
				j = jMin;
				mABC.setValue(i, j, 0);
				arcsTypeABC.setValue(i, j, Arc.INVALID);
				// Calcula a linha iMin
				for (j = jMin + 1; j <= jMax; j++)
				{
					if (j > iMin && j <= iMax)
					{
						j = iMax;
						mABC.setValue(i, j, 0);
						arcsTypeABC.setValue(i, j, Arc.INVALID);
						if (isSetC() && (mABC.getValue(i, iMin) > mABC.getValue(i, j)))
						{
							mABC.setValue(i, j, mABC.getValue(i, iMin));
							arcsTypeABC.setValue(i, j, Arc.JUNCTION);
						}
					}
					else
					{
						wh = mABC.getValue(i, j - 1) + egABC.getWeightHorizontalArc(i, j);
						if (wh < 0)
						{
							mABC.setValue(i, j, 0);
							arcsTypeABC.setValue(i, j, Arc.INVALID);
						}
						else
						{
							mABC.setValue(i, j, wh);
							arcsTypeABC.setValue(i, j, Arc.HORIZONTAL);
						}
					}
				}
				for (i = iMin + 1; i <= iMax; i++)
				{
					for (j = jMin; j <= jMax; j++)
					{
						if ((j > iMin) && (j <= iMax))
						{
							j = iMax;
						}
						wv = mABC.getValue(i - 1, j) + egABC.getWeightVerticalArc(i, j);

						if ((j != jMin) && (j != iMax))
						{
							wh = mABC.getValue(i, j - 1) + egABC.getWeightHorizontalArc(i, j);
							wd = mABC.getValue(i - 1, j - 1) + egABC.getWeightDiagonalArc(i, j);
						}
						if ((j == jMin) || (j == iMax) || ((wv > wd) && (wv >= wh)))
						{
							mABC.setValue(i, j, wv);
							arcsTypeABC.setValue(i, j, Arc.VERTICAL);
							if ((j == iMax) && isSetC() && (mABC.getValue(i, iMin) > mABC.getValue(i, j)))
							{
								mABC.setValue(i, j, mABC.getValue(i, iMin));
								arcsTypeABC.setValue(i, j, Arc.JUNCTION);
							}
						}
						else if (wh > wd)
						{
							mABC.setValue(i, j, wh);
							arcsTypeABC.setValue(i, j, Arc.HORIZONTAL);
						}
						else
						{
							mABC.setValue(i, j, wd);
							arcsTypeABC.setValue(i, j, Arc.DIAGONAL);
						}
					}
				}
			}
			int i = iMax;
			int j = jMin;
			optimumABC = mABC.getElement(i, j);
			int val, max = optimumABC.getValue();
			for (j = jMin + 1; j <= jMax; j++)
			{
				if ((j > iMin) && (j <= iMax))
				{
					j = iMax;
				}
				val = mABC.getValue(i, j);
				if (val > max)
				{
					max = val;
					optimumABC = mABC.getElement(i, j);
				}
			}
			OptimumPath path = OptimumPathImpl.buildPath(mABC, arcsTypeABC, egABC.getVertex(optimumABC.getRow(),
				optimumABC.getCol()), egABC,
				"Alinhamento clássico na mesma seqüência para um arco extendido de uma duplicação.", gapOpenPenaltyABC,
				egForGapABC, iMin);
			arcABC = new ArcExtendedDup(vertexRange, path, getDuplicationPenalty(), true);
		}
		if (isSetD())
		{
			//monta a matrix de PD e de arcos
			jMin = egD.getColMin();
			jMax = egD.getColMax();
			mD = new MatrixIntPrimitive(iMax - iMin + 1, jMax - jMin + 1);
			if (iMin != 0 || jMin != 0)
			{
				mD = new MatrixIntRange(mD, iMin, jMin);
			}
			arcsTypeD = new MatrixCharRange(iMin, jMin, iMax, jMax);

			//if the extension is for gap open then build dynamic matrix with gap open
			if ((egD instanceof EditGraphBasic)
				&& (((EditGraphBasic) egD).getArcExtendedFactory() instanceof GapOpenFactory))
			{
				GapOpenFactory arcEFactory = (GapOpenFactory) ((EditGraphBasic) egD).getArcExtendedFactory();
				gapOpenPenaltyD = arcEFactory.getGapOpenPenalty();
				egForGapD = arcEFactory.getEditGraph();

				int gapHor;
				ArrayInt gapVert = new ArrayIntPrimitive(mD.getColsQtty());
				if (jMin != 0)
				{
					gapVert = new ArrayIntRange(gapVert, jMin);
				}

				int wNewGap;
				int i, j;
				int w, wh = 0, wd = 0, wv = 0;

				i = iMin;
				j = jMin;
				mD.setValue(i, j, 0);
				arcsTypeD.setValue(i, j, Arc.INVALID);
				gapHor = gapOpenPenaltyD;
				gapVert.setValue(j, gapOpenPenaltyD);
				// Calcula a linha iMin
				for (j = jMin + 1; j <= jMax; j++)
				{
					w = egForGapD.getWeightHorizontalArc(i, j);
					wNewGap = mD.getValue(i, j - 1) + w + gapOpenPenaltyD;
					wh = gapHor + w;
					if (wNewGap > wh)
					{
						wh = wNewGap;
					}
					if (wh < 0)
					{
						mD.setValue(i, j, 0);
						arcsTypeD.setValue(i, j, Arc.INVALID);
					}
					else
					{
						mD.setValue(i, j, wh);
						arcsTypeD.setValue(i, j, Arc.GAP_HOR);
					}
					gapHor = wh;
					gapVert.setValue(j, mD.getValue(i, j) + gapOpenPenaltyD);
				}
				for (i = iMin + 1; i <= iMax; i++)
				{
					for (j = jMin; j <= jMax; j++)
					{
						w = egForGapD.getWeightVerticalArc(i, j);
						wNewGap = mD.getValue(i - 1, j) + w + gapOpenPenaltyD;
						wv = gapVert.getValue(j) + w;
						if (wv < wNewGap)
						{
							wv = wNewGap;
						}
						gapVert.setValue(j, wv);

						if (j != jMin)
						{
							w = egForGapD.getWeightHorizontalArc(i, j);
							wNewGap = mD.getValue(i, j - 1) + w + gapOpenPenaltyD;
							wh = gapHor + w;
							if (wNewGap > wh)
							{
								wh = wNewGap;
							}
							gapHor = wh;

							wd = mD.getValue(i - 1, j - 1) + egD.getWeightDiagonalArc(i, j);
						}
						if ((j == jMin) || ((wv > wd) && (wv >= wh)))
						{
							mD.setValue(i, j, wv);
							arcsTypeD.setValue(i, j, Arc.GAP_VERT);
						}
						else if (wh > wd)
						{
							mD.setValue(i, j, wh);
							arcsTypeD.setValue(i, j, Arc.GAP_HOR);
						}
						else
						{
							mD.setValue(i, j, wd);
							arcsTypeD.setValue(i, j, Arc.DIAGONAL);
						}
						if (j == jMin)
						{
							gapHor = mD.getValue(i, jMin) + gapOpenPenaltyD;
						}
					}
				}
			}
			else
			//build dynamic matrix without gap open
			{
				int i, j;
				int w, wh = 0, wd = 0, wv = 0;

				i = iMin;
				j = jMin;
				mD.setValue(i, j, 0);
				arcsTypeD.setValue(i, j, Arc.INVALID);
				// Calcula a linha iMin
				for (j = jMin + 1; j <= jMax; j++)
				{
					wh = mD.getValue(i, j - 1) + egD.getWeightHorizontalArc(i, j);
					if (wh < 0)
					{
						mD.setValue(i, j, 0);
						arcsTypeD.setValue(i, j, Arc.INVALID);
					}
					else
					{
						mD.setValue(i, j, wh);
						arcsTypeD.setValue(i, j, Arc.HORIZONTAL);
					}
				}
				for (i = iMin + 1; i <= iMax; i++)
				{
					for (j = jMin; j <= jMax; j++)
					{
						wv = mD.getValue(i - 1, j) + egD.getWeightVerticalArc(i, j);

						if (j != jMin)
						{
							wh = mD.getValue(i, j - 1) + egD.getWeightHorizontalArc(i, j);
							wd = mD.getValue(i - 1, j - 1) + egD.getWeightDiagonalArc(i, j);
						}
						if ((j == jMin) || ((wv > wd) && (wv >= wh)))
						{
							mD.setValue(i, j, wv);
							arcsTypeD.setValue(i, j, Arc.VERTICAL);
						}
						else if (wh > wd)
						{
							mD.setValue(i, j, wh);
							arcsTypeD.setValue(i, j, Arc.HORIZONTAL);
						}
						else
						{
							mD.setValue(i, j, wd);
							arcsTypeD.setValue(i, j, Arc.DIAGONAL);
						}
					}
				}
			}
			int i = iMax;
			int j = jMin;
			optimumD = mD.getElement(i, j);
			int val, max = optimumD.getValue();
			for (j = jMin + 1; j <= jMax; j++)
			{
				val = mD.getValue(i, j);
				if (val > max)
				{
					max = val;
					optimumD = mD.getElement(i, j);
				}
			}
			OptimumPath path = OptimumPathImpl.buildPath(mD, arcsTypeD, egD.getVertex(optimumD.getRow(),
				optimumD.getCol()), egD,
				"Alinhamento clássico na outra seqüência para um arco extendido de uma duplicação.", gapOpenPenaltyD,
				egForGapD);
			arcD = new ArcExtendedDup(vertexRange, path, getDuplicationPenalty(), false);
		}

		ArcExtended arc = getArcEFactoryForGapOpen().getExtendedArc(vertexRange);
		return (arcD.getWeight() < arc.getWeight()) ? arc : arcD;
	}

	public DupPenaltyCalculator getDuplicationPenalty()
	{
		return duplicationPenalty;
	}

	public GapOpenFactory getArcEFactoryForGapOpen()
	{
		return arcEFactoryForGapOpen;
	}

	public EditGraph getEgSeq1xSeq1()
	{
		return egSeq1xSeq1;
	}

	public EditGraph getEgSeq1xSeq2()
	{
		return egSeq1xSeq2;
	}

	public EditGraph getEgSeq2xSeq1()
	{
		return egSeq2xSeq1;
	}

	public EditGraph getEgSeq2xSeq2()
	{
		return egSeq2xSeq2;
	}

	public boolean isSetA()
	{
		return setA;
	}

	public boolean isSetB()
	{
		return setB;
	}

	public boolean isSetC()
	{
		return setC;
	}

	public boolean isSetD()
	{
		return setD;
	}

	public char getAlignType()
	{
		return alignType;
	}

	public MatrixInt buildM_ABC_I1(int iMin, int iMax, EditGraph egABC)
	{

	}

	public int getW_ABC_I1_I2(int i2, MatrixInt mABC_I1)
	{

	}

	public MatrixInt getW_D_J(int j, EditGraph egD, MatrixInt mD_JAnt)
	{

	}

	public OptimumPath createPath(VertexRange vertexRange, EditGraph eg) throws ExceptionInvalidEditGraph
	{
		int iMin, iMax, jMin, jMax;
		iMin = vertexRange.getBeginVertex().getRow();
		jMin = vertexRange.getBeginVertex().getCol();
		iMax = vertexRange.getEndVertex().getRow();
		jMax = vertexRange.getEndVertex().getCol();
		int rows = vertexRange.getRowsQtty();
		int cols = vertexRange.getColsQtty();

		if (!(eg instanceof EditGraphBasic))
		{
			throw new ExceptionInvalidEditGraph(eg);
		}
		if (((EditGraphBasic) eg).getArcExtendedFactory() != this)
		{
			throw new ExceptionInvalidEditGraph(eg);
		}

		GapOpenFactory arcEFactoryForGapOpen = getArcEFactoryForGapOpen();
		int gapOpenPenalty;
		EditGraph egForGap;
		int wJOld;
		ArrayInt gapVert = null;
		int gapHor = 0;
		if (arcEFactoryForGapOpen != null)
		{
			gapOpenPenalty = arcEFactoryForGapOpen.getGapOpenPenalty();
			egForGap = arcEFactoryForGapOpen.getEditGraph();
			gapVert = new ArrayIntPrimitive(cols);
			if (jMin != 0)
			{
				gapVert = new ArrayIntRange(gapVert, jMin);
			}
		}
		else
		{
			gapOpenPenalty = 0;
			egForGap = eg;
		}

		if (isGlobal())
		{
			m = new MatrixIntPrimitive(rows, cols);
		}
		else
		{
			m = new MatrixIntPrimitiveForMax(rows, cols);
		}

		MatrixInt extI = new MatrixIntPrimitive(rows, cols);
		MatrixInt extJ = new MatrixIntPrimitive(rows, cols);

		if (iMin != 0 || jMin != 0)
		{
			m = new MatrixIntRange(m, iMin, jMin);
			extI = new MatrixIntRange(extI, iMin, jMin);
			extJ = new MatrixIntRange(extJ, iMin, jMin);
		}

		arcsType = new MatrixCharRange(iMin, jMin, iMax, jMax);

		MatrixInt WI = buildW(true, vertexRange);
		MatrixInt WJ = buildW(false, vertexRange);

		int i, j, i1, j1;
		int wh, whe, wv, wve, wd, wdj, wdi;

		try
		{
			i = iMin;
			j = jMin;
			m.setValue(i, j, 0);
			arcsType.setValue(i, j, Arc.INVALID);
			if (gapOpenPenalty != 0)
			{
				gapHor = m.getValue(i, j) + gapOpenPenalty;
				gapVert.setValue(j, m.getValue(i, j) + gapOpenPenalty);
			}

			// Calcula a linha iMin
			for (j = jMin + 1; j <= i; j++)
			{
				wh = m.getValue(i, j - 1) + egForGap.getWeightHorizontalArc(i, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					whe = gapHor + egForGap.getWeightHorizontalArc(i, j);
				}
				for (j1 = jMin; j1 < j; j1++)
				{

				}
				if (!isGlobal() && (wh < 0))
				{
					m.setValue(i, j, 0);
					arcsType.setValue(i, j, Arc.INVALID);
				}
				else
				{
					m.setValue(i, j, wh);
					if (gapOpenPenalty != 0)
					{
						arcsType.setValue(i, j, Arc.GAP_HOR);
					}
					else
					{
						arcsType.setValue(i, j, Arc.HORIZONTAL);
					}
				}
				if (gapOpenPenalty != 0)
				{
					gapVert.setValue(j, m.getValue(i, j) + gapOpenPenalty);
					gapHor = Math.max(wh, whe);
				}
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

	public MatrixInt buildW(boolean isSeq1, VertexRange range)
	{

	}

	//eg is the edit graph seq2xseq2 or seq1xseq1
	public static MatrixInt buildWA(EditGraph eg, VertexRange range, MatrixInt wC1) throws ExceptionInvalidVertex
	{
		int iMin = range.getBeginVertex().getRow();
		int iMax = range.getEndVertex().getRow();
		int jMin = range.getBeginVertex().getCol();
		int jMax = range.getEndVertex().getCol();
		int rows = iMax - iMin + 1;
		int cols = jMax - jMin + 1;

		MatrixInt wA = new MatrixIntPrimitive(rows, rows);
		ArrayInt wJ = new ArrayIntPrimitive(cols);

		if (iMin != 0)
		{
			wA = new MatrixIntRange(wA, iMin, iMin);
		}
		if (jMin != 0)
		{
			wJ = new ArrayIntRange(wJ, jMin);
		}
		int i, i1, j;
		int gapOpenPenalty;
		EditGraph egForGap;
		int wh = 0, wd = 0, wv = 0;
		int wJOld;
		ArrayInt gapVert = null;
		int gapHor = 0;
		//if the extension is for gap open then build dynamic matrix with gap open
		if ((eg instanceof EditGraphBasic)
			&& (((EditGraphBasic) eg).getArcExtendedFactory() instanceof GapOpenFactory))
		{
			GapOpenFactory arcEFactory = (GapOpenFactory) ((EditGraphBasic) eg).getArcExtendedFactory();
			gapOpenPenalty = arcEFactory.getGapOpenPenalty();
			egForGap = arcEFactory.getEditGraph();

			gapVert = new ArrayIntPrimitive(cols);
			if (jMin != 0)
			{
				gapVert = new ArrayIntRange(gapVert, jMin);
			}
		}
		else
		{
			egForGap = eg;
			gapOpenPenalty = 0;
		}

		for (i = iMin; i <= iMax; i++)
		{
			i1 = i;
			j = jMin;
			wJ.setValue(j, 0);
			wA.setValue(i, i1, wJ.getValue(j));
			if (gapOpenPenalty != 0)
			{
				gapHor = wJ.getValue(j) + gapOpenPenalty;
				gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
			}

			for (j = jMin + 1; j <= i; j++)
			{
				wh = wJ.getValue(j - 1) + egForGap.getWeightHorizontalArc(i1, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j));
					gapHor = wh;
				}
				wJ.setValue(j, Math.max(0, wh));
				if (gapOpenPenalty != 0)
				{
					gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
				}
				if (wJ.getValue(j) > wA.getValue(i, i1))
				{
					wA.setValue(i, i1, wJ.getValue(j));
				}
			}

			if (wC1 != null)
			{
				wC1.setValue(i, i1, wJ.getValue(i));
			}

			for (i1 = i + 1; i1 <= iMax; i1++)
			{
				j = jMin;
				wJOld = wJ.getValue(j);
				wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1, j));
					gapHor = wv + gapOpenPenalty;
					gapVert.setValue(j, wv);
				}
				wJ.setValue(j, wv);
				wA.setValue(i, i1, wJ.getValue(j));

				for (j = jMin + 1; j <= i; j++)
				{
					wd = wJOld + eg.getWeightDiagonalArc(i1, j);
					wh = wJ.getValue(j - 1) + egForGap.getWeightHorizontalArc(i1, j) + gapOpenPenalty;
					wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1, j) + gapOpenPenalty;
					if (gapOpenPenalty != 0)
					{
						wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j));
						wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1, j));
						gapHor = wh;
						gapVert.setValue(j, wv);
					}
					wJOld = wJ.getValue(j);
					wJ.setValue(j, max(wh, wv, wd));
					if (wJ.getValue(j) > wA.getValue(i, i1))
					{
						wA.setValue(i, i1, wJ.getValue(j));
					}
				}
				if (wC1 != null)
				{
					wC1.setValue(i, i1, wJ.getValue(i));
				}
			}
		}
		return wA;
	}

	//eg is the edit graph seq2xseq2 or seq1xseq1
	//matrix wA will be updated with the new values in set B and will be return
	public static MatrixInt buildWB(EditGraph eg, VertexRange range, MatrixInt wA, MatrixInt wC2)
			throws ExceptionInvalidVertex
	{
		int iMin = range.getBeginVertex().getRow();
		int iMax = range.getEndVertex().getRow();
		int jMin = range.getBeginVertex().getCol();
		int jMax = range.getEndVertex().getCol();
		int rows = iMax - iMin + 1;
		int cols = jMax - jMin + 1;

		MatrixInt wB;
		if (wA != null)
		{
			wB = wA;
		}
		else
		{
			wB = new MatrixIntPrimitive(rows, rows);
			if (iMin != 0)
			{
				wB = new MatrixIntRange(wB, iMin, iMin);
			}
		}

		ArrayInt wJ = new ArrayIntPrimitive(cols);

		if (jMin != 0)
		{
			wJ = new ArrayIntRange(wJ, jMin);
		}
		int i, i1, j;
		int gapOpenPenalty;
		EditGraph egForGap;
		int wh = 0, wd = 0, wv = 0;
		int wJOld;
		ArrayInt gapVert = null;
		int gapHor = 0;
		//if the extension is for gap open then build dynamic matrix with gap open
		if ((eg instanceof EditGraphBasic)
			&& (((EditGraphBasic) eg).getArcExtendedFactory() instanceof GapOpenFactory))
		{
			GapOpenFactory arcEFactory = (GapOpenFactory) ((EditGraphBasic) eg).getArcExtendedFactory();
			gapOpenPenalty = arcEFactory.getGapOpenPenalty();
			egForGap = arcEFactory.getEditGraph();

			gapVert = new ArrayIntPrimitive(cols);
			if (jMin != 0)
			{
				gapVert = new ArrayIntRange(gapVert, jMin);
			}
		}
		else
		{
			egForGap = eg;
			gapOpenPenalty = 0;
		}

		for (i = iMin; i <= iMax; i++)
		{
			i1 = i;
			j = jMax;
			wJ.setValue(j, 0);
			if ((wA == null) || (wJ.getValue(j) > wB.getValue(i1, i)))
			{
				wB.setValue(i1, i, wJ.getValue(j));
			}
			if (gapOpenPenalty != 0)
			{
				gapHor = wJ.getValue(j) + gapOpenPenalty;
				gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
			}

			for (j = jMax - 1; j >= i; j--)
			{
				wh = wJ.getValue(j + 1) + egForGap.getWeightHorizontalArc(i1, j + 1) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j + 1));
					gapHor = wh;
				}
				wJ.setValue(j, Math.max(0, wh));
				if (gapOpenPenalty != 0)
				{
					gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
				}
				if (wJ.getValue(j) > wB.getValue(i1, i))
				{
					wB.setValue(i1, i, wJ.getValue(j));
				}
			}

			if (wC2 != null)
			{
				wC2.setValue(i1, i, wJ.getValue(i));
			}

			for (i1 = i - 1; i1 >= iMin; i1--)
			{
				j = jMax;
				wJOld = wJ.getValue(j);
				wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1 + 1, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1 + 1, j));
					gapHor = wv + gapOpenPenalty;
					gapVert.setValue(j, wv);
				}
				wJ.setValue(j, wv);
				if ((wA == null) || (wJ.getValue(j) > wB.getValue(i1, i)))
				{
					wB.setValue(i1, i, wJ.getValue(j));
				}

				for (j = jMax - 1; j >= i; j--)
				{
					wd = wJOld + eg.getWeightDiagonalArc(i1 + 1, j + 1);
					wh = wJ.getValue(j + 1) + egForGap.getWeightHorizontalArc(i1, j + 1) + gapOpenPenalty;
					wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1 + 1, j) + gapOpenPenalty;
					if (gapOpenPenalty != 0)
					{
						wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j + 1));
						wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1 + 1, j));
						gapHor = wh;
						gapVert.setValue(j, wv);
					}
					wJOld = wJ.getValue(j);
					wJ.setValue(j, max(wh, wv, wd));
					if (wJ.getValue(j) > wB.getValue(i1, i))
					{
						wB.setValue(i1, i, wJ.getValue(j));
					}
				}
				if (wC2 != null)
				{
					wC2.setValue(i1, i, wJ.getValue(i));
				}
			}
		}
		return wB;
	}

	public static MatrixInt buildWC(MatrixInt wC1, MatrixInt wC2, MatrixInt wB) throws ExceptionInvalidVertex
	{
		int iMin = wC1.getIndexBeginRow();
		int iMax = wC1.getIndexEndRow();
		int rows = iMax - iMin + 1;

		MatrixInt wC;
		if (wB != null)
		{
			wC = wB;
		}
		else
		{
			wC = new MatrixIntPrimitive(rows, rows);
			if (iMin != 0)
			{
				wC = new MatrixIntRange(wC, iMin, iMin);
			}
		}

		int i, i1, i2;
		int w;
		for (i = iMin; i <= iMax; i++)
		{
			for (i1 = i; i1 <= iMax; i1++)
			{
				i2 = i;
				w = wC1.getValue(i, i2) + wC2.getValue(i2, i1);
				if ((wB == null) || (w > wC.getValue(i, i1)))
				{
					wC.setValue(i, i1, w);
				}
				for (i2 = i + 1; i2 <= i1; i2++)
				{
					w = wC1.getValue(i, i2) + wC2.getValue(i2, i1);
					if (w > wC.getValue(i, i1))
					{
						wC.setValue(i, i1, w);
					}
				}
			}
		}
		return wC;
	}

	//eg is the edit graph seq2xseq1 or seq1xseq2
	public static MatrixInt buildWD(EditGraph eg, VertexRange range, MatrixInt wC) throws ExceptionInvalidVertex
	{
		int iMin = range.getBeginVertex().getRow();
		int iMax = range.getEndVertex().getRow();
		int jMin = range.getBeginVertex().getCol();
		int jMax = range.getEndVertex().getCol();
		int rows = iMax - iMin + 1;
		int cols = jMax - jMin + 1;

		MatrixInt wD;
		if (wC != null)
		{
			wD = wC;
		}
		else
		{
			wD = new MatrixIntPrimitive(rows, rows);
			if (iMin != 0)
			{
				wD = new MatrixIntRange(wD, iMin, iMin);
			}
		}

		ArrayInt wJ = new ArrayIntPrimitive(cols);

		if (jMin != 0)
		{
			wJ = new ArrayIntRange(wJ, jMin);
		}
		int i, i1, j;
		int gapOpenPenalty;
		EditGraph egForGap;
		int wh = 0, wd = 0, wv = 0;
		int wJOld;
		ArrayInt gapVert = null;
		int gapHor = 0;
		//if the extension is for gap open then build dynamic matrix with gap open
		if ((eg instanceof EditGraphBasic)
			&& (((EditGraphBasic) eg).getArcExtendedFactory() instanceof GapOpenFactory))
		{
			GapOpenFactory arcEFactory = (GapOpenFactory) ((EditGraphBasic) eg).getArcExtendedFactory();
			gapOpenPenalty = arcEFactory.getGapOpenPenalty();
			egForGap = arcEFactory.getEditGraph();

			gapVert = new ArrayIntPrimitive(cols);
			if (jMin != 0)
			{
				gapVert = new ArrayIntRange(gapVert, jMin);
			}
		}
		else
		{
			egForGap = eg;
			gapOpenPenalty = 0;
		}

		for (i = iMin; i <= iMax; i++)
		{
			i1 = i;
			j = jMin;
			wJ.setValue(j, 0);
			if ((wC == null) || (wJ.getValue(j) > wD.getValue(i, i1)))
			{
				wD.setValue(i, i1, wJ.getValue(j));
			}
			if (gapOpenPenalty != 0)
			{
				gapHor = wJ.getValue(j) + gapOpenPenalty;
				gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
			}

			for (j = jMin + 1; j <= jMax; j++)
			{
				wh = wJ.getValue(j - 1) + egForGap.getWeightHorizontalArc(i1, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j));
					gapHor = wh;
				}
				wJ.setValue(j, Math.max(0, wh));
				if (gapOpenPenalty != 0)
				{
					gapVert.setValue(j, wJ.getValue(j) + gapOpenPenalty);
				}
				if (wJ.getValue(j) > wD.getValue(i, i1))
				{
					wD.setValue(i, i1, wJ.getValue(j));
				}
			}

			for (i1 = i + 1; i1 <= iMax; i1++)
			{
				j = jMin;
				wJOld = wJ.getValue(j);
				wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1, j) + gapOpenPenalty;
				if (gapOpenPenalty != 0)
				{
					wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1, j));
					gapHor = wv + gapOpenPenalty;
					gapVert.setValue(j, wv);
				}
				wJ.setValue(j, wv);
				if ((wC == null) || (wJ.getValue(j) > wD.getValue(i, i1)))
				{
					wD.setValue(i, i1, wJ.getValue(j));
				}

				for (j = jMin + 1; j <= jMax; j++)
				{
					wd = wJOld + eg.getWeightDiagonalArc(i1, j);
					wh = wJ.getValue(j - 1) + egForGap.getWeightHorizontalArc(i1, j) + gapOpenPenalty;
					wv = wJ.getValue(j) + egForGap.getWeightVerticalArc(i1, j) + gapOpenPenalty;
					if (gapOpenPenalty != 0)
					{
						wh = Math.max(wh, gapHor + egForGap.getWeightHorizontalArc(i1, j));
						wv = Math.max(wv, gapVert.getValue(j) + egForGap.getWeightVerticalArc(i1, j));
						gapHor = wh;
						gapVert.setValue(j, wv);
					}
					wJOld = wJ.getValue(j);
					wJ.setValue(j, max(wh, wv, wd));
					if (wJ.getValue(j) > wD.getValue(i, i1))
					{
						wD.setValue(i, i1, wJ.getValue(j));
					}
				}
			}
		}
		return wD;
	}

	public String getName()
	{
		return "Alignment with duplication n^3";
	}

	public boolean isLocal()
	{
		return alignType == OptimumPathMethod.LOCAL;
	}

	public boolean isGlobal()
	{
		return alignType == OptimumPathMethod.GLOBAL;
	}

	public boolean isSemiGlobal()
	{
		return alignType == OptimumPathMethod.SEMIGLOBAL;
	}

	public static int max(int a, int b, int c)
	{
		if (a >= b && a >= c)
		{
			return a;
		}
		else if (b >= c)
		{
			return b;
		}
		else
		{
			return c;
		}
	}

}

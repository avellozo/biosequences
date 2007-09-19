package sequences.bim.n3lgn;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDP;
import sequences.editgraph.EGInternalException;
import sequences.editgraph.EGInvalidArcException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntComposite;
import sequences.matrix.MatrixIntTreeJeanette;
import sequences.matrix.RowInt;
import sequences.matrix.TMMatrixInt;

public class PathBimN3lgn<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>>
		extends PathBimDP<E>
{

	public PathBimN3lgn(VertexRange<E> range, boolean local) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		super(range, local);
	}

	@Override
	protected void init()
	{
		super.init();
	}

	@Override
	protected void buildMatrix()
	{
		TreeJeanette treeUp, treeLeft, treeUpLeft;
		int wUp, wLeft, wUpLeft;
		MatrixInt r;
		int[] t = new int[cols];
		int[] tIndex = new int[cols];
		TreeJeanette[] trees = new TreeJeanette[cols];

		int i, i1, j, extendedScore;
		int bimWithInversioRowI; // value of the bim of the best inversion
		// that begin in the row i
		int colBimWithInversionRowI;// column of the bim of the best inversion
		// that begin in the row i

		try
		{
			setBim(iMin, jMin, 0, (char) 0);
			setExtendedScore(iMin, jMin, iMin, jMin, 0);

			// Calcula o Bim da linha zero
			i = iMin;
			bimWithInversioRowI = bim.getValue(i, jMin) - inversionPenalty;
			colBimWithInversionRowI = jMin;
			for (j = jMin + 1; j <= jMax; j++)
			{
				// set the bim value
				setBim(i, j, bim.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
				// set the bimWithInversion value
				extendedScore = extender.getEGExtenderWeightHorizontalArc(i + 1, j);
				if ((bimWithInversioRowI + extendedScore) < (bim.getValue(i, j - 1) - inversionPenalty + extendedScore))
				{
					// it is better if begins a new inversion
					bimWithInversioRowI = bim.getValue(i, j - 1) - inversionPenalty + extendedScore;
					colBimWithInversionRowI = j - 1;
				}
				else
				{
					// it is better if continues the inversion
					bimWithInversioRowI += extendedScore;
				}
				setBimWithInversion(i, colBimWithInversionRowI, i, j, bimWithInversioRowI);
			}

			// Calcula o Bim das outras linhas
			for (i = iMin + 1; i <= iMax; i++)
			{
				j = jMin;
				// b[i][0] = b[i - 1][0] + eg.getWeightVertical(i, 0);
				setBim(i, j, bim.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
				// Calculate the bim value based on calculated bim values
				for (j = jMin + 1; j <= jMax; j++)
				{
					setBim(i, j, bim.getValue(i - 1, j - 1) + eg.getWeightDiagonalArc(i, j), EditGraph.DIAGONAL);
					updateBim(i, j, bim.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
				}

				// Calculate the bim value such that the last operation is an inversion
				treeUp = null;
				for (i1 = i + 1; i1 >= iMin + 1; i1--)
				{
					treeLeft = null;
					treeUpLeft = null;
					// cria as árvores da linha i1
					for (j = jMin; j <= jMax; j++)
					{
						if (i1 <= i)
						{
							treeUp = trees[j];
						}
						wUp = treeUp == null ? 0 : extender.getEGExtenderWeightVerticalArc(i1, j);
						wUpLeft = treeUpLeft == null ? 0 : extender.getEGExtenderWeightDiagonalArc(i1, j);
						wLeft = treeLeft == null ? 0 : extender.getEGExtenderWeightHorizontalArc(i1, j);
						trees[j] = new TreeJeanette(treeUp, treeUpLeft, treeLeft, wUp, wUpLeft, wLeft);
						treeLeft = trees[j];
						if (i1 <= i)
						{
							treeUpLeft = treeUp;
						}
					}
					for (j = jMin; j <= jMax; j++)
					{
						t[j] = bim.getValue(i1 - 1, j);
					}
					r = new MatrixIntTreeJeanette(trees);
					r = new MatrixIntComposite(r, t, null);
					r = new TMMatrixInt(r);
					RowInt[] rowsMax = ((TMMatrixInt) r).getRowsMaxValueColumns();
					// rowsMax[j] = linha que contém o valor máximo da coluna j
					t = ((TMMatrixInt) r).getMaxColValues(rowsMax);
					// t[j] = o valor máximo da coluna j
					tIndex = ((TMMatrixInt) r).getIndexMaxColValues(rowsMax);
					// tIndex[j] = índice da linha que contém o valor máximo da coluna j
					for (j = jMin; j <= jMax; j++)
					{
						if (i1 == i + 1)
						{
							setBimWithInversion(i1 - 1, tIndex[j], i, j, t[j] - inversionPenalty);
						}
						else
						{
							updateBimWithInversion(i1 - 1, tIndex[j], i, j, t[j] - inversionPenalty);
						}
					}
				}

				// Calculate the bim value based on bim values on row i and already finished the calculation
				bimWithInversioRowI = bim.getValue(i, jMin) - inversionPenalty;
				colBimWithInversionRowI = jMin;
				for (j = jMin + 1; j <= jMax; j++)
				{
					// set the bim value
					updateBim(i, j, bim.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
					// set the bimWithInversion value
					extendedScore = extender.getEGExtenderWeightHorizontalArc(i + 1, j);
					if ((bimWithInversioRowI + extendedScore) < (bim.getValue(i, j - 1) - inversionPenalty + extendedScore))
					{
						// it is better if begins a new inversion
						bimWithInversioRowI = bim.getValue(i, j - 1) - inversionPenalty + extendedScore;
						colBimWithInversionRowI = j - 1;
					}
					else
					{
						// it is better if continues the inversion
						bimWithInversioRowI += extendedScore;
					}
					updateBimWithInversion(i, colBimWithInversionRowI, i, j, bimWithInversioRowI);
				}
			}
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

}

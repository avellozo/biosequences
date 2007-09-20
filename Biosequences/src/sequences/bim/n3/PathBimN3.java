package sequences.bim.n3;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDP;
import sequences.editgraph.EGInternalException;
import sequences.editgraph.EGInvalidArcException;
import sequences.editgraph.EGInvalidEditGraphException;
import sequences.editgraph.EGInvalidRangeException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.VertexRange;

public class PathBimN3<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>> extends
		PathBimDP<E>
{

	int[]	dist;

	public PathBimN3(VertexRange<E> range, boolean local) throws EGInvalidRangeException, EGInvalidEditGraphException
	{
		super(range, local);
	}

	@Override
	protected void init()
	{
		super.init();
		dist = new int[cols];
	}

	@Override
	protected void buildMatrix()
	{
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
				Out[] out;
				StepCollection[] blhLast = null;
				for (i1 = i + 1; i1 > iMin; i1--)
				{
					//TODO talvez o loop deva começar com i???
					blhLast = buildBorderline(blhLast, i1);
					out = getAlignment(blhLast, i1);
					for (j = jMin; j <= jMax; j++)
					{
						if (i1 == i + 1)
						{
							setBimWithInversion(i1 - 1, out[j].getJ(), i, j, out[j].getValue() - inversionPenalty);
						}
						else
						{
							updateBimWithInversion(i1 - 1, out[j].getJ(), i, j, out[j].getValue() - inversionPenalty);
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

	protected StepCollection[] buildBorderline(StepCollection[] blhPrev, int i)
	{
		StepCollection[] blh = new StepCollection[jMax + 1];
		int j;
		try
		{
			if (blhPrev == null) // i+1 == i1
			{
				blh[jMin] = new StepArray(0);
				dist[jMin] = 0;
				for (j = jMin + 1; j <= jMax; j++)
				{
					blh[j] = new StepArray(1);
					blh[j].add(new Step(jMin, extender.getEGExtenderWeightHorizontalArc(i, j)));
					dist[j] = 0;
				}
				return blh;
			}
			StepCollection[] blv = new StepCollection[jMax + 1];
			for (j = jMin; j <= jMax; j++)
			{
				dist[j] += extender.getEGExtenderWeightVerticalArc(i, j);
				blh[j] = new StepArray();
				blv[j] = new StepArray();
				int alfaH = 0, alfaH1 = 0, alfaV = 0, alfaV1 = 0, j1 = jMin, max;
				while (j1 < j)
				{
					max = max(blv[j - 1].get(alfaV1).getDelta() + extender.getEGExtenderWeightHorizontalArc(i, j),
						extender.getEGExtenderWeightDiagonalArc(i, j), blhPrev[j].get(alfaH1).getDelta()
							+ extender.getEGExtenderWeightVerticalArc(i, j));
					if (alfaH == 0 || (blh[j].get(alfaH - 1).getDelta() != (max - blv[j - 1].get(alfaV1).getDelta())))
					{
						blh[j].add(new Step(j1, max - blv[j - 1].get(alfaV1).getDelta()));
						alfaH++;
					}
					if (alfaV == 0 || (blv[j].get(alfaV - 1).getDelta() != (max - blhPrev[j].get(alfaH1).getDelta())))
					{
						blv[j].add(new Step(j1, max - blhPrev[j].get(alfaH1).getDelta()));
						alfaV++;
					}
					// nextBorderLine
					if (alfaH1 == blhPrev[j].size() - 1 && alfaV1 == blv[j - 1].size() - 1)
					{
						j1 = j;
					}
					else if (alfaH1 == blhPrev[j].size() - 1)
					{
						alfaV1++;
						j1 = blv[j - 1].get(alfaV1).getJ();
					}
					else if (alfaV1 == blv[j - 1].size() - 1)
					{
						alfaH1++;
						j1 = blhPrev[j].get(alfaH1).getJ();
					}
					else if (blhPrev[j].get(alfaH1 + 1).getJ() <= blv[j - 1].get(alfaV1 + 1).getJ())
					{
						alfaH1++;
						j1 = blhPrev[j].get(alfaH1).getJ();
					}
					else
					{
						alfaV1++;
						j1 = blv[j - 1].get(alfaV1).getJ();
					}
				}
				if (alfaV == 0 || blv[j].get(alfaV - 1).getDelta() != extender.getEGExtenderWeightVerticalArc(i, j))
				{
					blv[j].add(new Step(j1, extender.getEGExtenderWeightVerticalArc(i, j)));
					alfaV++;
				}
			}
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}

		return blh;
	}

	protected Out[] getAlignment(StepCollection[] blh, int i1)
	{
		int step;
		Candidate j1, j2;
		Out[] out = new Out[jMax + 1];
		CandidateList cList = new CandidateListDisjointSet(jMax + 1);
		for (int j = jMin; j <= jMax; j++)
		{
			cList.add(j, bim.getValue(i1 - 1, j) + dist[j]);
			if (j != jMin)
			{
				blh[j].add(new Step(j, -(dist[j - 1] + bim.getValue(i1 - 1, j - 1))));
				// TODO verificar se não interfere no uso de blhPrev
			}
			else
			{
				blh[j].add(new Step(j, 0));
			}
			for (int alfa = 0; alfa < blh[j].size(); alfa++)
			{
				step = blh[j].get(alfa).getDelta();
				if (alfa != 0)
				{
					step = step - blh[j].get(alfa - 1).getDelta();
				}
				j1 = cList.find(blh[j].get(alfa).getJ());
				j1.setGap(j1.getGap() + step);
				while (j1.getGap() >= 0 && j1 != cList.getFirst())
				{
					j2 = cList.getPrevious(j1);
					j1.setGap(j1.getGap() + j2.getGap());
					cList.remove(j2);
					j1 = cList.find(j1.getJ()); // é preciso devido à
					// implementação do remove
				}
			}
			out[j] = new Out(cList.getFirst().getJ(), cList.getFirst().getGap());
		}
		return out;
	}
}

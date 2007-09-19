package sequences.dup;

import sequences.common.SequenceInternalException;
import sequences.editgraph.Arc;
import sequences.editgraph.EGGeneralException;
import sequences.editgraph.EGInternalException;
import sequences.editgraph.EGInvalidArcException;
import sequences.editgraph.EGInvalidVertexException;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntDP;
import sequences.matrix.MatrixIntPrimitive;

public class PathDupDP<E extends EditGraph<E, ? extends ExtenderDup<E>>> extends
		PathDup<E>
{
	protected int[][]		mij;

	protected int[][]		inversionsI;

	protected int[][]		inversionsJ;

	protected int[][]		dupWithInversion;

	protected char[][]		arcsType	= null;

	// The matrix used to calculate this path during the dynamic programming
	// algorithm
	protected MatrixIntDP	dup;

	public PathDupDP(VertexRange<E> range, boolean local) throws EGInvalidVertexException, EGGeneralException
	{
		super(range, local);
		init();
		buildMatrix();
		buildPath();
		finish();
	}

	protected void init()
	{
		arcsType = new char[rows][cols];
		dup = new MatrixIntDP(new MatrixIntPrimitive(rows, cols), isLocal());
		inversionsI = new int[rows][cols];
		inversionsJ = new int[rows][cols];
		dupWithInversion = new int[rows][cols];
	}

	protected void finish()
	{
		finishTime();
	}

	protected void buildMatrix()
	{
		// Method O(N^4)
		int i, j;

		mij = new int[rows][cols];
		try
		{
			setDup(iMin, jMin, 0, (char) 0);
			afterSimpleAlignment(iMin, jMin);
			// Calcula o Dup da linha zero
			i = iMin;
			for (j = jMin+1; j <= jMax; j++)
			{
				updateDup(i, j, dup.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
				afterSimpleAlignment(i, j);
			}
			// Calcula o Dup das outras linhas
			for (i = iMin+1; i <= iMax; i++)
			{
				// coluna zero
				// b[i][0] = b[i - 1][0] + eg.getWeightVertical(i, 0);
				// Calcula o Dup da linha i tal que a última operação não é
				// inversão
				for (j = jMin; j <= jMax; j++)
				{
					if (j == jMin)
					{
						setDup(i, j, dup.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
					}
					else
					{
						updateDup(i, j, dup.getValue(i - 1, j - 1) + eg.getWeightDiagonalArc(i, j), EditGraph.DIAGONAL);
						updateDup(i, j, dup.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
						updateDup(i, j, dup.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
					}
					afterSimpleAlignment(i, j);
				}
			}
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	// method n4
	protected void afterSimpleAlignment(int i, int j)
	{
		int i1, j1, wv, wh, wd;
		// Calcula o Dup da linha i tal que a última operação é inversão
		try
		{
			i1 = i;
			j1 = j;
			mij[i][j] = 0;
			//set extended weight of row i
			if (j == jMin)
			{
				// initialize the dupWithInversion and the dup for this row.
				setExtendedScore(i1, j, i, j1, mij[i1][j1]);
				// dup[i][0] must be already setted
				for (j1 = j + 1; j1 <= jMax; j1++)
				{
					mij[i1][j1] = mij[i1][j1 - 1] + extender.getEGExtenderWeightHorizontalArc(i1 + 1, j1);
					// sets the dup value and the dup with inversion
					setDup(i, j1, Integer.MIN_VALUE, (char) 0);
					setExtendedScore(i1, j, i, j1, mij[i1][j1]);
				}
			}
			else
			{
				updateExtendedScore(i1, j, i, j1, mij[i1][j1]);
				for (j1 = j + 1; j1 <= jMax; j1++)
				{
					mij[i1][j1] = mij[i1][j1 - 1] + extender.getEGExtenderWeightHorizontalArc(i1 + 1, j1);
					updateExtendedScore(i1, j, i, j1, mij[i1][j1]);
				}
			}
			//set extended weight of another row
			for (i1 = i - 1; i1 >= (iMin+1); i1--)
			{
				j1 = j;
				mij[i1][j1] = mij[i1 + 1][j1] + extender.getEGExtenderWeightVerticalArc(i1 + 1, j1);
				updateExtendedScore(i1, j, i, j1, mij[i1][j1]);
				for (j1 = j + 1; j1 <= jMax; j1++)
				{
					wv = mij[i1 + 1][j1] + extender.getEGExtenderWeightVerticalArc(i1 + 1, j1);
					wh = mij[i1][j1 - 1] + extender.getEGExtenderWeightHorizontalArc(i1 + 1, j1);
					wd = mij[i1 + 1][j1 - 1] + extender.getEGExtenderWeightDiagonalArc(i1 + 1, j1);
					mij[i1][j1] = max(wv, wh, wd);
					updateExtendedScore(i1, j, i, j1, mij[i1][j1]);
				}
			}
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	protected void setExtendedScore(int bi, int bj, int ei, int ej, int extendedScore)
	{
		setDupWithInversion(bi, bj, ei, ej, dup.getValue(bi, bj) + extendedScore - inversionPenalty);
	}

	protected void updateExtendedScore(int bi, int bj, int ei, int ej, int extendedScore)
	{
		updateDupWithInversion(bi, bj, ei, ej, dup.getValue(bi, bj) + extendedScore - inversionPenalty);
	}

	protected void setDupWithInversion(int bi, int bj, int ei, int ej, int value)
	{
		dupWithInversion[ei][ej] = value;
		inversionsI[ei][ej] = bi;
		inversionsJ[ei][ej] = bj;
		updateDup(ei, ej, value, EditGraph.EXTENDED);
	}

	protected void updateDupWithInversion(int bi, int bj, int ei, int ej, int value)
	{
		if (dupWithInversion[ei][ej] < value)
		{
			dupWithInversion[ei][ej] = value;
			inversionsI[ei][ej] = bi;
			inversionsJ[ei][ej] = bj;
			updateDup(ei, ej, value, EditGraph.EXTENDED);
		}
	}

	protected void updateDup(int i, int j, int value, char arcType)
	{
		if (dup.getValue(i, j) < value)
		{
			dup.setValue(i, j, value);
			arcsType[i][j] = arcType;
		}
	}

	protected void setDup(int i, int j, int value, char arcType)
	{
		dup.setValue(i, j, value);
		arcsType[i][j] = arcType;
	}

	protected void buildPath()
	{
		// construir o path
		try
		{
			Vertex<E> v = getVertexRange().getEndVertex();
			if (isLocal())
			{
				v = eg.getVertex(dup.getRowMaxValue(), dup.getColMaxValue());
			}
			char c;
			Arc<E> arc;
			while (!((v.equals(getVertexRange().getBeginVertex())) || (isLocal() && (dup.getValue(v.getI(), v.getJ()) == 0))))
			{
				c = arcsType[v.getI()][v.getJ()];
				switch (c)
				{
				case EditGraph.VERTICAL:
					arc = eg.getVerticalArc(v);
					break;
				case EditGraph.HORIZONTAL:
					arc = eg.getHorizontalArc(v);
					break;
				case EditGraph.DIAGONAL:
					arc = eg.getDiagonalArc(v);
					break;
				case EditGraph.EXTENDED:
					try
					{
						arc = eg.getExtendedArc(new VertexRange<E>(eg.getVertex(inversionsI[v.getI()][v.getJ()],
							inversionsJ[v.getI()][v.getJ()]), v));
					}
					catch (EGGeneralException e)
					{
						e.printStackTrace();
						throw new SequenceInternalException();
					}
					break;
				default:
					throw new RuntimeException("Tipo de arco inválido: " + c);
				}
				addFirst(arc);
				v = arc.getBeginVertex();
			}
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
		catch (EGInvalidVertexException e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
	}

	public MatrixInt getMatrixDup()
	{
		return dup;
	}

	public int[][] getInversionsI()
	{
		return inversionsI;
	}

	public int[][] getInversionsJ()
	{
		return inversionsJ;
	}

	public char[][] getArcsType()
	{
		return arcsType;
	}

	public int[][] getDupWithInversion()
	{
		return dupWithInversion;
	}

}

package sequences.bim;

import sequences.common.SequenceInternalException;
import sequences.editgraph.Arc;
import sequences.editgraph.ExceptionInternalEG;
import sequences.editgraph.ExceptionInvalidArc;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.EditGraphSegment;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntDP;
import sequences.matrix.MatrixIntPrimitive;

public class PathBimDP<E extends EditGraph<E, ? extends ExtenderUsingEGInvertedRows<E, ? extends EditGraph>>> extends
		PathBim
{
	protected int[][]		mij;

	protected int[][]		inversionsI;

	protected int[][]		inversionsJ;

	protected int[][]		bimWithInversion;

	protected char[][]		arcsType	= null;

	// The matrix used to calculate this path during the dynamic programming
	// algorithm
	protected MatrixIntDP	bim;

	public PathBimDP(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException, ExceptionInvalidEditGraph
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
		bim = new MatrixIntDP(new MatrixIntPrimitive(rows, cols), isLocal());
		inversionsI = new int[rows][cols];
		inversionsJ = new int[rows][cols];
		bimWithInversion = new int[rows][cols];
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
			setBim(iMin, jMin, 0, (char) 0);
			afterSimpleAlignment(iMin, jMin);
			// Calcula o Bim da linha zero
			i = iMin;
			for (j = jMin+1; j <= jMax; j++)
			{
				updateBim(i, j, bim.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
				afterSimpleAlignment(i, j);
			}
			// Calcula o Bim das outras linhas
			for (i = iMin+1; i <= iMax; i++)
			{
				// coluna zero
				// b[i][0] = b[i - 1][0] + eg.getWeightVertical(i, 0);
				// Calcula o Bim da linha i tal que a última operação não é
				// inversão
				for (j = jMin; j <= jMax; j++)
				{
					if (j == jMin)
					{
						setBim(i, j, bim.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
					}
					else
					{
						updateBim(i, j, bim.getValue(i - 1, j - 1) + eg.getWeightDiagonalArc(i, j), EditGraph.DIAGONAL);
						updateBim(i, j, bim.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j), EditGraph.VERTICAL);
						updateBim(i, j, bim.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j), EditGraph.HORIZONTAL);
					}
					afterSimpleAlignment(i, j);
				}
			}
		}
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	// method n4
	protected void afterSimpleAlignment(int i, int j)
	{
		int i1, j1, wv, wh, wd;
		// Calcula o Bim da linha i tal que a última operação é inversão
		try
		{
			i1 = i;
			j1 = j;
			mij[i][j] = 0;
			//set extended weight of row i
			if (j == jMin)
			{
				// initialize the bimWithInversion and the bim for this row.
				setExtendedScore(i1, j, i, j1, mij[i1][j1]);
				// bim[i][0] must be already setted
				for (j1 = j + 1; j1 <= jMax; j1++)
				{
					mij[i1][j1] = mij[i1][j1 - 1] + extender.getEGExtenderWeightHorizontalArc(i1 + 1, j1);
					// sets the bim value and the bim with inversion
					setBim(i, j1, Integer.MIN_VALUE, (char) 0);
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
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	protected void setExtendedScore(int bi, int bj, int ei, int ej, int extendedScore)
	{
		setBimWithInversion(bi, bj, ei, ej, bim.getValue(bi, bj) + extendedScore - inversionPenalty);
	}

	protected void updateExtendedScore(int bi, int bj, int ei, int ej, int extendedScore)
	{
		updateBimWithInversion(bi, bj, ei, ej, bim.getValue(bi, bj) + extendedScore - inversionPenalty);
	}

	protected void setBimWithInversion(int bi, int bj, int ei, int ej, int value)
	{
		bimWithInversion[ei][ej] = value;
		inversionsI[ei][ej] = bi;
		inversionsJ[ei][ej] = bj;
		updateBim(ei, ej, value, EditGraph.EXTENDED);
	}

	protected void updateBimWithInversion(int bi, int bj, int ei, int ej, int value)
	{
		if (bimWithInversion[ei][ej] < value)
		{
			bimWithInversion[ei][ej] = value;
			inversionsI[ei][ej] = bi;
			inversionsJ[ei][ej] = bj;
			updateBim(ei, ej, value, EditGraph.EXTENDED);
		}
	}

	protected void updateBim(int i, int j, int value, char arcType)
	{
		if (bim.getValue(i, j) < value)
		{
			bim.setValue(i, j, value);
			arcsType[i][j] = arcType;
		}
	}

	protected void setBim(int i, int j, int value, char arcType)
	{
		bim.setValue(i, j, value);
		arcsType[i][j] = arcType;
	}

	protected void buildPath()
	{
		// construir o path
		try
		{
			Vertex v = getVertexRange().getEndVertex();
			if (isLocal())
			{
				v = eg.getVertex(bim.getRowMaxValue(), bim.getColMaxValue());
			}
			char c;
			Arc arc;
			while (!((v.equals(getVertexRange().getBeginVertex())) || (isLocal() && (bim.getValue(v.getRow(), v.getCol()) == 0))))
			{
				c = arcsType[v.getRow()][v.getCol()];
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
						arc = eg.getExtendedArc(new EditGraphSegment(eg.getVertex(inversionsI[v.getRow()][v.getCol()],
							inversionsJ[v.getRow()][v.getCol()]), v));
					}
					catch (ExceptionInvalidVertex e)
					{
						e.printStackTrace();
						throw new SequenceInternalException();
					}
					catch (EGInvalidVertexesOfExtensionException e)
					{
						e.printStackTrace();
						throw new SequenceInternalException();
					}
					catch (ExceptionInvalidEditGraph e)
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
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new SequenceInternalException();
		}
	}

	public MatrixInt getMatrixBim()
	{
		return bim;
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

	public int[][] getBimWithInversion()
	{
		return bimWithInversion;
	}

}

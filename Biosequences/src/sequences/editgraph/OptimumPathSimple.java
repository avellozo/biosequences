package sequences.editgraph;

import sequences.common.SequenceInternalException;
import sequences.matrix.MatrixIntDP;
import sequences.matrix.MatrixIntPrimitive;

public class OptimumPathSimple extends OptimumPathImpl
{

	protected char[][]		arcsType;

	// The matrix used to calculate this path during the dynamic programming
	// algorithm
	protected MatrixIntDP	matrixDP;

	public OptimumPathSimple(VertexRange range, boolean local) throws ExceptionInvalidVertex
	{
		super(range, local);
		// TODO melhorar memória usando matrizes range
		// arcsType = new MatrixCharRange(new char[rows][cols], iMin, jMin);
		// matrixDP = new MatrixIntRange(new MatrixIntDP(new
		// MatrixIntPrimitive(rows, cols), isLocal()), iMin, jMin);
		arcsType = new char[iMax + 1][jMax + 1];
		matrixDP = new MatrixIntDP(new MatrixIntPrimitive(iMax + 1, jMax + 1), isLocal());
		buildBimMatrix();
		buildPath();
		finishTime();
	}

	private void buildBimMatrix()
	{
		int i, j;
		int wh = 0, wd = 0, wv = 0;

		try
		{
			matrixDP.setValue(iMin, jMin, 0);
			arcsType[iMin][jMin] = 0;
			// Calcula o Bim da linha zero
			for (j = jMin + 1; j <= jMax; j++)
			{
				matrixDP.setValue(iMin, j, matrixDP.getValue(iMin, j - 1) + eg.getWeightHorizontalArc(iMin, j));
				arcsType[iMin][j] = EditGraph.HORIZONTAL;
			}
			for (i = iMin + 1; i <= iMax; i++)
			{
				// coluna zero
				// b[i][0] = b[i - 1][0] + eg.getWeightVertical(i, 0);
				for (j = jMin; j <= jMax; j++)
				{
					wv = matrixDP.getValue(i - 1, j) + eg.getWeightVerticalArc(i, j);
					if (j != jMin)
					{
						wh = matrixDP.getValue(i, j - 1) + eg.getWeightHorizontalArc(i, j);
						wd = matrixDP.getValue(i - 1, j - 1) + eg.getWeightDiagonalArc(i, j);
					}
					if ((j == jMin) || ((wv > wd) && (wv >= wh)))
					{
						matrixDP.setValue(i, j, wv);
						arcsType[i][j] = EditGraph.VERTICAL;
					}
					else if ((wh > wv) && (wh > wd))
					{
						matrixDP.setValue(i, j, wh);
						arcsType[i][j] = EditGraph.HORIZONTAL;
					}
					else
					{
						matrixDP.setValue(i, j, wd);
						arcsType[i][j] = EditGraph.DIAGONAL;
					}
				}
			}
		}
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	protected void buildPath()
	{
		// construir o path
		try
		{
			Vertex v = getVertexRange().getEndVertex();
			if (isLocal())
			{
				v = eg.getVertex(matrixDP.getRowMaxValue(), matrixDP.getColMaxValue());
			}
			char c;
			Arc arc;
			while (!((v.equals(getVertexRange().getBeginVertex())) || (isLocal() && (matrixDP.getValue(v.getRow(),
				v.getCol()) == 0))))
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

}

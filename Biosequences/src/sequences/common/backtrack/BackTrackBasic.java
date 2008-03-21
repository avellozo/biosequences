/*
 * Created on 17/03/2008
 */
package sequences.common.backtrack;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphExtended;
import sequences.editgraph.EditGraphWithGapSet;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixByteRange;
import sequences.matrix.MatrixInt;

public class BackTrackBasic implements BackTrack
{

	byte				typeAlign;

	BackTrackMatrixes	matrixValue;

	BackTrackExtension	backTrackExtension;
	BackTrackJunction	backTrackJunction;
	BackTrackGapSet		backtrackGapSet;

	public BackTrackBasic(int rowBegin, int colBegin, int rowEnd, int colEnd, byte type,
			BackTrackGapSet backtrackGapSet, BackTrackExtension backTrackExtension, BackTrackJunction backTrackJunction)
	{
		createDataStrutucture(rowBegin, colBegin, rowEnd, colEnd, type);
		this.backtrackGapSet = backtrackGapSet;
		this.backTrackExtension = backTrackExtension;
		this.backTrackJunction = backTrackJunction;
	}

	public BackTrackBasic(BackTrackMatrixes matrixes, BackTrackGapSet backtrackGapSet,
			BackTrackExtension backTrackExtension, BackTrackJunction backTrackJunction)
	{
		this.matrixValue = matrixes;
		this.backtrackGapSet = backtrackGapSet;
		this.backTrackExtension = backTrackExtension;
		this.backTrackJunction = backTrackJunction;
	}

	public MatrixInt getMatrixValues()
	{
		return matrixValue.getMatrixValues();
	}

	public MatrixByteRange getMatrixArcsType()
	{
		return matrixValue.getMatrixArcsType();
	}

	public int getValue(int row, int col)
	{
		return matrixValue.getValue(row, col);
	}

	public byte getArcType(int row, int col)
	{
		return matrixValue.getArcType(row, col);
	}

	protected static BackTrackMatrixes createDataStrutucture(int rowBegin, int colBegin, int rowEnd, int colEnd,
			byte type)
	{
		switch (type)
		{
		case OptimumPathMethod.LOCAL:
			return new BTMatrixesLocal(rowBegin, colBegin, rowEnd, colEnd);
		case OptimumPathMethod.GLOBAL:
			return new BTMatrixesGlobal(rowBegin, colBegin, rowEnd, colEnd);
		case OptimumPathMethod.SEMIGLOBAL:
			return new BTMatrixesSemiGlobal(rowBegin, colBegin, rowEnd, colEnd);
		default:
			throw new RuntimeException("Invalid alignment type: " + type);
		}
	}

	public void updateDiagonal(int row, int col, int value)
	{
		updateValue(row, col, value, DIAGONAL);
	}

	public void updateHorizontal(int row, int col, int value)
	{
		updateValue(row, col, value, HORIZONTAL);
	}

	public void updateVertical(int row, int col, int value)
	{
		updateValue(row, col, value, VERTICAL);
	}

	public void updateJunction(int rowBegin, int colBegin, int rowEnd, int colEnd) throws ExceptionInvalidVertex
	{
		int value = getMatrixValues().getValue(rowBegin, colBegin);
		updateValue(rowEnd, colEnd, value, JUNCTION);
		backTrackJunction.setJunction(rowBegin, colBegin, rowEnd, colEnd, value);
	}

	public void updateExtended(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
	{
		updateValue(rowEnd, colEnd, value, EXTENDED);
		backTrackExtension.setExtension(rowBegin, colBegin, rowEnd, colEnd, value);
	}

	public void updateGapSetHorizontal(int colBegin, int row, int colEnd, int value)
	{
		updateValue(row, colEnd, value, GAP_HOR);
		backtrackGapSet.setGapHor(colBegin, row, colEnd, value);
	}

	public void updateGapSetVertical(int rowBegin, int rowEnd, int col, int value)
	{
		updateValue(rowEnd, col, value, GAP_VER);
		backtrackGapSet.setGapVer(rowBegin, rowEnd, col, value);
	}

	public void updateDiagonal(int row, int col, EditGraph eg) throws ExceptionInvalidVertex
	{
		updateDiagonal(row, col, getValue(row - 1, col - 1) + eg.getWeightDiagonalArc(row, col));
	}

	public void updateHorizontal(int row, int col, EditGraph eg) throws ExceptionInvalidVertex
	{
		updateHorizontal(row, col, getValue(row, col - 1) + eg.getWeightHorizontalArc(row, col));
	}

	public void updateVertical(int row, int col, EditGraph eg) throws ExceptionInvalidVertex
	{
		updateVertical(row, col, getValue(row - 1, col) + eg.getWeightVerticalArc(row, col));
	}

	// Assume que ou continua o anterior gap set ou faz um novo
	public void updateGapSetHorizontal(int row, int colEnd, EditGraphWithGapSet eg) throws ExceptionInvalidVertex
	{
		int colOpt, valOpt;
		if (colEnd - 1 == eg.getColMin())
		{
			colOpt = colEnd - 1;
			valOpt = getValue(row, colOpt) + eg.getGapSetHorizontalWeight(colOpt, row, colEnd);
		}
		else
		{
			colOpt = getOptColGapSetHor(row, colEnd - 1);
			valOpt = getValue(row, colOpt) + eg.getGapSetHorizontalWeight(colOpt, row, colEnd);
			int valColAnt = getValue(row, colEnd - 1) + eg.getGapSetHorizontalWeight(colEnd - 1, row, colEnd);
			if (valOpt < valColAnt)
			{
				colOpt = colEnd - 1;
				valOpt = valColAnt;
			}
		}
		updateGapSetHorizontal(colOpt, row, colEnd, valOpt);
	}

	// Assume que ou continua o anterior gap set ou faz um novo
	public void updateGapSetVertical(int rowEnd, int col, EditGraphWithGapSet eg) throws ExceptionInvalidVertex
	{
		int rowOpt, valOpt;
		if (rowEnd - 1 == eg.getRowMin())
		{
			rowOpt = rowEnd - 1;
			valOpt = getValue(rowOpt, col) + eg.getGapSetHorizontalWeight(rowOpt, rowEnd, col);
		}
		else
		{
			rowOpt = getOptRowGapSetVer(rowEnd - 1, col);
			valOpt = getValue(rowOpt, col) + eg.getGapSetVerticalWeight(rowOpt, rowEnd, col);
			int valRowAnt = getValue(rowEnd - 1, col) + eg.getGapSetVerticalWeight(rowEnd - 1, rowEnd, col);
			if (valOpt < valRowAnt)
			{
				rowOpt = rowEnd - 1;
				valOpt = valRowAnt;
			}
		}
		updateGapSetVertical(rowOpt, rowEnd, col, valOpt);
	}

	public Vertex getOptVertexExtended(int rowEnd, int colEnd) throws ExceptionInvalidVertex
	{
		return backTrackExtension.getOptVertexExtended(rowEnd, colEnd);
	}

	public int getOptColGapSetHor(int rowEnd, int colEnd) throws ExceptionInvalidVertex
	{
		return backtrackGapSet.getOptColGapSetHor(rowEnd, colEnd);
	}

	public int getOptRowGapSetVer(int rowEnd, int colEnd) throws ExceptionInvalidVertex
	{
		return backtrackGapSet.getOptRowGapSetVer(rowEnd, colEnd);
	}

	public OptimumPath getOptimumPath(EditGraph eg)
	{
		OptimumPathImpl path = new OptimumPathImpl(eg);
		Vertex v = getOptimumVertex();
		Arc arc;
		try
		{
			while (!isBeginOptimumPath(v.getRow(), v.getCol()))
			{
				arc = getArc(v, eg);
				path.addFirst(arc);
				v = arc.getBeginVertex();
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		return path;
	}

	protected void updateValue(int row, int col, int value, byte type)
	{
		matrixValue.updateValue(row, col, value, type);
	}

	public Vertex getOptimumVertex()
	{
		return matrixValue.getOptimumVertex();
	}

	public boolean isBeginOptimumPath(int row, int col)
	{
		return matrixValue.isBeginOptimumPath(row, col);
	}

	public Arc getArc(Vertex v, EditGraph eg) throws ExceptionInvalidVertex
	{
		Arc arc;
		byte b = matrixValue.getArcType(v.getRow(), v.getCol());
		switch (b)
		{
		case DIAGONAL:
			arc = eg.getDiagonalArc(v);
			break;
		case VERTICAL:
			arc = eg.getVerticalArc(v);
			break;
		case HORIZONTAL:
			arc = eg.getHorizontalArc(v);
			break;
		case EXTENDED:
			arc = backTrackExtension.getExtendedArc(v, (EditGraphExtended) eg);
			break;
		case JUNCTION:
			arc = backTrackJunction.getArcJuntion(v);
			break;
		case GAP_HOR:
			arc = backtrackGapSet.getArcGapHor(v, (EditGraphWithGapSet) eg);
			break;
		case GAP_VER:
			arc = backtrackGapSet.getArcGapVer(v, (EditGraphWithGapSet) eg);
			break;
		default:
			throw new RuntimeException("Tipo de arco inválido: " + b);
		}
		return arc;
	}

	public void setBeginPath(int row, int col, int value)
	{
		matrixValue.setBeginOptimumPath(row, col, value);
	}

}

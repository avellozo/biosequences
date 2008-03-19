/*
 * Created on 17/03/2008
 */
package sequences.common;

import sequences.editgraph.Arc;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathImpl;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcJuntion;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixByteRange;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;
import sequences.matrix.MatrixIntRange;

public abstract class BackTrackGlobal implements BackTrack
{
	public static final byte	VERTICAL	= 'V';

	public static final byte	HORIZONTAL	= 'H';

	public static final byte	DIAGONAL	= 'D';

	public static final byte	EXTENDED	= 'E';

	public static final byte	JUNCTION	= 'J';	//arco sem peso que junta dois vértices

	public static final byte	GAP_HOR		= 'R';	//Gap estendido horizontal

	public static final byte	GAP_VERT	= 'C';	//Gap estendido vertical

	public static final byte	INVALID		= 'I';

	public static final byte	EMPTY		= 0;

	MatrixInt					m;
	protected MatrixByteRange	arcsType;
	String						nameMethod;

	BackTrackExtension			backTrackExtension;
	BackTrackExtension			backTrackJunction;

	public BackTrackGlobal(int rowBegin, int colBegin, int rowEnd, int colEnd, String nameMethod,
			BackTrackExtension backTrackExtension, BackTrackExtension backTrackJunction)
	{
		m = new MatrixIntPrimitive(rowEnd - rowBegin + 1, colEnd - colBegin + 1);
		if (rowBegin != 0 || colBegin != 0)
		{
			m = new MatrixIntRange(m, rowBegin, colBegin, rowEnd, colEnd);
		}
		arcsType = new MatrixByteRange(rowBegin, colBegin, rowEnd, colEnd);

		this.nameMethod = nameMethod;
		this.backTrackExtension = backTrackExtension;
		this.backTrackJunction = backTrackJunction;
	}

	public MatrixInt getMatrix()
	{
		return m;
	}

	public void setDiagonal(int row, int col, int value)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == EMPTY)
		{
			m.setValue(row, col, value);
			arcsType.setValue(row, col, DIAGONAL);
		}
	}

	public void setHorizontal(int row, int col, int value)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == EMPTY)
		{
			m.setValue(row, col, value);
			arcsType.setValue(row, col, HORIZONTAL);
		}
	}

	public void setVertical(int row, int col, int value)
	{
		if (m.getValue(row, col) < value || arcsType.getValue(row, col) == EMPTY)
		{
			m.setValue(row, col, value);
			arcsType.setValue(row, col, VERTICAL);
		}
	}

	public void setJunction(int rowBegin, int colBegin, int rowEnd, int colEnd)
	{
		int value = m.getValue(rowBegin, colBegin);
		if (m.getValue(rowEnd, colEnd) < value || arcsType.getValue(rowEnd, colEnd) == EMPTY)
		{
			m.setValue(rowEnd, colEnd, m.getValue(rowBegin, colBegin));
			arcsType.setValue(rowEnd, colEnd, JUNCTION);
		}
		backTrackJunction.setExtension(rowBegin, colBegin, rowEnd, colEnd, value);
	}

	public void setExtended(int rowBegin, int colBegin, int rowEnd, int colEnd, int value)
	{
		if (m.getValue(rowEnd, colEnd) < value || arcsType.getValue(rowEnd, colEnd) == EMPTY)
		{
			m.setValue(rowEnd, colEnd, m.getValue(rowBegin, colBegin));
			arcsType.setValue(rowEnd, colEnd, EXTENDED);
		}
		backTrackExtension.setExtension(rowBegin, colBegin, rowEnd, colEnd, value);
	}

	public OptimumPath getOptimumPath(EditGraph eg)
	{
		OptimumPathImpl path = new OptimumPathImpl(eg, nameMethod);
		Vertex v = getOptimumVertex();
		byte b;
		Arc arc;
		Vertex v1;
		int mIJ, wGap, i, j;
		try
		{
			while ((b = arcsType.getValue(i = v.getRow(), j = v.getCol())) != EMPTY)
			{
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
						arc = eg.getExtendedArc(new VertexRange(backTrackExtension.getBeginVertex(i, j), v));
						break;
					case JUNCTION:
						arc = new ArcJuntion(new VertexRange(backTrackJunction.getBeginVertex(i, j), v));
						break;
					default:
						throw new RuntimeException("Tipo de arco inválido: " + b);
				}
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

	public Vertex getOptimumVertex()
	{
		return new Vertex(m.getIndexEndRow(), m.getIndexEndRow());
	}

}

package sequences.matrix;

public class MatrixByteRange
{

	byte[][]	a;
	int			iMin, jMin, iMax, jMax;

	public MatrixByteRange(int iMin, int jMin, int iMax, int jMax)
	{
		this.a = new byte[iMax - iMin + 1][jMax - jMin + 1];
		this.iMin = iMin;
		this.jMin = jMin;
		this.iMax = iMax;
		this.jMax = jMax;
	}

	public byte getValue(int row, int col)
	{
		return a[row - iMin][col - jMin];
	}

	public void setValue(int row, int col, byte value)
	{
		a[row - iMin][col - jMin] = value;
	}

}

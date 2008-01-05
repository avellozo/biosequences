package sequences.matrix;

public class MatrixCharRange
{

	char[][]	a;
	int			iMin, jMin, iMax, jMax;

	public MatrixCharRange(int iMin, int jMin, int iMax, int jMax)
	{
		this.a = new char[iMax - iMin + 1][jMax - jMin + 1];
		this.iMin = iMin;
		this.jMin = jMin;
		this.iMax = iMax;
		this.jMax = jMax;
	}

	public char getValue(int row, int col)
	{
		return a[row - iMin][col - jMin];
	}

	public void setValue(int row, int col, char value)
	{
		a[row - iMin][col - jMin] = value;
	}

}

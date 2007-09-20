package sequences.matrix;

public class MatrixCharRange
{

	char[][]	a;
	int			iMin, jMin;

	public MatrixCharRange(char[][] a, int iMin, int jMin)
	{
		this.a = a;
		this.jMin = jMin;
		this.iMin = iMin;
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

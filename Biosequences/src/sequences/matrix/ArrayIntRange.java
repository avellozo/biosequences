package sequences.matrix;

public class ArrayIntRange implements RowInt
{

	int[]	a;
	int		jMin;

	public ArrayIntRange(int[] a, int jMin)
	{
		this.a = a;
		this.jMin = jMin;
	}

	public int getValue(int col)
	{
		return a[col - jMin];
	}

	public int length()
	{
		return a.length;
	}

	public void setValue(int col, int value)
	{
		a[col - jMin] = value;
	}

}

/*
 * Criado em 16/06/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 16/06/2004
 */
public class ArrayIntPrimitive implements ArrayInt
{
	protected int[]	array;

	public ArrayIntPrimitive(int[] array)
	{
		this.array = array;
	}

	public ArrayIntPrimitive(int length)
	{
		this(new int[length]);
	}

	public int getValue(int col)
	{
		return array[col];
	}

	public void setValue(int col, int value)
	{
		array[col] = value;
	}

	public int length()
	{
		return array.length;
	}

	public int getIndexBegin()
	{
		return 0;
	}

	public int getIndexEnd()
	{
		return length() - 1;
	}

	public int[] getArray()
	{
		return array;
	}

	public boolean isValidIndex(int i)
	{
		return (i >= getIndexBegin() && i <= getIndexEnd());
	}

	public String toString()
	{
		int iBegin = getIndexBegin();
		int iEnd = getIndexEnd();
		StringBuffer str = new StringBuffer();
		str.append(getValue(iBegin));
		for (int i = iBegin + 1; i <= iEnd; i++)
		{
			str.append(' ');
			str.append(getValue(i));
		}
		return str.toString();
	}

}

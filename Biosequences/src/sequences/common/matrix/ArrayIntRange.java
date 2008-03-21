package sequences.matrix;

public class ArrayIntRange implements ArrayInt
{

	ArrayInt	array;
	private int	iBegin, iEnd, iBeginMapped;

	//O índice indexBegin do novo objeto representará o índice indexBeginMapped do array sobreposto
	// as alterações são feitas em cascata
	public ArrayIntRange(ArrayInt array, int indexBegin, int indexEnd, int indexBeginMapped)
	{
		this.array = array;
		iBegin = indexBegin;
		iEnd = indexEnd;
		iBeginMapped = indexBeginMapped;
		if ((!array.isValidIndex(indexMapped(indexBegin))) || (!array.isValidIndex(indexMapped(indexEnd))))
		{
			throw new IndexOutOfBoundsException("Array is smaller than new array.");
		}
	}

	public ArrayIntRange(ArrayInt array, int indexBegin, int indexEnd)
	{
		this(array, indexBegin, indexEnd, array.getIndexBegin());
	}

	public ArrayIntRange(ArrayInt array, int indexBegin)
	{
		this(array, indexBegin, indexBegin + array.length() - 1);
	}

	public ArrayIntRange(int indexBegin, int indexEnd)
	{
		this(new ArrayIntPrimitive(indexEnd - indexBegin + 1), indexBegin, indexEnd);
	}

	//index deve estar entre iBegin e iEnd
	public int getValue(int i)
	{
		if (isValidIndex(i))
		{
			return array.getValue(indexMapped(i));
		}
		throw new IndexOutOfBoundsException("Index " + i);
	}

	//index deve estar entre iBegin e iEnd
	public void setValue(int i, int value)
	{
		if (isValidIndex(i))
		{
			array.setValue(indexMapped(i), value);
		}
		throw new IndexOutOfBoundsException("Index " + i);
	}

	public int length()
	{
		return getIndexEnd() - getIndexBegin() + 1;
	}

	public int[] getArray()
	{
		int l = length();
		if (array.length() != l)
		{
			int[] ret = new int[l];
			int b = getIndexBegin();
			for (int i = 0; i < l; i++)
			{
				ret[i] = getValue(b + i);
			}
			return ret;
		}
		return array.getArray();
	}

	public int getIndexBegin()
	{
		return iBegin;
	}

	public int getIndexEnd()
	{
		return iEnd;
	}

	public boolean isValidIndex(int i)
	{
		return ((i >= getIndexBegin()) && (i <= getIndexEnd()));
	}

	protected int indexMapped(int i)
	{
		return i - iBegin + iBeginMapped;
	}
}

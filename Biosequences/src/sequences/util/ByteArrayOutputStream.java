/*
 * Created on 18/10/2004
 */
package sequences.util;

/**
 * @author Augusto
 */
public class ByteArrayOutputStream extends java.io.ByteArrayOutputStream
{

	public ByteArrayOutputStream()
	{
		super();
	}

	public ByteArrayOutputStream(int size)
	{
		super(size);
	}

	public boolean equals(ByteArrayOutputStream b)
	{
		byte[] a = this.buf;
		byte[] a1 = b.buf;

		if (a == a1)
			return true;
		if (a == null || a1 == null)
			return false;

		int length = b.count;
		if (this.count != length)
			return false;

		for (int i = 0; i < length; i++)
			if (a[i] != a1[i])
				return false;

		return true;
	}
}
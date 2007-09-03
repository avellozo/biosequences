/*
 * Criado em 18/06/2004
 */
package sequences.common;

/**
 * @author Augusto
 * @data 18/06/2004
 */
public class ComplementSequence implements Sequence
{
	Sequence	seq;

	public ComplementSequence(Sequence seq)
	{
		this.seq = seq;
	}

	public int getLength()
	{
		return seq.getLength();
	}

	// começa em 1, ou seja, é como um array de [1..length].
	public byte getLetter(int pos)
	{
		char c = (char) seq.getLetter(pos);
		switch (c)
		{
		case 'A':
			c = 'T';
			break;
		case 'C':
			c = 'G';
			break;
		case 'T':
			c = 'A';
			break;
		case 'G':
			c = 'C';
			break;
		case 'a':
			c = 't';
			break;
		case 'c':
			c = 'g';
			break;
		case 't':
			c = 'a';
			break;
		case 'g':
			c = 'c';
			break;
		}
		return (byte) c;
	}

	// começa em 1, ou seja, é como um array de [1..length].
	public void setLetter(byte b, int pos)
	{
		char c = (char) b;
		switch (c)
		{
		case 'A':
			c = 'T';
			break;
		case 'C':
			c = 'G';
			break;
		case 'T':
			c = 'A';
			break;
		case 'G':
			c = 'C';
			break;
		case 'a':
			c = 't';
			break;
		case 'c':
			c = 'g';
			break;
		case 't':
			c = 'a';
			break;
		case 'g':
			c = 'c';
			break;
		}
		seq.setLetter((byte) c, pos);
	}

	public String getName()
	{
		return seq.getName();
	}

	public void setName(String name)
	{
		seq.setName(name);
	}

	public String toString()
	{
		int length = getLength();
		StringBuffer stb = new StringBuffer(length);
		for (int i = 1; i <= length; i++)
		{
			stb.append(getLetter(i));
		}
		return stb.toString();
	}

	public void setLetters(byte[] letters)
	{
		throw new RuntimeException("Método não implementado.");
	}

	public byte[] getLetters()
	{
		throw new RuntimeException("Método não implementado.");
	}
}

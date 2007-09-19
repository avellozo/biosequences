package sequences.common;

public class ReverseSequence implements Sequence
{

	Sequence	seq;

	public ReverseSequence(Sequence seq)
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
		return seq.getLetter(getLength() - pos + 1);
	}

	// começa em 1, ou seja, é como um array de [1..length].
	public void setLetter(byte b, int pos)
	{
		seq.setLetter(b, getLength() - pos + 1);
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

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

	// come�a em 1, ou seja, � como um array de [1..length].
	public byte getLetter(int pos)
	{
		return seq.getLetter(getLength() - pos + 1);
	}

	// come�a em 1, ou seja, � como um array de [1..length].
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
		throw new SequenceInternalException("It's impossible to set the name of one sequence reverse.");
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
		throw new RuntimeException("M�todo n�o implementado.");
	}

	public byte[] getLetters()
	{
		throw new RuntimeException("M�todo n�o implementado.");
	}

	public String getDescription()
	{
		return "Reverse sequence of " + seq.getDescription();
	}

	public void setDescription(String description)
	{
		throw new SequenceInternalException("It's impossible to set the description of one sequence reverse.");
	}
}

/*
 * Criado em 17/06/2004
 */
package sequences.common;

/**
 * @author Augusto
 * @data 17/06/2004
 */
public class SequenceFragment implements Sequence
{
	Sequence	seq;
	int			begin, length;
	String		description;

	public SequenceFragment(Sequence seq, int begin, int length)
	{
		this.seq = seq;
		this.begin = begin;
		this.length = length;
	}

	public SequenceFragment(Sequence seq, int begin)
	{
		this(seq, begin, seq.getLength() - begin + 1);
	}

	public String toString()
	{
		return seq.toString().substring(begin, begin + length);
	}

	// índice começa em 1
	public byte getLetter(int i)
	{
		return seq.getLetter(begin + i - 1);
	}

	// índice começa em 1
	public void setLetter(byte b, int pos)
	{
		seq.setLetter(b, begin + pos - 1);
	}

	/**
	 * @return Returns the begin.
	 */
	public int getBegin()
	{
		return begin;
	}

	/**
	 * @param begin The begin to set.
	 */
	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	/**
	 * @return Returns the length.
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * @param length The length to set.
	 */
	public void setLength(int length)
	{
		this.length = length;
	}

	public String getName()
	{
		return seq.getName();
	}

	public void setName(String name)
	{
		throw new SequenceInternalException("It's impossible to set the name of one sequence fragment.");
	}

	public void setLetters(byte[] letters)
	{
		throw new RuntimeException("Método não implementado.");
	}

	public byte[] getLetters()
	{
		throw new RuntimeException("Método não implementado.");
	}

	public String getDescription()
	{
		return "Fragment of " + begin + " to " + Integer.toString(begin + length - 1) + " of sequence: "
			+ seq.getDescription();
	}

	public void setDescription(String description)
	{
		throw new SequenceInternalException("It's impossible to set the description of one sequence fragment.");
	}
}

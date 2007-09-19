/*
 * Criado em 18/06/2004
 */
package sequences.common;

/**
 * @author Augusto
 * @data 18/06/2004
 */
public class ComplementReverseSequence extends ComplementSequence
{
	public ComplementReverseSequence(Sequence seq)
	{
		super(seq);
	}

	// começa em 1, ou seja, é como um array de [1..length].
	public byte getLetter(int pos)
	{
		return super.getLetter(getLength() - pos + 1);
	}

	// começa em 1, ou seja, é como um array de [1..length].
	public void setLetter(byte b, int pos)
	{
		super.setLetter(b, getLength() - pos + 1);
	}

}

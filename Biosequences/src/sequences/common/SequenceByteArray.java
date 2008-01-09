package sequences.common;

import java.io.IOException;
import java.io.InputStream;

/*
 * Criado em 14/04/2004
 */

/**
 * @author Augusto
 * @data 14/04/2004
 */
public class SequenceByteArray implements Sequence
{
	protected String	name;
	protected byte[]	letters;
	protected String	description;

	public SequenceByteArray(String name, byte[] letters)
	{
		setName(name);
		setLetters(letters);
	}

	public SequenceByteArray(String name, int length)
	{
		setName(name);
		setLetters(new byte[length]);
	}

	public void fill(InputStream in) throws IOException
	{
		int off = 0;
		int bytesLidos = 0;
		while ((bytesLidos = in.read(letters, off = off + bytesLidos, in.available())) != 0)
			;
	}

	public String toString()
	{
		return new String(letters);
	}

	public int getLength()
	{
		return letters.length;
	}

	// índice começa em 1
	public byte getLetter(int i)
	{
		return letters[i - 1];
	}

	// índice começa em 1
	public void setLetter(byte b, int pos)
	{
		letters[pos - 1] = b;
	}

	/**
	 * @return Returns the letras.
	 */
	public byte[] getLetters()
	{
		return letters;
	}

	/**
	 * @param letras The letras to set.
	 */
	public void setLetters(byte[] letters)
	{
		this.letters = letters;
	}

	/**
	 * @return Returns the nome.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param nome The nome to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}

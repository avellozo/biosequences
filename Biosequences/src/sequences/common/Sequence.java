/*
 * Criado em 17/06/2004
 */

package sequences.common;

/**
 * @author Augusto
 * @data 17/06/2004
 */
public interface Sequence
{
	public String toString();

	public int getLength();

	// índice começa em 1
	public byte getLetter(int i);

	// índice começa em 1
	public void setLetter(byte b, int pos);

	public void setLetters(byte[] letters);

	public byte[] getLetters();

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

}
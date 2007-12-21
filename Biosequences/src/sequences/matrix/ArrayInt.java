/*
 * Criado em 16/06/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 16/06/2004
 */
public interface ArrayInt
{
	public int getValue(int index);

	public void setValue(int index, int value);

	public int length();

	public int getIndexBegin();

	public int getIndexEnd();

	//Retorna um array indexado a partir do zero
	public int[] getArray();

	public boolean isValidIndex(int i);

	public String toString();

}

/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public class Out
{
	int	j;
	int	value;

	public Out(int j, int delta)
	{
		this.j = j;
		this.value = delta;
	}

	public void setValue(int delta)
	{
		this.value = delta;
	}

	public int getValue()
	{
		return this.value;
	}

	public int getJ()
	{
		return this.j;
	}
}

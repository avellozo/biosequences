/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public class Step
{
	int	j;
	int	delta;

	public Step(int j, int delta)
	{
		this.j = j;
		this.delta = delta;
	}

	public void setDelta(int delta)
	{
		this.delta = delta;
	}

	public int getDelta()
	{
		return this.delta;
	}

	public int getJ()
	{
		return this.j;
	}
}

/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public abstract class Candidate
{
	int	j;
	int	gap;

	public Candidate(int j, int gap)
	{
		this.j = j;
		this.gap = gap;
	}

	public void setGap(int gap)
	{
		this.gap = gap;
	}

	public int getGap()
	{
		return this.gap;
	}

	public int getJ()
	{
		return this.j;
	}
}

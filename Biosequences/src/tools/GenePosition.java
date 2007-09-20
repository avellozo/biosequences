/*
 * Created on 11/10/2005
 */
package tools;

/**
 * @author Augusto F. Vellozo
 */
public class GenePosition
{
	boolean complement;
	int position;

	public GenePosition(boolean complement, int position)
	{
		this.complement = complement;
		this.position = position;
	}
	
	
	public boolean isComplement()
	{
		return this.complement;
	}
	public void setComplement(boolean complement)
	{
		this.complement = complement;
	}
	public int getPosition()
	{
		return this.position;
	}
	public void setPosition(int position)
	{
		this.position = position;
	}
}

/*
 * Created on 10/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public class EGInvalidArcException extends EGException
{

	public EGInvalidArcException(char type)
	{
		super("Arc: " + type);
	}

	public EGInvalidArcException(char type, String msg)
	{
		super("Arc: " + type + System.getProperty("line.separator") + msg);
	}

	public EGInvalidArcException(String msg)
	{
		super(msg);
	}

}

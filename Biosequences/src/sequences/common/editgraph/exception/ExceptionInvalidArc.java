/*
 * Created on 10/07/2006
 */
package sequences.editgraph.exception;

/**
 * @author Augusto F. Vellozo
 */
public class ExceptionInvalidArc extends ExceptionEG
{

	public ExceptionInvalidArc(char type)
	{
		super("Arc: " + type);
	}

	public ExceptionInvalidArc(char type, String msg)
	{
		super("Arc: " + type + System.getProperty("line.separator") + msg);
	}

	public ExceptionInvalidArc(String msg)
	{
		super(msg);
	}

}

/*
 * Created on 10/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public abstract class EGException extends Exception
{

	public EGException(EditGraph eg)
	{
		super();
	}

	public EGException()
	{
		super();
	}

	public EGException(String message)
	{
		super(message);
	}

	public EGException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EGException(Throwable cause)
	{
		super(cause);
	}

}

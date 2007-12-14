/*
 * Created on 10/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public abstract class ExceptionEG extends Exception
{

	public ExceptionEG(EditGraph eg)
	{
		super();
	}

	public ExceptionEG()
	{
		super();
	}

	public ExceptionEG(String message)
	{
		super(message);
	}

	public ExceptionEG(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExceptionEG(Throwable cause)
	{
		super(cause);
	}

}

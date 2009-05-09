package sequences.editgraph.exception;

import sequences.editgraph.OptimumPathMethod;

public class ExceptionInvalidPathMethod extends ExceptionEG
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public ExceptionInvalidPathMethod(OptimumPathMethod method)
	{
		super(method.getName());
	}

	public ExceptionInvalidPathMethod(String message)
	{
		super(message);
	}

}

package sequences.editgraph.exception;

import sequences.editgraph.OptimumPathMethod;

public class ExceptionInvalidPathMethod extends ExceptionEG
{

	public ExceptionInvalidPathMethod(OptimumPathMethod method)
	{
		super(method.getName());
	}

	public ExceptionInvalidPathMethod(String message)
	{
		super(message);
	}

}

package sequences.common;

public class SequenceInternalException extends RuntimeException
{

	public SequenceInternalException()
	{
		super("Internal error");
	}

	public SequenceInternalException(String message)
	{
		super(message);
	}

}

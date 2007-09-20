package sequences.editgraph;

public class EGInvalidEditGraphException extends EGException
{

	public EGInvalidEditGraphException(EditGraph eg)
	{
		super(eg);
	}

	public EGInvalidEditGraphException(String message)
	{
		super(message);
	}

}

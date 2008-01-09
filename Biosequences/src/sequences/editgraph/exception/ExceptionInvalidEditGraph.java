package sequences.editgraph.exception;

import sequences.editgraph.EditGraph;

public class ExceptionInvalidEditGraph extends ExceptionEG
{

	public ExceptionInvalidEditGraph(EditGraph eg)
	{
		super(eg);
	}

	public ExceptionInvalidEditGraph(String message)
	{
		super(message);
	}

}

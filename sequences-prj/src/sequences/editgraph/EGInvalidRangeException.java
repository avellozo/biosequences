/*
 * Created on 02/04/2007
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public class EGInvalidRangeException extends EGException
{

	public EGInvalidRangeException(VertexRange range)
	{
		super("Range: " + range);
	}

	public EGInvalidRangeException(VertexRange range, String msg)
	{
		super("Range: " + range + System.getProperty("line.separator") + msg);
	}

	public EGInvalidRangeException(String msg)
	{
		super(msg);
	}

}
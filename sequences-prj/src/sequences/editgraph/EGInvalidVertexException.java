/*
 * Created on 10/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public class EGInvalidVertexException extends EGException
{

	public EGInvalidVertexException(int i, int j)
	{
		super("Vertex: " + i + " " + j);
	}

	public EGInvalidVertexException(int i, int j, String msg)
	{
		super("Vertex: " + i + " " + j + System.getProperty("line.separator")
			+ msg);
	}

	public EGInvalidVertexException(Vertex v)
	{
		super("Vertex: " + v);
	}

	public EGInvalidVertexException(Vertex v, String msg)
	{
		super("Vertex: " + v + System.getProperty("line.separator") + msg);
	}

	public EGInvalidVertexException(String msg)
	{
		super(msg);
	}

}
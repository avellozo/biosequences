/*
 * Created on 10/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public class ExceptionInvalidVertex extends ExceptionEG
{

	public ExceptionInvalidVertex(int i, int j)
	{
		super("Vertex: " + i + " " + j);
	}

	public ExceptionInvalidVertex(int i, int j, String msg)
	{
		super("Vertex: " + i + " " + j + System.getProperty("line.separator")
			+ msg);
	}

	public ExceptionInvalidVertex(Vertex v)
	{
		super("Vertex: " + v);
	}

	public ExceptionInvalidVertex(Vertex v, String msg)
	{
		super("Vertex: " + v + System.getProperty("line.separator") + msg);
	}

	public ExceptionInvalidVertex(String msg)
	{
		super(msg);
	}

}
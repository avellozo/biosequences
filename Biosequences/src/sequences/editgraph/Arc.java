/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public interface Arc
{
	public Vertex getBeginVertex();

	public Vertex getEndVertex();

	public int getWeight();

	public EditGraph getEditGraph();

}
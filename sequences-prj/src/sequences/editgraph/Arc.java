/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public interface Arc<E extends EditGraph<E, ? extends Extender<E>>>
{
	public Vertex<E> getBeginVertex();

	public Vertex<E> getEndVertex();

	public int getWeight();

	public E getEditGraph();

}
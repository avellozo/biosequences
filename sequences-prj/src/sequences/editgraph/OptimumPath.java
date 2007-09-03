/*
 * Created on 01/07/2006
 */
package sequences.editgraph;

import java.util.List;

/**
 * @author Augusto F. Vellozo
 */
public interface OptimumPath<E extends EditGraph<E, ? extends Extender<E>>>
{
	public List<Arc<E>> getArcs();
	
	public Arc<E> getFirst();

	public Arc<E> getLast();

	public boolean add(Arc<E> arc);

	public boolean addFirst(Arc<E> arc);

	public E getEditGraph();

	public VertexRange<E> getVertexRange();

	public int getScore();

	public boolean isLocal();

	public long time();

	public long finishTime();
	
	public int getQttyArcs();

	public int getQttyExtendedArcs();

	public int getQttyHorizontalArcs();
	
	public int getQttyMatches();
	
	public int getQttyMismatches();
	
	public int getQttyVerticalArcs();
	
	public List<ArcExtended<E>> getArcsExtended();
}

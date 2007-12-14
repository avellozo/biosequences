/*
 * Created on 01/07/2006
 */
package sequences.editgraph;

import java.util.List;

/**
 * @author Augusto F. Vellozo
 */
public interface OptimumPath<E extends EditGraph<E, ? extends Extender>>
{
	public List<Arc> getArcs();
	
	public Arc getFirst();

	public Arc getLast();

	public boolean add(Arc arc);

	public boolean addFirst(Arc arc);

	public E getEditGraph();

	public EditGraphSegment getVertexRange();

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
	
	public List<ArcExtended> getArcsExtended();
}

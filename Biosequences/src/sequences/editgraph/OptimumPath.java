/*
 * Created on 01/07/2006
 */
package sequences.editgraph;

import java.util.List;

import sequences.editgraph.arcs.ArcExtended;

/**
 * @author Augusto F. Vellozo
 */
public interface OptimumPath
{
	public List<Arc> getArcs();

	public Arc getFirst();

	public Arc getLast();

	public boolean add(Arc arc);

	public void addFirst(Arc arc);

	public EditGraph getEditGraph();

	public int getScore();

	//	public boolean isLocal();
	//
	//	public long getTimeToConstruct();

	//	public long finishTime();

	public int getQttyArcs();

	public int getQttyVerticalArcs();

	public int getQttyHorizontalArcs();

	public int getQttyMatches();

	public int getQttyMismatches();

	public int getQttyExtendedArcs();

	public List<ArcExtended> getArcsExtended();

}

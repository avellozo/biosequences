/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface ArcHorizontalFactory
{

	public boolean canCreateHorizontalArc(Vertex endVertex);

	public boolean canCreateHorizontalArc(int i, int j);

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos horizontais que são positivos no grafo
	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg);

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex;

}

/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

import java.util.List;

public interface ArcVerticalFactory
{

	public boolean canCreateVerticalArc(Vertex endVertex);

	public boolean canCreateVerticalArc(int i, int j);

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos verticais que são positivos no grafo
	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg);

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex;

}

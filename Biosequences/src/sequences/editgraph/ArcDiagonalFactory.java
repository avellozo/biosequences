/*
 * Created on 07/01/2008
 */
package sequences.editgraph;

import java.util.List;

import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface ArcDiagonalFactory
{

	public boolean canCreateDiagonalArc(Vertex endVertex);

	public boolean canCreateDiagonalArc(int i, int j);

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos diagonais que são positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg);

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex;

}

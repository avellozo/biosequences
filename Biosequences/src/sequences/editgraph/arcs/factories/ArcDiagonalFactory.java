/*
 * Created on 07/01/2008
 */
package sequences.editgraph.arcs.factories;

import java.util.List;

import sequences.editgraph.EditGraph;
import sequences.editgraph.Vertex;
import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public interface ArcDiagonalFactory
{

	public boolean canCreateDiagonalArc(Vertex endVertex);

	public boolean canCreateDiagonalArc(int i, int j);

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos diagonais que s�o positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg);

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex;

}

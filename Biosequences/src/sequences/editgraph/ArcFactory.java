/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

import java.util.List;

public interface ArcFactory
{
	public boolean canCreateVerticalArc(Vertex endVertex);

	public boolean canCreateHorizontalArc(Vertex endVertex);

	public boolean canCreateDiagonalArc(Vertex endVertex);

	public boolean canCreateVerticalArc(int i, int j);

	public boolean canCreateHorizontalArc(int i, int j);

	public boolean canCreateDiagonalArc(int i, int j);

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos diagonais que são positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg);

	// Cria uma lista somente com arcos verticais que são positivos no grafo
	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg);

	// Cria uma lista somente com arcos horizontais que são positivos no grafo
	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg);

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex;

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex;

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex;

	//	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex;
	//
}

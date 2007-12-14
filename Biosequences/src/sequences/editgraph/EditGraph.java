/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

import java.util.List;

/**
 * @author Augusto F. Vellozo
 */
public interface EditGraph
{
	// Grafo cujos vértices são representados pelo par (i,j) de uma matriz,
	// onde i é o número da linha e j é o número da coluna.
	// Os arcos têm pesos e são representados pelo tipo e vértice final.

	public int getRowMin();

	public int getColMin();

	public int getRowMax();

	public int getColMax();

	public boolean existsVertex(int row, int col);

	public boolean existsVertex(Vertex v);

	public boolean existsVerticalArc(Vertex endVertex);

	public boolean existsHorizontalArc(Vertex endVertex);

	public boolean existsDiagonalArc(Vertex endVertex);

	//	public boolean existsExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;
	//
	public Vertex getVertex(int row, int col) throws ExceptionInvalidVertex;

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	//	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;
	//
	// Cria uma lista somente com arcos diagonais que são positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs();

	// Cria uma lista somente com arcos verticais que são positivos no grafo
	public List< ? extends ArcVertical> getNonZeroVerticalArcs();

	// Cria uma lista somente com arcos horizontais que são positivos no grafo
	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs();

	public EditGraph getSegment(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex;

	// Retorna true se a aresta diagonal terminada em endVertex representa um match
	// Retorna falso caso contrário
	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex;

}
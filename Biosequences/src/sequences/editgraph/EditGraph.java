/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

import java.util.List;

import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto F. Vellozo
 */
public interface EditGraph
{
	// Grafo cujos v�rtices s�o representados pelo par (i,j) de uma matriz,
	// onde i � o n�mero da linha e j � o n�mero da coluna.
	// Os arcos t�m pesos e s�o representados pelo tipo e v�rtice final.

	public int getRowMin();

	public int getColMin();

	public int getRowMax();

	public int getColMax();

	public boolean existsVertex(int row, int col);

	public boolean existsVertex(Vertex v);

	public Vertex getVertex(int row, int col) throws ExceptionInvalidVertex;

	public boolean existsVerticalArc(Vertex endVertex);

	public boolean existsHorizontalArc(Vertex endVertex);

	public boolean existsDiagonalArc(Vertex endVertex);

	//	public boolean existsExtendedArc(VertexRange vertexRange);

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex;

	//	public ArcExtended getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex;

	// Cria uma lista somente com arcos diagonais que s�o positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs();

	// Cria uma lista somente com arcos verticais que s�o positivos no grafo
	public List< ? extends ArcVertical> getNonZeroVerticalArcs();

	// Cria uma lista somente com arcos horizontais que s�o positivos no grafo
	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs();

	//	public EditGraph getSegment(VertexRange vertexRange) throws ExceptionInvalidVertex;

	public int getWeightVerticalArc(int i, int j) throws ExceptionInvalidVertex;

	public int getWeightHorizontalArc(int i, int j) throws ExceptionInvalidVertex;

	public int getWeightDiagonalArc(int i, int j) throws ExceptionInvalidVertex;

	public VertexRange getFullRange();

	// Retorna true se a aresta diagonal terminada em endVertex representa um match
	// Retorna falso caso contr�rio
	//	public boolean isMatch(Vertex endVertex) throws ExceptionInvalidVertex;

	//	public String getNameRows();
	//
	//	public String getNameCols();
	//
}
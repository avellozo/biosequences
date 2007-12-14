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

	public static final char	VERTICAL	= 'V';

	public static final char	HORIZONTAL	= 'H';

	public static final char	DIAGONAL	= 'D';

	public static final char	EXTENDED	= 'E';

	/*	public OptimumPath getOptimumPath(VertexRange range, boolean local)
				throws EGInvalidRangeException, EGInvalidEditGraphException;

		public OptimumPath getOptimumPath(VertexRange range,
				boolean local, OptimumPathFactory<E, P> pathFactory)
				throws EGInvalidRangeException, EGInvalidEditGraphException;

		// get maximum path from up left corner to down right corner
		public OptimumPath getOptimumPath(boolean local) throws EGInvalidEditGraphException;

		public OptimumPathFactory<E, ? extends OptimumPath> getOptimumPathFactory();
		
	*/
	public int getRowMin();

	public int getColMin();

	public int getRowMax();

	public int getColMax();

	//	public boolean isExtended();

	//	public X getExtender();

	//	public boolean isValidVertex(Vertex v);
	//
	public boolean existsVertex(int row, int col);

	//	public VertexRange getFullVertexRange();
	//
	public boolean existsVerticalArc(int row, int col) throws ExceptionInvalidVertex;

	public boolean existsHorizontalArc(int row, int col) throws ExceptionInvalidVertex;

	public boolean existsDiagonalArc(int row, int col) throws ExceptionInvalidVertex;

	//	public boolean existsExtendedArc(int rowBeginVertex, int colBeginVertex, int rowEndVertex, int colEndVertex) throws EGInvalidVertexException;

	//	public int getWeightHorizontalArc(int row, int col)
	//			throws EGInvalidArcException;
	//
	//	public int getWeightVerticalArc(int row, int col)
	//			throws EGInvalidArcException;
	//
	//	public int getWeightDiagonalArc(int row, int col)
	//			throws EGInvalidArcException;
	//
	//	public int getWeightExtendedArc(VertexRange range)
	//			throws EGInvalidRangeException;
	//
	public Vertex getVertex(int row, int col) throws ExceptionInvalidVertex;

	public ArcDiagonal getDiagonalArc(Vertex v) throws ExceptionInvalidVertex;

	public ArcHorizontal getHorizontalArc(Vertex v) throws ExceptionInvalidVertex;

	public ArcVertical getVerticalArc(Vertex v) throws ExceptionInvalidVertex;

	//	public ArcExtended<X> getExtendedArc(int row1, int col1, int row2, int col2)
	//	throws EGInvalidArcException, EGInvalidVertexException;
	//
	//	public ArcExtended<X> getExtendedArc(Vertex beginVertex, Vertex endVertex)
	//	throws EGInvalidVertexException, EGInvalidEditGraphException;

	// Cria uma lista somente com arcos diagonais que são positivos no grafo
	public List< ? extends ArcDiagonal> getPositiveDiagonalArcs();

	// Cria uma lista somente com arcos verticais que são positivos no grafo
	public List< ? extends ArcVertical> getPositiveVerticalArcs();

	// Cria uma lista somente com arcos horizontais que são positivos no grafo
	public List< ? extends ArcHorizontal> getPositiveHorizontalArcs();

	//	public boolean isMatch(ArcDiagonal arc);

	// returns true if v1 dominates v2
	public boolean dominates(Vertex v1, Vertex v2) throws ExceptionInvalidVertex;

	//	public boolean dominates(int row1, int col1, int row2, int col2)
	//			throws EGInvalidVertexException;
	//
}
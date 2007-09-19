/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

import java.util.List;

/**
 * @author Augusto F. Vellozo
 */
public interface EditGraph<E extends EditGraph<E, X>, X extends Extender<E>>
{
	// Grafo cujos vértices são representados pelo par (i,j) de uma matriz,
	// onde i é o número da linha e j é o número da coluna.
	// Os arcos têm pesos e são representados pelo tipo e vértice final.

	public static final char	VERTICAL	= 'V';

	public static final char	HORIZONTAL	= 'H';

	public static final char	DIAGONAL	= 'D';

	public static final char	EXTENDED	= 'E';

	public OptimumPath<E> getOptimumPath(VertexRange<E> range, boolean local)
			throws EGInvalidRangeException, EGInvalidEditGraphException;

	public <P extends OptimumPath<E>> P getOptimumPath(VertexRange<E> range,
			boolean local, OptimumPathFactory<E, P> pathFactory)
			throws EGInvalidRangeException, EGInvalidEditGraphException;

	// get maximum path from up left corner to down right corner
	public OptimumPath<E> getOptimumPath(boolean local) throws EGInvalidEditGraphException;

	public OptimumPathFactory<E, ? extends OptimumPath<E>> getOptimumPathFactory();
	
	public int getRowMin();

	public int getColMin();

	public int getRowMax();

	public int getColMax();

	public boolean isExtended();

	public X getExtender();

	public boolean isValidVertex(Vertex<E> v);

	public boolean existsVertex(int row, int col);

	public Vertex<E> getVertex(int row, int col)
			throws EGInvalidVertexException;

	public VertexRange<E> getFullVertexRange();

	public boolean existsVerticalArc(int row, int col);

	public boolean existsHorizontalArc(int row, int col);

	public boolean existsDiagonalArc(int row, int col);

	public boolean existsExtendedArc(VertexRange<E> range);

	public int getWeightHorizontalArc(int row, int col)
			throws EGInvalidArcException;

	public int getWeightVerticalArc(int row, int col)
			throws EGInvalidArcException;

	public int getWeightDiagonalArc(int row, int col)
			throws EGInvalidArcException;

	public int getWeightExtendedArc(VertexRange<E> range)
			throws EGInvalidRangeException;

	public ArcDiagonal<E> getDiagonalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException;

	public ArcHorizontal<E> getHorizontalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException;

	public ArcVertical<E> getVerticalArc(Vertex<E> endVertex)
			throws EGInvalidArcException, EGInvalidVertexException;

	public ArcExtended<E> getExtendedArc(VertexRange<E> range)
			throws EGInvalidRangeException, EGInvalidEditGraphException;

	// Cria uma lista somente com arcos diagonais que são positivos do grafo
	public List< ? extends ArcDiagonal<E>> getMatches();

	public boolean isMatch(ArcDiagonal<E> arc) throws EGInvalidArcException;

	// returns true if v1 dominatse v2
	public boolean dominates(Vertex<E> v1, Vertex<E> v2)
			throws EGInvalidVertexException;

	public boolean dominates(int row1, int col1, int row2, int col2)
			throws EGInvalidVertexException;

}
/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

import java.util.List;

public interface WeighterArcs
{
	public int getWeightHorizontal(int row, int col);

	public int getWeightVertical(int row, int col);

	public int getWeightDiagonal(int row, int col);

	// Cria uma lista somente com arcos diagonais que s�o positivos no grafo
	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraphSegment segment);

	// Cria uma lista somente com arcos verticais que s�o positivos no grafo
	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraphSegment segment);

	// Cria uma lista somente com arcos horizontais que s�o positivos no grafo
	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraphSegment segment);

}

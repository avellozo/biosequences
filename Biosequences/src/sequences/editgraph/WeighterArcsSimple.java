/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public abstract class WeighterArcsSimple implements WeighterArcs
{
	// Os arcos e vértices não existem realmente, existem representações que
	// respondem se há ou não um match em um vértice. Desta forma os pesos das
	// arestas são:
	// vertical e horizontal: o valor do parâmetro 'gap'
	// diagonal: se for um match o valor de 'match' senão o valor de 'mismatch'

	protected int	match, mismatch, gap;

	public WeighterArcsSimple(int match, int mismatch, int gap)
	{
		this.match = match;
		this.mismatch = mismatch;
		this.gap = gap;
	}

	public int getWeightHorizontal(int row, int col)
	{
		return gap;
	}

	public int getWeightVertical(int row, int col)
	{
		return gap;
	}

	public int getWeightDiagonal(int row, int col)
	{
		return (isMatch(row, col)) ? match : mismatch;
	}

	protected abstract boolean isMatch(int row, int col);

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		int i, j, w;
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					if ((w = getWeightDiagonal(i, j)) != 0)
					{
						list.addLast(new ArcDiagonal(eg.getVertex(i, j), w));
					}
				}
			}
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		return list;
	}

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraph eg)
	{
		int i, j;
		LinkedList<ArcHorizontal> list = new LinkedList<ArcHorizontal>();
		if (gap != 0)
		{
			try
			{
				for (i = eg.getRowMin(); i <= eg.getRowMax(); i++)
				{
					for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
					{
						list.addLast(new ArcHorizontal(eg.getVertex(i, j), gap));
					}
				}
			}
			catch (ExceptionInvalidVertex e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
		}
		return list;
	}

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraph eg)
	{
		int i, j;
		LinkedList<ArcVertical> list = new LinkedList<ArcVertical>();
		if (gap != 0)
		{
			try
			{
				for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
				{
					for (j = eg.getColMin(); j <= eg.getColMax(); j++)
					{
						list.addLast(new ArcVertical(eg.getVertex(i, j), gap));
					}
				}
			}
			catch (ExceptionInvalidVertex e)
			{
				e.printStackTrace();
				throw new ExceptionInternalEG();
			}
		}
		return list;
	}

}
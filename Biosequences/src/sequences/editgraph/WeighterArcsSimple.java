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
	// Os arcos e v�rtices n�o existem realmente, existem representa��es que
	// respondem se h� ou n�o um match em um v�rtice. Desta forma os pesos das
	// arestas s�o:
	// vertical e horizontal: o valor do par�metro 'gap'
	// diagonal: se for um match o valor de 'match' sen�o o valor de 'mismatch'

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

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraphSegment segment)
	{
		int i, j, w;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = segment.getRowMin() + 1; i <= segment.getRowMax(); i++)
			{
				for (j = segment.getColMin() + 1; j <= segment.getColMax(); j++)
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

	public List< ? extends ArcHorizontal> getNonZeroHorizontalArcs(EditGraphSegment segment)
	{
		int i, j;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcHorizontal> list = new LinkedList<ArcHorizontal>();
		if (gap != 0)
		{
			try
			{
				for (i = segment.getRowMin(); i <= segment.getRowMax(); i++)
				{
					for (j = segment.getColMin() + 1; j <= segment.getColMax(); j++)
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

	public List< ? extends ArcVertical> getNonZeroVerticalArcs(EditGraphSegment segment)
	{
		int i, j;
		EditGraph eg = segment.getEditGraph();
		LinkedList<ArcVertical> list = new LinkedList<ArcVertical>();
		if (gap != 0)
		{
			try
			{
				for (i = segment.getRowMin() + 1; i <= segment.getRowMax(); i++)
				{
					for (j = segment.getColMin(); j <= segment.getColMax(); j++)
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
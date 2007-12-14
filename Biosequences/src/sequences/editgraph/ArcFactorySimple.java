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
public abstract class ArcFactorySimple implements ArcFactory
{
	// Os arcos e vértices não existem realmente, existem representações que
	// respondem se há ou não um match em um vértice. Desta forma os pesos das
	// arestas são:
	// vertical e horizontal: o valor do parâmetro 'gap'
	// diagonal: se for um match o valor de 'match' senão o valor de 'mismatch'

	protected int	match, mismatch, gap;

	public ArcFactorySimple(int match, int mismatch, int gap)
	{
		this.match = match;
		this.mismatch = mismatch;
		this.gap = gap;
	}

	public ArcDiagonal getDiagonalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsDiagonalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcDiagonal(endVertex, (isMatch(endVertex)) ? match : mismatch);
	}

	public ArcHorizontal getHorizontalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsHorizontalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcHorizontal(endVertex, gap);
	}

	public ArcVertical getVerticalArc(Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (!existsVerticalArc(endVertex))
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		return new ArcVertical(endVertex, gap);
	}

	public boolean existsHorizontalArc(Vertex endVertex)
	{
		return true;
	}

	public boolean existsVerticalArc(Vertex endVertex)
	{
		return true;
	}

	public List< ? extends ArcDiagonal> getNonZeroDiagonalArcs(EditGraph eg)
	{
		int i, j;
		ArcDiagonal arc;
		LinkedList<ArcDiagonal> list = new LinkedList<ArcDiagonal>();
		try
		{
			for (i = eg.getRowMin() + 1; i <= eg.getRowMax(); i++)
			{
				for (j = eg.getColMin() + 1; j <= eg.getColMax(); j++)
				{
					arc = getDiagonalArc(eg.getVertex(i, j));
					if (arc.getWeight() != 0)
					{
						list.addLast(arc);
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
						list.addLast(getHorizontalArc(eg.getVertex(i, j)));
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
						list.addLast(getVerticalArc(eg.getVertex(i, j)));
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
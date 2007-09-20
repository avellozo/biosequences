/*
 * Criado em 09/08/2004
 */
package sequences.editgraph;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public abstract class EGAlignment<E extends EGAlignment<E, X>, X extends Extender<E>>
		extends EditGraphImpl<E, X>
{
	// Os arcos e vértices não existem realmente, existem representações que
	// respondem se há ou não um match em um vértice. Desta forma os pesos das
	// arestas são:
	// vertical e horizontal: o valor do parâmetro 'gap'
	// diagonal: se for um match o valor de 'match' senão o valor de 'mismatch'

	protected int	match, mismatch, gap;

	public EGAlignment(int match, int mismatch, int gap,
			OptimumPathFactory<E, ? extends OptimumPath<E>> pathFactory,
			X extender)
	{
		super(pathFactory, extender);
		this.match = match;
		this.mismatch = mismatch;
		this.gap = gap;
	}

	protected int getWeightHorizontal(int row, int col)
	{
		return gap;
	}

	protected int getWeightVertical(int row, int col)
	{
		return gap;
	}

	protected int getWeightDiagonal(int row, int col)
	{
		try
		{
			return (isMatch(row, col)) ? match : mismatch;
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}

	public abstract boolean isMatch(int row, int col)
			throws EGInvalidArcException;

	public boolean isMatch(ArcDiagonal<E> arc) throws EGInvalidArcException
	{
		validateArc(arc);
		try
		{
			return isMatch(arc.getEndVertex().getI(), arc.getEndVertex().getJ());
		}
		catch (EGInvalidArcException e)
		{
			e.printStackTrace();
			throw new EGInternalException();
		}
	}
}
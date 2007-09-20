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
	// Os arcos e v�rtices n�o existem realmente, existem representa��es que
	// respondem se h� ou n�o um match em um v�rtice. Desta forma os pesos das
	// arestas s�o:
	// vertical e horizontal: o valor do par�metro 'gap'
	// diagonal: se for um match o valor de 'match' sen�o o valor de 'mismatch'

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
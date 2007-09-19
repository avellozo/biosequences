/**
 * 
 */
package sequences.editgraph;

public class ArcExtended<E extends EditGraph<E, ? extends Extender<E>>> extends ArcAbstractImpl<E>
{
	VertexRange<E>	range;

	public ArcExtended(VertexRange<E> range, int weight) throws EGInvalidVertexException, EGInvalidRangeException
	{
		super(range.getEndVertex(), weight);
		if (!getEditGraph().existsExtendedArc(range))
		{
			throw new EGInvalidRangeException("Can not create extended arc on the range "
				+ this.range);
		}
	}

	public Vertex<E> getBeginVertex()
	{
		return range.getBeginVertex();
	}

	public VertexRange<E> getRange()
	{
		return range;
	}

	@Override
	public String toString()
	{
		return "Extended " + super.toString();
	}
}
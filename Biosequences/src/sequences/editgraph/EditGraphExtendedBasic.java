/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

/*
 * Created on 09/10/2007
 */

public class EditGraphExtendedBasic extends EditGraphBasic implements EditGraphExtended
{

	ArcExtendedFactory	arcExtendedFactory;

	public EditGraphExtendedBasic(int rowMin, int rowMax, int colMin, int colMax, ArcFactory arcFactory,
			ArcExtendedFactory arcExtendedFactory)
	{
		super(rowMin, rowMax, colMin, colMax, arcFactory);
		this.arcExtendedFactory = arcExtendedFactory;
	}

	public boolean existsExtendedArc(Vertex beginVertex, Vertex endVertex)
	{
		try
		{
			return (isValidVertexParam(beginVertex) && isValidVertexParam(endVertex) && beginVertex.dominates(endVertex));
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (existsExtendedArc(beginVertex, endVertex))
		{
			return arcExtendedFactory.getExtendedArc(beginVertex, endVertex);
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex, "It's impossible to create an extended arc with begin vertex:"
				+ beginVertex);
		}
	}

	/**
	 * @return Returns the arcExtendedFactory.
	 */
	public ArcExtendedFactory getArcExtendedFactory()
	{
		return arcExtendedFactory;
	}

}

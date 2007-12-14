/*
 * Created on 09/10/2007
 */
package sequences.editgraph;

/*
 * Created on 09/10/2007
 */

public class EditGraphExtendedBasic extends EditGraphBasic implements EditGraphExtended
{

	WeighterExtendedArcs	weighterExtended;

	public EditGraphExtendedBasic(int rowMin, int rowMax, int colMin, int colMax, WeighterArcs weighter,
			WeighterExtendedArcs weighterExtended)
	{
		super(rowMin, rowMax, colMin, colMax, weighter);
		this.weighterExtended = weighterExtended;
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
			return new ArcExtended(beginVertex, endVertex, weighterExtended.getWeightExtended(beginVertex.getRow(),
				beginVertex.getCol(), endVertex.getRow(), endVertex.getCol()));
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex, "It's impossible to create an extended arc with begin vertex:"
				+ beginVertex);
		}
	}
}

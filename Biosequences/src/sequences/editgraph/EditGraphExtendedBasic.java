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

	public boolean existsExtendedArc(int rowBeginVertex, int colBeginVertex, int rowEndVertex, int colEndVertex)
			throws ExceptionInvalidVertex
	{
		if (existsVertex(rowBeginVertex, colBeginVertex) && existsVertex(rowEndVertex, colEndVertex))
		{
			return dominates(rowBeginVertex, colBeginVertex, rowEndVertex, colEndVertex);
		}
		else
		{
			throw new ExceptionInvalidVertex("Vertex " + rowBeginVertex + "," + colBeginVertex + "or vertex "
				+ rowEndVertex + "," + colEndVertex + " doesn't exist in this edit graph.");
		}
	}

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		if (isValidVertexParam(beginVertex) && isValidVertexParam(endVertex)
			&& existsExtendedArc(beginVertex.getI(), beginVertex.getJ(), endVertex.getI(), endVertex.getJ()))
		{
			return new ArcExtended(beginVertex, endVertex, weighterExtended.getWeightExtended(beginVertex.getI(),
				beginVertex.getJ(), endVertex.getI(), endVertex.getJ()));
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex, "It's impossible to create an extended arc with vertex:"
				+ beginVertex);
		}
	}
}

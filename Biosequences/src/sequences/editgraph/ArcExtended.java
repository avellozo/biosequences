/**
 * 
 */
package sequences.editgraph;

public class ArcExtended extends ArcAbstractImpl
{
	Vertex				beginVertex;
	EditGraphExtended	eg;

	public ArcExtended(Vertex beginVertex, Vertex endVertex, int weight) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		if (beginVertex == null)
		{
			throw new ExceptionInvalidVertex(beginVertex);
		}
		if (endVertex == null)
		{
			throw new ExceptionInvalidVertex(endVertex);
		}
		if (endVertex.getEditGraph() instanceof EditGraphExtended)
		{
			eg = (EditGraphExtended) endVertex.getEditGraph();
		}
		else
		{
			throw new ExceptionInvalidVertex(endVertex,
				"This vertex isn't of an extended edit graph. Couldn't create a extended arc.");
		}
		if (beginVertex.getEditGraph() != endVertex.getEditGraph())
		{
			throw new ExceptionInvalidVertex(endVertex, "Can't create extended arc of diferents graphes: "
				+ beginVertex);
		}
		if (!getEditGraph().dominates(beginVertex, endVertex))
		{
			throw new ExceptionInvalidVertex(beginVertex, "Begin vertex is invalid to end vertex: " + endVertex);
		}
		if (!getEditGraph().existsExtendedArc(beginVertex.getI(), beginVertex.getJ(), endVertex.getI(),
			endVertex.getJ()))
		{
			throw new ExceptionInvalidVertex(endVertex, "Can't create extended arc from " + beginVertex);
		}
	}

	public Vertex getBeginVertex()
	{
		return beginVertex;
	}

	@Override
	public EditGraphExtended getEditGraph()
	{
		return eg;
	}

	public int getRowsOfExtension()
	{
		return (getEndVertex().getI() - getBeginVertex().getI() + 1);
	}

	public int getColsOfExtension()
	{
		return (getEndVertex().getJ() - getBeginVertex().getJ() + 1);
	}

	@Override
	public String toString()
	{
		return "Extended " + super.toString();
	}
}
/*
 * Created on 14/12/2007
 */
package sequences.editgraph;

public class ArcExtendedFactoryOverEGInverted implements ArcExtendedFactory
{

	// Este Weighter assume que a seqüência que define as linhas do grafo de edição está invertida em 
	// relação à egInverted

	EditGraph	egInverted;

	public ArcExtendedFactoryOverEGInverted(EditGraph egInverted)
	{
		this.egInverted = egInverted;
	}

	public EditGraph getEgInverted()
	{
		return egInverted;
	}

	public int getWeightExtended(int rowBeginVertex, int colBeginVertex, int rowEndVertex, int colEndVertex)
	{
	}

	public ArcExtended getExtendedArc(Vertex beginVertex, Vertex endVertex) throws ExceptionInvalidVertex
	{
		return new ArcExtended(beginVertex, endVertex, getWeightExtended(beginVertex.getRow(), beginVertex.getCol(),
			endVertex.getRow(), endVertex.getCol()));
	}
}

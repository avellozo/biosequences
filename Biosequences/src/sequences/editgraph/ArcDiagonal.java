/**
 * 
 */
package sequences.editgraph;

public class ArcDiagonal extends ArcAbstractImpl
{

	boolean	isMatch;

	public ArcDiagonal(Vertex endVertex, int weight, boolean isMatch) throws ExceptionInvalidVertex
	{
		super(endVertex, weight);
		this.isMatch = isMatch;
	}

	public Vertex getBeginVertex()
	{
		return new Vertex(getEndVertex().getRow() - 1, getEndVertex().getCol() - 1);
	}

	/**
	 * @return Returns the match.
	 */
	public boolean isMatch()
	{
		return isMatch;
	}

	public String toString()
	{
		return "Arc Diagonal: endVertex:" + getEndVertex() + " weight " + weight;
	}
}
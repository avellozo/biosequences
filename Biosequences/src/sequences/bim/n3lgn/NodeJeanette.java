/*
 * Criado em 17/08/2004
 */
package sequences.bim.n3lgn;

/**
 * @author Augusto
 * @data 17/08/2004
 */
public class NodeJeanette extends NodeBinaryWeight
{
	protected boolean leftSubtreeComplete = false;
	protected int middleWeight;
	
	public NodeJeanette()
	{
		super();
	}

	public NodeJeanette(Node leftNode, Node rightNode)
	{
		super(leftNode, rightNode);
	}

	public NodeJeanette(Node leftNode, Node rightNode, int leftWeight,
			int rightWeight)
	{
		super(leftNode, rightNode, leftWeight, rightWeight);
	}
	
	public int getMiddleWeight()
	{
		return middleWeight;
	}

	private void setMiddleWeight(int middleWeight)
	{
		this.middleWeight = middleWeight;
	}

	public boolean isLeftSubtreeComplete()
	{
		return leftSubtreeComplete;
	}

	private void setLeftSubtreeComplete(boolean leftSubtreeComplete)
	{
		this.leftSubtreeComplete = leftSubtreeComplete;
	}
	
	public void setLeftWeight(int leftWeight)
	{
		if (isLeftSubtreeComplete())
		{
			int diffWeight = leftWeight - getLeftWeight();
			setMiddleWeight(getMiddleWeight() + diffWeight);
		}
		super.setLeftWeight(leftWeight);
	}

	public void completedLeftSubtree(int middleWeight)
	{
		setMiddleWeight(middleWeight);
		setLeftSubtreeComplete(true);
	}

}

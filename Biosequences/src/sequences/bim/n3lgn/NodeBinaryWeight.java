/*
 * Criado em 11/08/2004
 */
package sequences.bim.n3lgn;

/**
 * @author Augusto
 * @data 11/08/2004
 */
public class NodeBinaryWeight extends NodeBinary
{
	int leftWeight, rightWeight;
	
	public NodeBinaryWeight()
	{
		super();
	}

	public NodeBinaryWeight(Node leftNode, Node rightNode)
	{
		super(leftNode, rightNode);
	}

	public NodeBinaryWeight(Node leftNode, Node rightNode, int leftWeight, int rightWeight)
	{
		this(leftNode, rightNode);
		this.leftWeight = leftWeight;
		this.rightWeight = rightWeight;
	}
	
	public int getLeftWeight()
	{
		return leftWeight;
	}

	public void setLeftWeight(int leftWeight)
	{
		this.leftWeight = leftWeight;
	}

	public int getRightWeight()
	{
		return rightWeight;
	}

	public void setRightWeight(int rightWeight)
	{
		this.rightWeight = rightWeight;
	}

	public void setLeftNode(Node node, int weight)
	{
		setLeftNode(node);
		setLeftWeight(weight);
	}

	public void setRightNode(Node node, int weight)
	{
		setRightNode(node);
		setRightWeight(weight);
	}

	public Node cloneLeft(int weight)
	{
		setLeftNode((Node)(getLeftNode().clone()), weight);
		return getLeftNode();
	}

	public Node cloneRight(int weight)
	{
		setRightNode((Node)(getRightNode().clone()), weight);
		return getRightNode();
	}
	
	public NodeBinaryWeight clone(int additionalWeight)
	{
		NodeBinaryWeight node = (NodeBinaryWeight) clone();
		if (node.getLeftNode() != null)
		{
			node.setLeftWeight(node.getLeftWeight() + additionalWeight);
		}
		if (node.getRightNode() != null)
		{
			node.setRightWeight(node.getRightWeight() + additionalWeight);
		}
		return node;
	}

}

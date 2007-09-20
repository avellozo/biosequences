/*
 * Criado em 10/08/2004
 */
package sequences.bim.n3lgn;

/**
 * @author Augusto
 * @data 10/08/2004
 */
public class NodeBinary implements Node
{
	Node leftNode, rightNode;
	
	public NodeBinary()
	{
		super();
	}
	
	public NodeBinary(Node leftNode, Node rightNode)
	{
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	
	/* (non-Javadoc)
	 * @see sequences.Node#isLeaf()
	 */
	public boolean isLeaf()
	{
		return ((leftNode == null) && (rightNode == null));
	}

	/* (non-Javadoc)
	 * @see sequences.Node#getEdgeOutQtty()
	 */
	public int getNodeQtty()
	{
		return (isLeaf()?0:(((leftNode == null) || (rightNode == null))?1:0));
	}

	/* (non-Javadoc)
	 * @see sequences.Node#getNode(int)
	 */
	public Node getNode(int i)
	{
		switch (i)
		{
			case 0: return leftNode;
			case 1: return rightNode;
			default: throw new RuntimeException("Índice inválido");
		}
	}
	
	public Node getLeftNode()
	{
		return leftNode;
	}

	public Node getRightNode()
	{
		return rightNode;
	}

	public void setLeftNode(Node leftNode)
	{
		this.leftNode = leftNode;
	}

	public void setRightNode(Node rightNode)
	{
		this.rightNode = rightNode;
	}

	/* (non-Javadoc)
	 * @see sequences.Node#setNode(int, sequences.Node)
	 */
	public void setNode(int i, Node node)
	{
		switch (i)
		{
			case 0: setLeftNode(node); break;
			case 1: setRightNode(node); break;
			default: throw new RuntimeException("Índice inválido");
		}
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			throw new InternalError();
		}
	}

}

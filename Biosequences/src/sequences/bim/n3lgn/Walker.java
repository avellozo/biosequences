/*
 * Criado em 25/08/2004
 */
package sequences.bim.n3lgn;



class Walker implements Cloneable
{
	Node node;
	int weightAccumulated;
	
	public Walker(Node node, int weightAccumulated)
	{
		super();
		this.node = node;
		this.weightAccumulated = weightAccumulated;
	}
	
	public int getRightWeightAccumulated()
	{
		return (((NodeBinaryWeight)getNode()).getRightWeight() + 
				getWeightAccumulated());
	}

	public int getLeftWeightAccumulated()
	{
		return (((NodeBinaryWeight)getNode()).getLeftWeight() + 
				getWeightAccumulated());
	}

	public Node getRightNode()
	{
		return ((NodeBinary)getNode()).getRightNode();
	}

	public Node getLeftNode()
	{
		return ((NodeBinary)getNode()).getLeftNode();
	}

	public int getMiddleWeightAccumulated()
	{
		return ((NodeJeanette)getNode()).getMiddleWeight() + 
			getWeightAccumulated();
	}

	public Node getNode()
	{
		return node;
	}

	public void setNode(Node node)
	{
		this.node = node;
	}

	public int getWeightAccumulated()
	{
		return weightAccumulated;
	}

	public void setWeightAccumulated(int weightAccumulated)
	{
		this.weightAccumulated = weightAccumulated;
	}

	public void walkLeft()
	{
		setWeightAccumulated(getWeightAccumulated() + 
				((NodeBinaryWeight) getNode()).getLeftWeight());
		setNode(((NodeBinary) getNode()).getLeftNode());
	}

	public void walkRight()
	{
		setWeightAccumulated(getWeightAccumulated() + 
				((NodeBinaryWeight) getNode()).getRightWeight());
		setNode(((NodeBinary) getNode()).getRightNode());
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
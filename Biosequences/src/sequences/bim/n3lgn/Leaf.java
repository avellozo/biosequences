/*
 * Criado em 09/08/2004
 */
package sequences.bim.n3lgn;


/**
 * @author Augusto
 * @data 09/08/2004
 */
public class Leaf implements Node
{
	private int index;
	
	public Leaf(int index)
	{
		this.index = index;
	}

	/* (non-Javadoc)
	 * @see sequences.Node#isLeaf()
	 */
	public boolean isLeaf()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see sequences.Node#getEdgeOutQtty()
	 */
	public int getNodeQtty()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see sequences.Node#getNode(int)
	 */
	public Node getNode(int i)
	{
		return null;
	}

	public int getIndex()
	{
		return index;
	}

	/* (non-Javadoc)
	 * @see sequences.Node#setNode(int, sequences.Node)
	 */
	public void setNode(int i, Node node)
	{
		throw new RuntimeException("Não é possível adicionar filhos a uma folha");
	}

//	public Object clone()
//	{
//		try
//		{
//			return super.clone();
//		}
//		catch (CloneNotSupportedException e)
//		{
//			e.printStackTrace();
//			throw new InternalError();
//		}
//	}

	public Object clone()
	{
		return this;
	}

}

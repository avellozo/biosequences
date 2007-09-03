/*
 * Criado em 25/08/2004
 */

package sequences.bim.n3lgn;


/**
 * @author Augusto @data 25/08/2004
 */
public class WalkerNewPath extends Walker
{
	Walker	wLeft;
	Walker	wRight;

	public WalkerNewPath(Node node, int weightAccumulated, Walker w1, Walker w2)
	{
		super(node, weightAccumulated);
		this.wLeft = w1;
		this.wRight = w2;
	}

	public void walkLeft()
	{
		//insere subárovre direita do nó2 a direita do nó corrente
//		if (wRight.getRightNode() != null)
//		{
//			addRightSubtree();
//		}
		//se chegou na folha
		if (getLeftNode().isLeaf())
		{
			//somente seta o peso da aresta com o peso acumulado do nó2
			((NodeBinaryWeight) getNode()).setLeftWeight(wRight.getLeftWeightAccumulated());
		}
		else
		{
			//cria novo nó a esquerda do nó
			((NodeBinaryWeight) getNode()).cloneLeft(0);
		}
		//acerta peso da subárovre esquerda
		//precisa ser depois do cloneLeft(0)
		if (((NodeJeanette) wRight.getNode()).isLeftSubtreeComplete())
		{
			((NodeJeanette) getNode()).completedLeftSubtree(wRight
					.getMiddleWeightAccumulated());
		}
		super.walkLeft();
		wLeft.walkLeft();
		wRight.walkLeft();
	}

	public void walkRight()
	{
//		addLeftSubtree();
//		//acerta peso da subárovre esquerda
//		((NodeJeanette) getNode()).completedLeftSubtree(wLeft
//				.getMiddleWeightAccumulated());
		//verifica se chegou no fim
		if ((wLeft.getRightNode() == null) || (getRightNode().isLeaf()))
		{
			addRightSubtree();
		}
		else
		{
			//cria novo nó a direita do nó corrente
			((NodeBinaryWeight) node).cloneRight(0);
		}
		super.walkRight();
		wLeft.walkRight();
		wRight.walkRight();
	}
	
	public void addRightSubtree()
	{
		//insere subárovre direita do nó2 a direita do nó corrente
		((NodeBinaryWeight) getNode()).setRightNode(wRight.getRightNode(), 
		wRight.getRightWeightAccumulated());
	}
	
	public void addLeftSubtree()
	{
		//insere subárovre esquerda do nó1 a eaquerda do nó corrente
		((NodeBinaryWeight) getNode()).setLeftNode(wLeft.getLeftNode(), 
		wLeft.getLeftWeightAccumulated());
		//acerta peso da subárovre esquerda
		((NodeJeanette) getNode()).completedLeftSubtree(wLeft
				.getMiddleWeightAccumulated());
	}
	
	public boolean isToLeft()
	{
		return (getRightNode() == null) || 
			(wLeft.getMiddleWeightAccumulated() < 
					wRight.getMiddleWeightAccumulated());
	}
	
	public boolean endOfPath()
	{
		return (getNode().isLeaf() || (wLeft.getNode() == null));
	}
	public Walker getWLeft()
	{
		return wLeft;
	}

	public void setWLeft(Walker left)
	{
		wLeft = left;
	}

	public Walker getWRight()
	{
		return wRight;
	}

	public void setWRight(Walker right)
	{
		wRight = right;
	}

}

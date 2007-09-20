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
		//insere sub�rovre direita do n�2 a direita do n� corrente
//		if (wRight.getRightNode() != null)
//		{
//			addRightSubtree();
//		}
		//se chegou na folha
		if (getLeftNode().isLeaf())
		{
			//somente seta o peso da aresta com o peso acumulado do n�2
			((NodeBinaryWeight) getNode()).setLeftWeight(wRight.getLeftWeightAccumulated());
		}
		else
		{
			//cria novo n� a esquerda do n�
			((NodeBinaryWeight) getNode()).cloneLeft(0);
		}
		//acerta peso da sub�rovre esquerda
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
//		//acerta peso da sub�rovre esquerda
//		((NodeJeanette) getNode()).completedLeftSubtree(wLeft
//				.getMiddleWeightAccumulated());
		//verifica se chegou no fim
		if ((wLeft.getRightNode() == null) || (getRightNode().isLeaf()))
		{
			addRightSubtree();
		}
		else
		{
			//cria novo n� a direita do n� corrente
			((NodeBinaryWeight) node).cloneRight(0);
		}
		super.walkRight();
		wLeft.walkRight();
		wRight.walkRight();
	}
	
	public void addRightSubtree()
	{
		//insere sub�rovre direita do n�2 a direita do n� corrente
		((NodeBinaryWeight) getNode()).setRightNode(wRight.getRightNode(), 
		wRight.getRightWeightAccumulated());
	}
	
	public void addLeftSubtree()
	{
		//insere sub�rovre esquerda do n�1 a eaquerda do n� corrente
		((NodeBinaryWeight) getNode()).setLeftNode(wLeft.getLeftNode(), 
		wLeft.getLeftWeightAccumulated());
		//acerta peso da sub�rovre esquerda
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

/*
 * Criado em 26/08/2004
 */

package sequences.bim.n3lgn;


/**
 * @author Augusto @data 26/08/2004
 */
public class WalkerCommonNewPath extends WalkerNewPath
{
	Walker	wCenter;

	public WalkerCommonNewPath(Node node, int weightAccumulated, Walker w1, 
	Walker w2, Walker w3)
	{
		super(node, weightAccumulated, w1, w3);
		this.wCenter = w2;
	}

	public void walkLeft()
	{
		super.walkLeft();
		wCenter.walkLeft();
	}

	public void walkRight()
	{
		super.walkRight();
		wCenter.walkRight();
	}

	public boolean nextSamePath()
	{
		//só existe um caminho a seguir
		return ((getRightNode() == null) || 
		//caminho 2 vai a esquerda ou
				(wCenter.getMiddleWeightAccumulated() < 
				wRight.getMiddleWeightAccumulated()) || 
		//caminho1 vai a direita
		(wLeft.getMiddleWeightAccumulated() >= 
		wCenter.getMiddleWeightAccumulated()));
	}

	public WalkerNewPath getPathLeft()
	{
		WalkerNewPath ret = new WalkerNewPath(getNode(), getWeightAccumulated(), 
			(Walker) wLeft.clone(), (Walker) wCenter.clone());
		ret.walkLeft();
		return ret;
	}

	public WalkerNewPath getPathRight()
	{
		WalkerNewPath ret = new WalkerNewPath(getNode(), getWeightAccumulated(),
				(Walker) wCenter.clone(), (Walker) wRight.clone());
		ret.walkRight();
		return ret;
	}
	public Walker getWCenter()
	{
		return wCenter;
	}

	public void setWCenter(Walker center)
	{
		wCenter = center;
	}

}

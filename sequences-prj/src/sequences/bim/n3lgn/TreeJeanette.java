/*
 * Criado em 09/08/2004
 */

package sequences.bim.n3lgn;


/**
 * @author Augusto
 * @data 09/08/2004
 */
public class TreeJeanette
{
	NodeJeanette	root;
	//	int row = -1;
	//	int col = -1;
	byte			height;
	int				numLeafs;

	public TreeJeanette(TreeJeanette treeLeft, TreeJeanette treeUpLeft,
			TreeJeanette treeUp, int weightLeft, int weightUpLeft, int weightUp)
	{
		//		this.row = row;
		//		this.col = col;
		//		NodeJeanette root1;
		//		Node nodeLeft, nodeRight;
		int additionalWeight;
		if (treeUp == null) // row==0
		{
			setHeight((byte) 1);
			setNumLeafs(1);
			if (treeLeft == null) // row==0 e col==0
			{
				setRoot(newNode());
				getRoot().setLeftNode(new Leaf(0), 0);
				getRoot().completedLeftSubtree(0);
			}
			else
			// row==0 e col!=0
			{
				additionalWeight = weightLeft;
				setRoot((NodeJeanette) treeLeft.getRoot().clone(
					additionalWeight));
			}
		}
		else
		// row!=0
		{
			if (treeLeft == null) // col==0
			{
				additionalWeight = weightUp;
				//cria uma nova árvore somando um peso para todas as folhas
				// da árvore acima
				setRoot((NodeJeanette) treeUp.getRoot().clone(additionalWeight));
				setHeight(treeUp.getHeight());
				//adiciona a folha "row" com peso zero
				setNumLeafs(treeUp.getNumLeafs() + 1);
				addLeaf(treeUp.getNumLeafs(), 0);
			}
			else
			// row!=0 e col!=0
			{
				setHeight(treeLeft.getHeight());
				setNumLeafs(treeLeft.getNumLeafs());
				setRoot((NodeJeanette) treeLeft.getRoot().clone());
				//treeUp = A, treeUpLeft = B, treeLeft = C
				Walker wA = new Walker(treeUp.getRoot(), weightUp);
				Walker wB = new Walker(treeUpLeft.getRoot(), weightUpLeft);
				Walker wC = new Walker(treeLeft.getRoot(), weightLeft);
				WalkerCommonNewPath wCommon = new WalkerCommonNewPath(
					getRoot(), 0, wA, wB, wC);
				//Verifica se tem um nível a mais
				if (((NodeJeanette) wC.getNode()).isLeftSubtreeComplete()
					&& wC.getRightNode() == null)
				{
					//só anda com wC e wCommon
					Walker wA1 = (Walker) wA.clone();
					Walker wB1 = (Walker) wB.clone();
					wCommon.walkLeft();
					wCommon.setWLeft(wA1);
					wCommon.setWCenter(wB1);
				}
				while (!wCommon.endOfPath() && wCommon.nextSamePath())
				{
					if (wCommon.isToLeft())
					{
						wCommon.addRightSubtree();
						wCommon.walkLeft();
					}
					else
					{
						wCommon.addLeftSubtree();
						wCommon.walkRight();
					}
				}
				if (!wCommon.endOfPath())
				{
					WalkerNewPath w1 = wCommon.getPathLeft();
					WalkerNewPath w2 = wCommon.getPathRight();

					while (!w1.endOfPath())
					{
						if (w1.isToLeft())
						{
							w1.addRightSubtree();
							w1.walkLeft();
						}
						else
						{
							w1.addLeftSubtree();
							w1.walkRight();
						}
					}
					while (!w2.endOfPath())
					{
						if (w2.isToLeft())
						{
							w2.addRightSubtree();
							w2.walkLeft();
						}
						else
						{
							w2.addLeftSubtree();
							w2.walkRight();
						}
					}
				}
			}
		}
	}

	protected void addLeaf(int leafNumber, int weight)
	{
		NodeJeanette node = getRoot();
		NodeJeanette newNode;
		NodeJeanette lastNodeToLeft = null;
		while (!node.isLeftSubtreeComplete() || (node.getRightNode() != null))
		{
			if (node.getRightNode() != null)
			{
				//o nó direito não é folha, pois a árvore não é completa
				newNode = (NodeJeanette) ((NodeBinaryWeight) node
					.getRightNode()).clone(node.getRightWeight());
				node.setRightNode(newNode, 0);
			}
			else
			// a subárvore esquerda não é completa
			{
				newNode = (NodeJeanette) ((NodeBinaryWeight) node.getLeftNode())
					.clone(node.getLeftWeight());
				node.setLeftNode(newNode, 0);
				lastNodeToLeft = node;
			}
			node = newNode;
		}
		//cria novo caminho a partir de node
		NodeJeanette oldPathNode = node;
		//Cria a primeira aresta para a direita
		if (!node.getLeftNode().isLeaf())
		{
			//se não chegou ao fim cria um nó interno
			node.setRightNode(newNode(), 0);
			node = (NodeJeanette) node.getRightNode();
			oldPathNode = (NodeJeanette) oldPathNode.getLeftNode();
			//Cria as outras arestas para a esquerda
			while (!oldPathNode.getLeftNode().isLeaf())
			{
				node.setLeftNode(newNode(), 0);
				node = (NodeJeanette) node.getLeftNode();
				oldPathNode = (NodeJeanette) oldPathNode.getLeftNode();
			}
			//Cria a folha para a esquerda
			node.setLeftNode(new Leaf(leafNumber), weight);
			lastNodeToLeft = node;
		}
		else
		//a aresta para a direita é para uma folha
		{
			node.setRightNode(new Leaf(leafNumber), weight);
		}
		// verifica se aumenta um nível
		if (lastNodeToLeft == null)
		{
			lastNodeToLeft = newNode();
			lastNodeToLeft.setLeftNode(getRoot(), 0);
			setRoot(lastNodeToLeft);
			setHeight((byte) (getHeight() + 1));
		}
		lastNodeToLeft.completedLeftSubtree(weight);
	}

	public int getScoringPath(int leafNumber)
	{
		Walker walker = new Walker(getRoot(), 0);
		int mask = 1 << (getHeight() - 1);
		while (mask != 0)
		{
			if ((leafNumber & mask) == 0)
			{
				walker.walkLeft();
			}
			else
			{
				walker.walkRight();
			}
			mask >>= 1;
		}
		return walker.getWeightAccumulated();
	}

	public NodeJeanette getRoot()
	{
		return root;
	}

	public void setRoot(NodeJeanette root)
	{
		this.root = root;
	}

	protected NodeJeanette newNode()
	{
		return new NodeJeanette();
	}

	public byte getHeight()
	{
		return height;
	}

	public int getNumLeafs()
	{
		return this.numLeafs;
	}

	public void setNumLeafs(int numLeafs)
	{
		this.numLeafs = numLeafs;
	}

	public void setHeight(byte height)
	{
		this.height = height;
	}
}
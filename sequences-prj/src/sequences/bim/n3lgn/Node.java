/*
 * Criado em 09/08/2004
 */
package sequences.bim.n3lgn;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public interface Node extends Cloneable
{
	public boolean isLeaf();
	
	public int getNodeQtty();
	
	public Node getNode(int i);
	
	public void setNode(int i, Node node);
	
	public Object clone();
}

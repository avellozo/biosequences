/*
 * Created on 08/07/2006
 */
package sequences.editgraph;

/**
 * @author Augusto F. Vellozo
 */
public interface Arc
{
	public static final char	VERTICAL	= 'V';

	public static final char	HORIZONTAL	= 'H';

	public static final char	DIAGONAL	= 'D';

	public static final char	EXTENDED	= 'E';

	public Vertex getBeginVertex();

	public Vertex getEndVertex();

	public int getWeight();

}
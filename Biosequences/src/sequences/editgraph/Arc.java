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

	public static final char	INVALID		= 'I';

	public static final char	GAP_HOR		= 'R';	//Gap estendido horizontal

	public static final char	GAP_VERT	= 'C';	//Gap estendido vertical

	public static final char	JUNCTION	= 'J';	//arco sem peso que junta dois vértices

	public static final char	DUP_HOR		= 'U';	//arco estendido de uma duplicação horizontal

	public static final char	DUP_VERT	= 'P';	//arco estendido de uma duplicação vertical

	public Vertex getBeginVertex();

	public Vertex getEndVertex();

	public int getWeight();

}
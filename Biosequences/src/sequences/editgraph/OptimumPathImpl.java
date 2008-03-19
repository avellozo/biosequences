package sequences.editgraph;

import java.util.LinkedList;

import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.ArcExtendedOverEG;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcJuntion;
import sequences.editgraph.arcs.ArcVertical;
import sequences.editgraph.exception.ExceptionInvalidVertex;
import sequences.matrix.MatrixCharRange;
import sequences.matrix.MatrixInt;

public class OptimumPathImpl implements OptimumPath
{

	//	VertexRange				range;
	EditGraph				eg;
	//	boolean					local;
	String					methodName;

	LinkedList<Arc>			arcs				= new LinkedList<Arc>();
	LinkedList<ArcExtended>	arcsExtended		= new LinkedList<ArcExtended>();
	int						score				= 0;
	int						qttyArcs			= 0;
	int						qttyExtendedArcs	= 0;
	int						qttyHorizontalArcs	= 0;
	int						qttyVerticalArcs	= 0;
	int						qttyMatches			= 0;
	int						qttyMismatches		= 0;

	//	protected int			iMin, iMax, jMin, jMax, rows, cols;

	//	public OptimumPathImpl(VertexRange range, EditGraph eg, boolean local)
	public OptimumPathImpl(EditGraph eg, String methodName)
	{
		//		this.range = range;
		this.eg = eg;
		this.methodName = methodName;
		//		this.local = local;
		//		iMin = getVertexRange().getBeginVertex().getRow();
		//		jMin = getVertexRange().getBeginVertex().getCol();
		//		iMax = getVertexRange().getEndVertex().getRow();
		//		jMax = getVertexRange().getEndVertex().getCol();
		//		rows = getVertexRange().getRowsQtty();
		//		cols = getVertexRange().getColsQtty();
	}

	public EditGraph getEditGraph()
	{
		return eg;
	}

	//	public VertexRange getVertexRange()
	//	{
	//		return range;
	//	}

	//	public boolean isLocal()
	//	{
	//		return local;
	//	}
	//
	public LinkedList<Arc> getArcs()
	{
		return arcs;
	}

	public Arc getFirst()
	{
		return arcs.getFirst();
	}

	public Arc getLast()
	{
		return arcs.getLast();
	}

	public boolean add(Arc arc)
	{
		updateStatistics(arc);
		return arcs.add(arc);
	}

	public void addFirst(Arc arc)
	{
		updateStatistics(arc);
		arcs.addFirst(arc);
	}

	protected void updateStatistics(Arc arc)
	{
		score += arc.getWeight();
		qttyArcs++;
		if (arc instanceof ArcHorizontal)
		{
			qttyHorizontalArcs++;
		}
		else if (arc instanceof ArcVertical)
		{
			qttyVerticalArcs++;
		}
		else if (arc instanceof ArcDiagonal)
		{
			if (((ArcDiagonal) arc).isMatch())
			{
				qttyMatches++;
			}
			else
			{
				qttyMismatches++;
			}
		}
		else if (arc instanceof ArcExtended)
		{
			qttyExtendedArcs++;
			arcsExtended.add((ArcExtended) arc);
			if (arc instanceof ArcExtendedOverEG)
			{
				OptimumPath path = ((ArcExtendedOverEG) arc).getOptimumPath();
				qttyExtendedArcs += path.getQttyExtendedArcs();
				qttyHorizontalArcs += path.getQttyHorizontalArcs();
				qttyVerticalArcs += path.getQttyVerticalArcs();
				qttyMatches += path.getQttyMatches();
				qttyMismatches += path.getQttyMismatches();
			}
		}
	}

	public int getScore()
	{
		return score;
	}

	public int getQttyExtendedArcs()
	{
		return qttyExtendedArcs;
	}

	public int getQttyHorizontalArcs()
	{
		return qttyHorizontalArcs;
	}

	public int getQttyVerticalArcs()
	{
		return qttyVerticalArcs;
	}

	public int getQttyMatches()
	{
		return qttyMatches;
	}

	public int getQttyMismatches()
	{
		return qttyMismatches;
	}

	public LinkedList<ArcExtended> getArcsExtended()
	{
		return arcsExtended;
	}

	public int getQttyArcs()
	{
		return qttyArcs;
	}

	public int max(int x, int y, int z)
	{
		return Math.max(x, Math.max(y, z));
	}

	public int max(int x, int y)
	{
		return Math.max(x, y);
	}

	public static OptimumPath buildPath(MatrixInt m, MatrixCharRange arcsType, Vertex optimumVertex, EditGraph eg,
			String name, int gapOpenPenalty, EditGraph egForGap, int beginJunction) throws ExceptionInvalidVertex
	{
		OptimumPathImpl path = new OptimumPathImpl(eg, name);
		Vertex v = optimumVertex;
		char c;
		Arc arc;
		Vertex v1;
		int mIJ, wGap, i, j;
		while ((c = arcsType.getValue(i = v.getRow(), j = v.getCol())) != Arc.INVALID)
		{
			switch (c)
			{
				case Arc.DIAGONAL:
					arc = eg.getDiagonalArc(v);
					break;
				case Arc.VERTICAL:
					arc = eg.getVerticalArc(v);
					break;
				case Arc.HORIZONTAL:
					arc = eg.getHorizontalArc(v);
					break;
				case Arc.GAP_VERT:
					mIJ = m.getValue(i, j);
					wGap = gapOpenPenalty;
					arc = egForGap.getVerticalArc(v);
					v1 = arc.getBeginVertex();
					while (m.getValue(v1.getRow(), v1.getCol()) + arc.getWeight() + wGap != mIJ)
					{
						wGap += arc.getWeight();
						arc = egForGap.getVerticalArc(v1);
						v1 = arc.getBeginVertex();
					}
					arc = eg.getExtendedArc(new VertexRange(v1, v));
					break;
				case Arc.GAP_HOR:
					mIJ = m.getValue(i, j);
					wGap = gapOpenPenalty;
					arc = egForGap.getHorizontalArc(v);
					v1 = arc.getBeginVertex();
					while (m.getValue(v1.getRow(), v1.getCol()) + arc.getWeight() + wGap != mIJ)
					{
						wGap += arc.getWeight();
						arc = egForGap.getHorizontalArc(v1);
						v1 = arc.getBeginVertex();
					}
					arc = eg.getExtendedArc(new VertexRange(v1, v));
					break;
				case Arc.JUNCTION:
					arc = new ArcJuntion(new VertexRange(eg.getVertex(i, beginJunction), v));
					break;
				default:
					throw new RuntimeException("Tipo de arco inválido: " + c);
			}
			path.addFirst(arc);
			v = arc.getBeginVertex();
		}
		return path;
	}

	public static OptimumPath buildPath(MatrixInt m, MatrixCharRange arcsType, Vertex optimumVertex, EditGraph eg,
			String name) throws ExceptionInvalidVertex
	{
		return buildPath(m, arcsType, optimumVertex, eg, name, 0, null, 0);
	}

	public static OptimumPath buildPath(MatrixInt m, MatrixCharRange arcsType, Vertex optimumVertex, EditGraph eg,
			String name, int gapOpenPenalty, EditGraph egForGap) throws ExceptionInvalidVertex
	{
		return buildPath(m, arcsType, optimumVertex, eg, name, gapOpenPenalty, egForGap, 0);
	}
}

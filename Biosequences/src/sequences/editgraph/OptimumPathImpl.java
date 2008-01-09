package sequences.editgraph;

import java.util.LinkedList;

import sequences.editgraph.arcs.ArcDiagonal;
import sequences.editgraph.arcs.ArcExtended;
import sequences.editgraph.arcs.ArcExtendedOverEG;
import sequences.editgraph.arcs.ArcHorizontal;
import sequences.editgraph.arcs.ArcVertical;

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
				OptimumPath pathEGExtender = ((ArcExtendedOverEG) arc).getOptimumPath();
				qttyExtendedArcs += pathEGExtender.getQttyExtendedArcs();
				qttyHorizontalArcs += pathEGExtender.getQttyHorizontalArcs();
				qttyVerticalArcs += pathEGExtender.getQttyVerticalArcs();
				qttyMatches += pathEGExtender.getQttyMatches();
				qttyMismatches += pathEGExtender.getQttyMismatches();
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

}

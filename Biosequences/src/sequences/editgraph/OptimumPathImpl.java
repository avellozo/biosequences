package sequences.editgraph;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import sequences.ui.Messages;
import sequences.ui.PrinterPath;

// TODO OptimumPath extend LinkedList
public class OptimumPathImpl<E extends EditGraph<E, ? extends Extender<E>>> implements OptimumPath<E>
{

	LinkedList<Arc<E>>			arcs				= new LinkedList<Arc<E>>();
	LinkedList<ArcExtended<E>>	arcsExtended;
	VertexRange<E>				range;
	long						finishTime;
	long						startTime;
	boolean						local;
	private boolean				changedArcs			= false;

	int							score				= 0;
	int							qttyArcs			= 0;
	int							qttyExtendedArcs	= 0;
	int							qttyHorizontalArcs	= 0;
	int							qttyVerticalArcs	= 0;
	int							qttyMatches			= 0;
	int							qttyMismatches		= 0;

	protected E					eg;
	protected int				iMin, iMax, jMin, jMax, rows, cols;

	public OptimumPathImpl(VertexRange<E> range, boolean local) throws EGInvalidRangeException
	{
		startTime();
		if (range == null)
		{
			throw new EGInvalidRangeException("Invalid range: null");
		}
		this.range = range;
		this.local = local;
		eg = getEditGraph();
		iMin = getVertexRange().getBeginVertex().getI();
		jMin = getVertexRange().getBeginVertex().getJ();
		iMax = getVertexRange().getEndVertex().getI();
		jMax = getVertexRange().getEndVertex().getJ();
		rows = getVertexRange().getRows();
		cols = getVertexRange().getCols();
		finishTime();
	}

	public LinkedList<Arc<E>> getArcs()
	{
		return arcs;
	}

	public Arc<E> getFirst()
	{
		return arcs.getFirst();
	}

	public Arc<E> getLast()
	{
		return arcs.getLast();
	}

	public boolean add(Arc<E> arc)
	{
		return (changedArcs = arcs.add(arc));
	}

	public boolean addFirst(Arc<E> arc)
	{
		arcs.addFirst(arc);
		return (changedArcs = true);
	}

	public E getEditGraph()
	{
		return getVertexRange().getEditGraph();
	}

	// public List<ArcDiagonal> getMatches() {
	// if (matches == null){
	// matches = new LinkedList<ArcDiagonal >();
	// for (Arc<E> arc : getArcs())
	// {
	// try {
	// if ((arc instanceof ArcDiagonal) && (getEditGraph().isMatch((ArcDiagonal
	// <E>)arc)))
	// {
	// matches.add((ArcDiagonal <E>)arc);
	// }
	// if (arc instanceof ArcExtendedOverInversion)
	// {
	// matches.addAll(((ArcExtendedOverInversion) arc).getMatches());
	// }
	// } catch (EGInvalidArcException e) {
	// e.printStackTrace();
	// throw new EGInternalException();
	// }
	// }
	// }
	// return matches;
	// }
	//
	public boolean isLocal()
	{
		return local;
	}

	public int getScore()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return score;
	}

	protected void calculateStatistics()
	{
		OptimumPath pathEGExtender;
		qttyHorizontalArcs = 0;
		qttyVerticalArcs = 0;
		qttyMatches = 0;
		qttyMismatches = 0;

		score = 0;
		qttyExtendedArcs = 0;
		qttyArcs = 0;
		arcsExtended = new LinkedList<ArcExtended<E>>();
		for (Arc<E> arc : arcs)
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
			else if (arc instanceof ArcExtendedOverEGExtender)
			{
				qttyExtendedArcs++;
				arcsExtended.add((ArcExtended<E>) arc);
				if (arc instanceof ArcExtendedOverEGExtender)
				{
					pathEGExtender = ((ArcExtendedOverEGExtender) arc).getPathEGExtender();
					qttyExtendedArcs += pathEGExtender.getQttyExtendedArcs();
					qttyHorizontalArcs += pathEGExtender.getQttyHorizontalArcs();
					qttyVerticalArcs += pathEGExtender.getQttyVerticalArcs();
					qttyMatches += pathEGExtender.getQttyMatches();
					qttyMismatches += pathEGExtender.getQttyMismatches();
				}
			}
			else if (arc instanceof ArcDiagonal)
			{
				try
				{
					if (getEditGraph().isMatch((ArcDiagonal<E>) arc))
					{
						qttyMatches++;
					}
					else
					{
						qttyMismatches++;
					}
				}
				catch (EGInvalidArcException e)
				{
					e.printStackTrace();
					throw new EGInternalException();
				}
			}
		}
	}

	public VertexRange<E> getVertexRange()
	{
		return range;
	}

	public long time()
	{
		return finishTime - startTime;
	}

	private long startTime()
	{
		return (startTime = System.currentTimeMillis());
	}

	public long finishTime()
	{
		return (finishTime = System.currentTimeMillis());
	}

	public int max(int x, int y, int z)
	{
		return Math.max(x, Math.max(y, z));
	}

	public int max(int x, int y)
	{
		return Math.max(x, y);
	}

	public int getQttyExtendedArcs()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyExtendedArcs;
	}

	public int getQttyHorizontalArcs()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyHorizontalArcs;
	}

	public int getQttyMatches()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyMatches;
	}

	public int getQttyMismatches()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyMismatches;
	}

	public int getQttyVerticalArcs()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyVerticalArcs;
	}

	public LinkedList<ArcExtended<E>> getArcsExtended()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return arcsExtended;
	}

	public int getQttyArcs()
	{
		if (changedArcs)
		{
			calculateStatistics();
			changedArcs = false;
		}
		return qttyArcs;
	}

}

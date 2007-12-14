package sequences.editgraph;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import sequences.ui.Messages;
import sequences.ui.PrinterPath;

// TODO OptimumPath extend LinkedList
public class OptimumPathImpl<E extends EditGraph<E, ? extends Extender>> implements OptimumPath
{

	LinkedList<Arc>			arcs				= new LinkedList<Arc>();
	LinkedList<ArcExtended>	arcsExtended;
	EditGraphSegment				range;
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

	public OptimumPathImpl(EditGraphSegment range, boolean local) throws EGInvalidVertexesOfExtensionException
	{
		startTime();
		if (range == null)
		{
			throw new EGInvalidVertexesOfExtensionException("Invalid range: null");
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
		return (changedArcs = arcs.add(arc));
	}

	public boolean addFirst(Arc arc)
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
	// for (Arc arc : getArcs())
	// {
	// try {
	// if ((arc instanceof ArcDiagonal) && (getEditGraph().isMatch((ArcDiagonal
	// )arc)))
	// {
	// matches.add((ArcDiagonal )arc);
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
		arcsExtended = new LinkedList<ArcExtended>();
		for (Arc arc : arcs)
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
				arcsExtended.add((ArcExtended) arc);
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
					if (getEditGraph().isMatch((ArcDiagonal) arc))
					{
						qttyMatches++;
					}
					else
					{
						qttyMismatches++;
					}
				}
				catch (ExceptionInvalidArc e)
				{
					e.printStackTrace();
					throw new ExceptionInternalEG();
				}
			}
		}
	}

	public EditGraphSegment getVertexRange()
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

	public LinkedList<ArcExtended> getArcsExtended()
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

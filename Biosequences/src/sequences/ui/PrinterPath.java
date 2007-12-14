/*
 * Created on 03/08/2006
 */
package sequences.ui;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import sequences.editgraph.Arc;
import sequences.editgraph.ArcDiagonal;
import sequences.editgraph.ArcExtended;
import sequences.editgraph.ArcExtendedOverEGExtender;
import sequences.editgraph.ArcHorizontal;
import sequences.editgraph.ArcVertical;
import sequences.editgraph.ArcFactorySimpleSequences;
import sequences.editgraph.ExceptionGeneralEG;
import sequences.editgraph.ExceptionInternalEG;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathFactory;
import sequences.editgraph.OptimumPathSimpleFactory;
import sequences.editgraph.Vertex;

public class PrinterPath
{
	public static char	MATCH_LINE2						= '|';
	public static char	MISMATCH_LINE2					= ' ';
	public static char	GAP_LINE2						= ' ';
	public static char	GAP_LINE1						= '-';
	public static char	GAP_LINE3						= '-';
	public static char	MATCH_LINE2_EXTENDED			= '|';
	public static char	MISMATCH_LINE2_EXTENDED			= ' ';
	public static char	GAP_LINE2_EXTENDED				= ' ';
	public static char	GAP_LINE1_EXTENDED				= '-';
	public static char	GAP_LINE3_EXTENDED				= '-';
	public static char	FRAGMENTS_LETTER				= '+';
	public static char	FRAGMENTS_SEPARATOR				= '*';
	public static char	FRAGMENTS_LETTER_EXTENDED		= '#';
	public static char	FRAGMENTS_SEPARATOR_EXTENDED	= '=';

	public static void printBlast(OptimumPath path, PrintStream out, int maxCols)
	{
		List<Arc> arcs = path.getArcs();
		OptimumPath pathEGExtender;
		EditGraph eg = path.getEditGraph();
		List<Arc> arcsPathEGExtender;
		Vertex v;
		LinePrinterBlast p = new LinePrinterBlast(out, maxCols);
		int posC1, posC3;
		char c1, c2, c3;

		for (Arc arc : arcs)
		{
			if (arc instanceof ArcExtendedOverEGExtender)
			{
				pathEGExtender = ((ArcExtendedOverEGExtender) arc).getPathEGExtender();
				arcsPathEGExtender = pathEGExtender.getArcs();
				p.preparePrintExtended(pathEGExtender.getQttyArcs());
				for (Arc arcPathEGExtender : arcsPathEGExtender)
				{
					try
					{
						v = ((ArcExtendedOverEGExtender) arc).transformVertexEGExtender(arcPathEGExtender
							.getEndVertex());
					}
					catch (ExceptionInvalidVertex e)
					{
						e.printStackTrace();
						throw new ExceptionInternalEG();
					}
					if (arcPathEGExtender instanceof ArcDiagonal)
					{
						c1 = getLetterIExtended(eg, v.getRow());
						c2 = (((ArcDiagonal) arcPathEGExtender).isMatch() ? MATCH_LINE2_EXTENDED
							: MISMATCH_LINE2_EXTENDED);
						c3 = getLetterJExtended(eg, v.getCol());
					}
					else if (arcPathEGExtender instanceof ArcHorizontal)
					{
						c1 = GAP_LINE1_EXTENDED;
						c2 = GAP_LINE2_EXTENDED;
						c3 = getLetterJExtended(eg, v.getCol());
					}
					else if (arcPathEGExtender instanceof ArcVertical)
					{
						c1 = getLetterIExtended(eg, v.getRow());
						c2 = GAP_LINE2_EXTENDED;
						c3 = GAP_LINE3_EXTENDED;
					}
					else
					{
						throw new ExceptionInternalEG();
					}
					posC1 = v.getRow();
					posC3 = v.getCol();
					p.print(c1, c2, c3, posC1, posC3);
				}
			}
			else
			{
				if (arc instanceof ArcDiagonal)
				{
					c1 = getLetterI(eg, arc.getEndVertex().getRow());
					c2 = (((ArcDiagonal) arc).isMatch() ? MATCH_LINE2 : MISMATCH_LINE2);
					c3 = getLetterJ(eg, arc.getEndVertex().getCol());
				}
				else if (arc instanceof ArcHorizontal)
				{
					c1 = GAP_LINE1;
					c2 = GAP_LINE2;
					c3 = getLetterJ(eg, arc.getEndVertex().getCol());
				}
				else if (arc instanceof ArcVertical)
				{
					c1 = getLetterI(eg, arc.getEndVertex().getRow());
					c2 = GAP_LINE2;
					c3 = GAP_LINE3;
				}
				else
				{
					throw new ExceptionInternalEG();
				}
				posC1 = arc.getEndVertex().getRow();
				posC3 = arc.getEndVertex().getCol();
				p.print(c1, c2, c3, posC1, posC3);
			}
		}
		p.flush();
	}

	public static void printMatches(OptimumPath path, PrintStream out)
	{
		List<Arc> arcs = path.getArcs();
		OptimumPath pathEGExtender;
		List<Arc> arcsPathEGExtender;
		Vertex v;
		for (Arc arc : arcs)
		{
			if ((arc instanceof ArcDiagonal) && (((ArcDiagonal) arc).isMatch()))
			{
				out.println(MessageFormat.format(Messages.getString("Bim.msgMatch0") //$NON-NLS-1$
					, arc.getEndVertex().getRow(), arc.getEndVertex().getCol()));
			}
			else if (arc instanceof ArcExtendedOverEGExtender)
			{
				pathEGExtender = ((ArcExtendedOverEGExtender) arc).getPathEGExtender();
				arcsPathEGExtender = pathEGExtender.getArcs();
				for (Arc arcPathEGExtender : arcsPathEGExtender)
				{
					if ((arcPathEGExtender instanceof ArcDiagonal) && (((ArcDiagonal) arcPathEGExtender).isMatch()))
					{
						try
						{
							v = ((ArcExtendedOverEGExtender) arc).transformVertexEGExtender(arcPathEGExtender
								.getEndVertex());
							out.println(MessageFormat.format(Messages.getString("Bim.msgMatch1") //$NON-NLS-1$
								, v.getRow(), v.getCol()));
						}
						catch (ExceptionInvalidVertex e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void printStatistics(OptimumPath path, PrintStream out)
	{
		try
		{
			out.println(Messages.getString("Bim.msgLegend"));
			if (path.isLocal())
			{
				out.println(MessageFormat.format(Messages.getString("Bim.msgLocalAlignment"), path.getFirst()
					.getBeginVertex().getRow() + 1, path.getFirst().getBeginVertex().getCol() + 1, path.getLast()
					.getEndVertex().getRow(), path.getLast().getEndVertex().getCol())); //$NON-NLS-1$
			}
			else
			{
				out.println(MessageFormat.format(Messages.getString("Bim.msgGlobalAlignment"), path.getVertexRange()
					.getBeginVertex().getRow() + 1, path.getVertexRange().getBeginVertex().getCol() + 1, path
					.getVertexRange().getEndVertex().getRow(), path.getVertexRange().getEndVertex().getCol())); //$NON-NLS-1$
			}

			int lengthSeq1 = path.getVertexRange().getEndVertex().getRow()
				- path.getVertexRange().getBeginVertex().getRow();
			int lengthSeq2 = path.getVertexRange().getEndVertex().getCol()
				- path.getVertexRange().getBeginVertex().getCol();
			out.println(MessageFormat.format(Messages.getString("Bim.msgLengthSeq1"), lengthSeq1)); //$NON-NLS-1$
			out.println(MessageFormat.format(Messages.getString("Bim.msgLengthSeq2"), lengthSeq2)); //$NON-NLS-1$

			double pathILength = path.getLast().getEndVertex().getRow() - path.getFirst().getBeginVertex().getRow();
			double pathJLength = path.getLast().getEndVertex().getCol() - path.getFirst().getBeginVertex().getCol();
			double lengthAverage = (pathILength + pathJLength) / 2;
			int qttyGaps = path.getQttyHorizontalArcs() + path.getQttyVerticalArcs();
			int qttyMatches = path.getQttyMatches();
			int qttyMismatches = path.getQttyMismatches();

			out.println(MessageFormat.format(Messages.getString("Bim.msglengthAlignedSeq1") //$NON-NLS-1$
				, pathILength, (pathILength / lengthSeq1) * 100));
			out.println(MessageFormat.format(Messages.getString("Bim.msglengthAlignedSeq2") //$NON-NLS-1$
				, pathJLength, (pathJLength / lengthSeq2) * 100));
			out.println(MessageFormat.format(Messages.getString("Bim.msgBim") //$NON-NLS-1$
				, path.getScore()));
			out.println(MessageFormat.format(Messages.getString("Bim.msgExecutionTime") //$NON-NLS-1$
				, path.time()));
			out.println(MessageFormat.format(Messages.getString("Bim.msgqttyMatches") //$NON-NLS-1$
				, path.getQttyMatches(), (qttyMatches / lengthAverage) * 100));
			out.println(MessageFormat.format(Messages.getString("Bim.msgqttyMismatches") //$NON-NLS-1$
				, path.getQttyMismatches(), (qttyMismatches / lengthAverage) * 100));
			out.println(MessageFormat.format(Messages.getString("Bim.msgqttyGaps") //$NON-NLS-1$
				, qttyGaps, (qttyGaps / lengthAverage) * 100));
			out.println(MessageFormat.format(Messages.getString("Bim.msgqttyInversions") //$NON-NLS-1$
				, path.getQttyExtendedArcs()));

			List<ArcExtended> arcsExtended = path.getArcsExtended();
			double totLengthExtended = 0;
			double lengthIExtended, lengthJExtended, lengthExtendedAverage;
			double qttyMatchesExtended, qttyMismatchesExtended, qttyGapsExtended;
			OptimumPath pathEgExtender, pathDirectExtendedRange;
			int weightExtended, totWeightExtended = 0;
			int scoreDirect, totScoreDirect = 0, matchesDirect, mismatchesDirect, gapsDirect;

			int count = 0;
			OptimumPathFactory pathSimpleFactory = new OptimumPathSimpleFactory();
			for (ArcExtended arc : arcsExtended)
			{
				count++;
				lengthIExtended = arc.getEndVertex().getRow() - arc.getBeginVertex().getRow();
				lengthJExtended = arc.getEndVertex().getCol() - arc.getBeginVertex().getCol();
				lengthExtendedAverage = (lengthIExtended + lengthJExtended) / 2;
				weightExtended = arc.getWeight();
				pathDirectExtendedRange = arc.getEditGraph().getOptimumPath(arc.getRange(), false, pathSimpleFactory);
				scoreDirect = pathDirectExtendedRange.getScore();
				matchesDirect = pathDirectExtendedRange.getQttyMatches();
				mismatchesDirect = pathDirectExtendedRange.getQttyMismatches();
				gapsDirect = pathDirectExtendedRange.getQttyHorizontalArcs()
					+ pathDirectExtendedRange.getQttyVerticalArcs();
				if (arc instanceof ArcExtendedOverEGExtender)
				{
					pathEgExtender = ((ArcExtendedOverEGExtender) arc).getPathEGExtender();
					qttyMatchesExtended = pathEgExtender.getQttyMatches();
					qttyMismatchesExtended = pathEgExtender.getQttyMismatches();
					qttyGapsExtended = pathEgExtender.getQttyHorizontalArcs() + pathEgExtender.getQttyVerticalArcs();
					out.println(MessageFormat.format(
						Messages.getString("Bim.msgInversion") //$NON-NLS-1$
						, arc.getBeginVertex().getRow() + 1, arc.getBeginVertex().getCol() + 1, arc.getEndVertex().getRow(),
						arc.getEndVertex().getCol(), lengthIExtended, (lengthIExtended * 100) / pathILength,
						lengthJExtended, (lengthJExtended * 100) / pathJLength, qttyMatchesExtended,
						(qttyMatchesExtended * 100) / lengthExtendedAverage, qttyMismatchesExtended,
						(qttyMismatchesExtended * 100) / lengthExtendedAverage, qttyGapsExtended,
						(qttyGapsExtended * 100) / lengthExtendedAverage, weightExtended, count, scoreDirect,
						matchesDirect, (matchesDirect * 100) / lengthExtendedAverage, mismatchesDirect,
						(mismatchesDirect * 100) / lengthExtendedAverage, gapsDirect, (gapsDirect * 100)
							/ lengthExtendedAverage));
				}
				else
				{
					out.println(MessageFormat.format(
						Messages.getString("Bim.msgInversion1") //$NON-NLS-1$
						, arc.getBeginVertex().getRow() + 1, arc.getBeginVertex().getCol() + 1, arc.getEndVertex().getRow(),
						arc.getEndVertex().getCol(), lengthIExtended, (lengthIExtended * 100) / pathILength,
						lengthJExtended, (lengthJExtended * 100) / pathJLength, weightExtended, count, scoreDirect,
						matchesDirect, (matchesDirect * 100) / lengthExtendedAverage, mismatchesDirect,
						(mismatchesDirect * 100) / lengthExtendedAverage, gapsDirect, (gapsDirect * 100)
							/ lengthExtendedAverage));
				}
				totLengthExtended += lengthIExtended;
				totWeightExtended += weightExtended;
				totScoreDirect += scoreDirect;
			}

			out.println(MessageFormat.format(Messages.getString("Bim.msgTotInversion") //$NON-NLS-1$
				, totLengthExtended, (totLengthExtended * 100 / pathILength), totWeightExtended, totScoreDirect));

		}
		catch (EGInvalidVertexesOfExtensionException e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	private static char getLetterI(EditGraph eg, int i)
	{
		if (eg instanceof ArcFactorySimpleSequences)
		{
			return toLowerCase(((ArcFactorySimpleSequences) eg).getSeq1().getLetter(i));
		}
		else
		{
			return (((i % 10) == 0) ? FRAGMENTS_SEPARATOR : FRAGMENTS_LETTER);
		}
	}

	private static char getLetterJ(EditGraph eg, int j)
	{
		if (eg instanceof ArcFactorySimpleSequences)
		{
			return toLowerCase(((ArcFactorySimpleSequences) eg).getSeq2().getLetter(j));
		}
		else
		{
			return (((j % 10) == 0) ? FRAGMENTS_SEPARATOR : FRAGMENTS_LETTER);
		}
	}

	private static char getLetterIExtended(EditGraph eg, int i)
	{
		if (eg instanceof ArcFactorySimpleSequences)
		{
			return toUpperCase(((ArcFactorySimpleSequences) eg).getSeq1().getLetter(i));
		}
		else
		{
			return (((i % 10) == 0) ? FRAGMENTS_SEPARATOR_EXTENDED : FRAGMENTS_LETTER_EXTENDED);
		}
	}

	private static char getLetterJExtended(EditGraph eg, int j)
	{
		if (eg instanceof ArcFactorySimpleSequences)
		{
			return toLowerCase(((ArcFactorySimpleSequences) eg).getSeq2().getLetter(j));
		}
		else
		{
			return (((j % 10) == 0) ? FRAGMENTS_SEPARATOR_EXTENDED : FRAGMENTS_LETTER_EXTENDED);
		}
	}

	private static char toUpperCase(byte letter)
	{
		char[] cs = {(char) letter};
		return (new String(cs)).toUpperCase().charAt(0);
	}

	private static char toLowerCase(byte letter)
	{
		char[] cs = {(char) letter};
		return (new String(cs)).toLowerCase().charAt(0);
	}
}

/*
 * Created on 04/10/2004
 */
package sequences.ui;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBim;
import sequences.bim.PathBimDummyFactory;
import sequences.bim.n3.PathBimN3Factory;
import sequences.bim.n3lgn.PathBimN3lgnFactory;
import sequences.bim.n4.PathBimN4Factory;
import sequences.common.ComplementReverseSequence;
import sequences.common.FileFastaSequence;
import sequences.common.FragmentsScores;
import sequences.common.MatchesWeight;
import sequences.common.Sequence;
import sequences.editgraph.EGSparseWithDiagonals;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.OptimumPathSimpleFactory;
import sequences.editgraph.arcs.factories.ArcDiagonalFactorySequences;
import sequences.editgraph.exception.ExceptionGeneralEG;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidArc;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;

/**
 * @author Augusto
 */
public class Bim
{

	public static void main(String[] args)
	{
		LongOpt[] longOpts = new LongOpt[17];
		String method = null;
		int inversion = 2, threshold = 45;
		String fileName = null;
		String sFileName = null, tFileName = null;
		boolean statistics = false, local = false;
		boolean alignment = false, blast = false;
		String[] methods = {"n3l", "n4", "sn4", "n3", "dummy"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		String wFileName = null;
		int match = 1, mismatch = -1, gap = 0;
		int maxCols = 60;

		FragmentsScores fragmentsScores = null;
		Sequence seq1 = null, seq2 = null;
		MatchesWeight matchesWeight = null;

		int c;
		String arg;
		longOpts[0] = new LongOpt("method", LongOpt.REQUIRED_ARGUMENT, null, 'm'); //$NON-NLS-1$
		longOpts[1] = new LongOpt("inversion", LongOpt.REQUIRED_ARGUMENT, null, 'i'); //$NON-NLS-1$
		longOpts[2] = new LongOpt("threshold", LongOpt.REQUIRED_ARGUMENT, null, 't'); //$NON-NLS-1$
		longOpts[3] = new LongOpt("file", LongOpt.REQUIRED_ARGUMENT, null, 'f'); //$NON-NLS-1$
		longOpts[4] = new LongOpt("statistics", LongOpt.NO_ARGUMENT, null, 's'); //$NON-NLS-1$
		// longOpts[5] = new LongOpt("matrixBim", LongOpt.NO_ARGUMENT, null,
		// 'M'); //$NON-NLS-1$
		// longOpts[6] = new LongOpt("length", LongOpt.NO_ARGUMENT, null, 'L');
		// //$NON-NLS-1$
		longOpts[7] = new LongOpt("alignment", LongOpt.NO_ARGUMENT, null, 'a'); //$NON-NLS-1$
		longOpts[8] = new LongOpt("blast", LongOpt.OPTIONAL_ARGUMENT, null, 'b'); //$NON-NLS-1$
		longOpts[9] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'); //$NON-NLS-1$
		longOpts[10] = new LongOpt("sequence1", LongOpt.REQUIRED_ARGUMENT, null, 'x'); //$NON-NLS-1$
		longOpts[11] = new LongOpt("sequence2", LongOpt.REQUIRED_ARGUMENT, null, 'y'); //$NON-NLS-1$
		longOpts[12] = new LongOpt("weights", LongOpt.REQUIRED_ARGUMENT, null, 'w'); //$NON-NLS-1$
		longOpts[13] = new LongOpt("local", LongOpt.NO_ARGUMENT, null, 'l'); //$NON-NLS-1$
		longOpts[14] = new LongOpt("match", LongOpt.REQUIRED_ARGUMENT, null, 'r'); //$NON-NLS-1$
		longOpts[15] = new LongOpt("mismatch", LongOpt.REQUIRED_ARGUMENT, null, 'q'); //$NON-NLS-1$
		longOpts[16] = new LongOpt("gap", LongOpt.REQUIRED_ARGUMENT, null, 'g'); //$NON-NLS-1$
		Getopt g = new Getopt("Bim", args, "m:i:t:f:slabhx:y:w:q:r:g:", longOpts); //$NON-NLS-1$ //$NON-NLS-2$
		while ((c = g.getopt()) != -1)
		{
			switch (c)
			{
			case 'm':
				method = g.getOptarg();
				break;
			case 'i':
				inversion = Integer.parseInt(g.getOptarg());
				break;
			case 'q':
				mismatch = Integer.parseInt(g.getOptarg());
				break;
			case 'r':
				match = Integer.parseInt(g.getOptarg());
				break;
			case 'g':
				gap = Integer.parseInt(g.getOptarg());
				break;
			case 't':
				threshold = Integer.parseInt(g.getOptarg());
				break;
			case 'f':
				fileName = g.getOptarg();
				try
				{
					fragmentsScores = new FragmentsScores(fileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			case 's':
				statistics = true;
				break;
			case 'b':
				blast = true;
				String aux = g.getOptarg();
				if (aux != null)
				{
					maxCols = Integer.parseInt(aux);
				}
				break;
			case 'l':
				local = true;
				break;
			case 'a':
				alignment = true;
				break;
			case 'h':
				System.out.println(Messages.getString("Bim.help")); //$NON-NLS-1$
				System.exit(0);
				break;
			case 'x':
				sFileName = g.getOptarg();
				try
				{
					seq1 = createSequence(sFileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {sFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {sFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			case 'y':
				tFileName = g.getOptarg();
				try
				{
					seq2 = createSequence(tFileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {tFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {tFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			case 'w':
				wFileName = g.getOptarg();
				try
				{
					matchesWeight = new MatchesWeight(wFileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {wFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {wFileName};
					System.out.println(MessageFormat.format(Messages.getString("Bim.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			}
		}

		OptimumPathMethod pathBimFactory = null;
		OptimumPathMethod pathSimpleFactory = new OptimumPathSimpleFactory();

		if (method.equals("n4")) //$NON-NLS-1$
		{
			pathBimFactory = new PathBimN4Factory();
		}
		else
		{
			// if (method.equals("sn4")) //$NON-NLS-1$
			// {
			// bimAlg = new BimSN4(eg, egInverted, inversion, local);
			// }
			// else
			// {
			if (method.equals("n3l")) //$NON-NLS-1$
			{
				pathBimFactory = new PathBimN3lgnFactory();
			}
			else
			{
				if (method.equals("n3")) //$NON-NLS-1$
				{
					pathBimFactory = new PathBimN3Factory();
				}
				else
				{
					if (method.equals("dummy")) //$NON-NLS-1$
					{
						pathBimFactory = new PathBimDummyFactory();
					}
					else
					{
						// if (method.equals("n3gap1")) //$NON-NLS-1$
						// {
						// bimAlg = new BimN3Gap1(eg, egInverted, inversion, local);
						// }
						// else
						// {
						System.out.println(Messages.getString("Bim.errInvalidMethod")); //$NON-NLS-1$
						System.exit(0);
					}
				}
			}
		}
		// }
		// }

		EditGraph eg = null, egInverted = null;

		try
		{
			if (fragmentsScores != null)
			{
				egInverted = new EGSparseWithDiagonals(fragmentsScores.getInvertedScores(), threshold, match, mismatch,
					gap, fragmentsScores.getNumFragsSeq1() + 1, fragmentsScores.getNumFragsSeq2() + 1, matchesWeight,
					true, pathSimpleFactory, null);
				eg = new EGSparseWithDiagonals(fragmentsScores.getDirectScores(), threshold, match, mismatch, gap,
					fragmentsScores.getNumFragsSeq1() + 1, fragmentsScores.getNumFragsSeq2() + 1, matchesWeight, false,
					pathBimFactory, new ExtenderUsingEGInvertedRows(egInverted, inversion));
			}
			else
			{
				if (seq1 != null && seq2 != null)
				{
					egInverted = new ArcDiagonalFactorySequences(new ComplementReverseSequence(seq1), seq2, match, mismatch,
						gap, pathSimpleFactory, null);
					eg = new ArcDiagonalFactorySequences(seq1, seq2, match, mismatch, gap, pathBimFactory,
						new ExtenderUsingEGInvertedRows(egInverted, inversion));
				}
				else
				{
					System.out.println(Messages.getString("Bim.errFileNameEmpty")); //$NON-NLS-1$
					System.exit(0);
				}
			}

		}
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		PathBim path;
		try
		{
			path = (PathBim) eg.getOptimumPath(local);
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}

		if (statistics)
		{
			System.out.print("args:"); //$NON-NLS-1$
			for (int i = 0; i < args.length; i++)
			{
				System.out.print(args[i] + " "); //$NON-NLS-1$
			}
			System.out.println();
			System.out.println(MessageFormat.format(Messages.getString("Bim.msgMethod"), pathBimFactory.getName())); //$NON-NLS-1$

			int length1 = 0, length2 = 0;
			if (fragmentsScores != null)
			{
				length1 = fragmentsScores.getNumFragsSeq1();
				length2 = fragmentsScores.getNumFragsSeq2();
				System.out.println(MessageFormat.format(Messages.getString("Bim.msgCompmatFile"), fileName)); //$NON-NLS-1$
				System.out.println(MessageFormat.format(
					Messages.getString("Bim.msgFragmentsSeq1"), fragmentsScores.getNameSeq1(), length1)); //$NON-NLS-1$
				System.out.println(MessageFormat.format(
					Messages.getString("Bim.msgFragmentsSeq2"), fragmentsScores.getNameSeq2(), length2)); //$NON-NLS-1$

				System.out.println(MessageFormat.format(Messages.getString("Bim.msgThreshold"), threshold)); //$NON-NLS-1$

				if (matchesWeight != null)
				{
					System.out.println(MessageFormat.format(Messages.getString("Bim.msgWeights"), wFileName)); //$NON-NLS-1$
				}
				else
				{
					System.out.println(MessageFormat.format(Messages.getString("Bim.msgMatchWeight"), match)); //$NON-NLS-1$
				}
			}
			else
			{
				if (seq1 != null && seq2 != null)
				{
					length1 = seq1.getLength();
					length2 = seq2.getLength();
					System.out.println(MessageFormat.format(
						Messages.getString("Bim.msgSeq1"), seq1.getName(), length1, sFileName)); //$NON-NLS-1$
					System.out.println(MessageFormat.format(
						Messages.getString("Bim.msgSeq2"), seq2.getName(), length2, tFileName)); //$NON-NLS-1$

					System.out.println(MessageFormat.format(Messages.getString("Bim.msgMatchWeight"), match)); //$NON-NLS-1$
				}
			}

			System.out.println(MessageFormat.format(Messages.getString("Bim.msgMismatchWeight"), mismatch)); //$NON-NLS-1$
			System.out.println(MessageFormat.format(Messages.getString("Bim.msgGapWeight"), gap)); //$NON-NLS-1$
			System.out.println(MessageFormat.format(Messages.getString("Bim.msgInversionPenalty"), inversion)); //$NON-NLS-1$

			PrinterPath.printStatistics(path, System.out);
		}
		if (blast)
		{
			System.out.println(Messages.getString("Bim.msgBlast")); //$NON-NLS-1$
			PrinterPath.printBlast(path, System.out, maxCols);
		}
		if (alignment)
		{
			System.out.println(Messages.getString("Bim.msgMatches")); //$NON-NLS-1$
			PrinterPath.printMatches(path, System.out);
		}
		System.exit(0);
	}

	private static Sequence createSequence(String fileName) throws FileNotFoundException, IOException
	{
		return (new FileFastaSequence(fileName)).getSequence();
	}

}
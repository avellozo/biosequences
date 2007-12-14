/*
 * Created on 04/10/2004
 */
package sequences.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Random;

import sequences.MaxPathJeanette;
import sequences.MaxPathNaive;
import sequences.common.FileFastaSequence;
import sequences.common.Sequence;
import sequences.editgraph.EditGraphOld;
import sequences.editgraph.WeighterArcsSimpleSequences;
import sequences.editgraph.WeighterArcsRandom;
import sequences.util.ByteArrayOutputStream;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * @author Augusto
 */
public class MaxPath
{

	public static void main(String[] args)
	{
		LongOpt[] longOpts = new LongOpt[12];
		String method = null;
		int match = 1, mismatch = 0, gap = 0;
		long seed = 0;
		boolean seedRandom = false;
		String sFileName = null;
		String tFileName = null;
		boolean best = false, matrix = false, time = false, help = false, alignRandom = false;

		int c;
		String arg;
		longOpts[0] = new LongOpt("method", LongOpt.REQUIRED_ARGUMENT, null, //$NON-NLS-1$
			'm');
		longOpts[1] = new LongOpt("match", LongOpt.REQUIRED_ARGUMENT, null, //$NON-NLS-1$
			'r');
		longOpts[2] = new LongOpt("mismatch", LongOpt.REQUIRED_ARGUMENT, null, //$NON-NLS-1$
			'q');
		longOpts[3] = new LongOpt(
			"gap-extension", LongOpt.REQUIRED_ARGUMENT, null, //$NON-NLS-1$
			'E');
		longOpts[4] = new LongOpt(
			"sequence1", LongOpt.REQUIRED_ARGUMENT, null, 's'); //$NON-NLS-1$
		longOpts[5] = new LongOpt(
			"sequence2", LongOpt.REQUIRED_ARGUMENT, null, 't'); //$NON-NLS-1$
		longOpts[6] = new LongOpt("best", LongOpt.NO_ARGUMENT, null, 'b'); //$NON-NLS-1$
		longOpts[7] = new LongOpt("matrixBim", LongOpt.NO_ARGUMENT, null, 'M'); //$NON-NLS-1$
		longOpts[8] = new LongOpt("time", LongOpt.NO_ARGUMENT, null, 'T'); //$NON-NLS-1$
		longOpts[9] = new LongOpt("random", LongOpt.NO_ARGUMENT, null, 'R'); //$NON-NLS-1$
		longOpts[10] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'); //$NON-NLS-1$
		longOpts[11] = new LongOpt("seed", LongOpt.REQUIRED_ARGUMENT, null, 'S'); //$NON-NLS-1$
		Getopt g = new Getopt("MaxPath", args, "m:r:q:E:s:t:S:bMTRh", longOpts); //$NON-NLS-1$ //$NON-NLS-2$
		while ((c = g.getopt()) != -1)
		{
			switch (c)
			{
			case 'm':
				method = g.getOptarg();
				break;
			case 'S':
				seed = Long.parseLong(g.getOptarg());
				seedRandom = true;
				break;
			case 'r':
				match = Integer.parseInt(g.getOptarg());
				break;
			case 'q':
				mismatch = Integer.parseInt(g.getOptarg());
				break;
			case 'E':
				gap = Integer.parseInt(g.getOptarg());
				break;
			case 's':
				sFileName = g.getOptarg();
				break;
			case 't':
				tFileName = g.getOptarg();
				break;
			case 'b':
				best = true;
				break;
			case 'T':
				time = true;
				break;
			case 'M':
				matrix = true;
				break;
			case 'R':
				alignRandom = true;
				break;
			case 'h':
				help = true;
				break;
			}
		}

		if (help)
		{
			System.out.println(Messages.getString("MaxPath.help")); //$NON-NLS-1$
			System.exit(0);
		}

		Sequence seq1, seq2;
		seq1 = createSequence(sFileName);
		seq2 = createSequence(tFileName);
		EditGraphOld editGraph = null;
		if ((seq1 != null) && (seq2 != null))
		{
			if (seedRandom)
			{
				editGraph = new WeighterArcsRandom(seq1.getLength() + 1, seq2
					.getLength() + 1, new Random(seed), false, 128);
				Object[] a = {new Long(seed)};
				System.out.println(MessageFormat.format(Messages
					.getString("MaxPath.msgSeed"), a)); //$NON-NLS-1$
			}
			else
			{
				if (alignRandom)
				{
					Random random = new Random();
					match = random.nextInt(128);
					mismatch = -random.nextInt(128);
					gap = -random.nextInt(128);
				}
				editGraph = new WeighterArcsSimpleSequences(seq1, seq2, match, mismatch,
					gap, false);
				Object[] a = {new Integer(match), new Integer(mismatch),
					new Integer(gap)};
				System.out.println(MessageFormat.format(Messages
					.getString("MaxPath.msgAlignmentParameters"), a)); //$NON-NLS-1$
			}
		}

		if (method == null
			|| ((!method.equals("n3")) && (!method.equals("n2l")) && (!method.equals("compare")))) //$NON-NLS-1$ //$NON-NLS-2$
		{
			System.out.println(Messages.getString("MaxPath.errInvalidMethod")); //$NON-NLS-1$
		}
		else
		{
			if (editGraph != null)
			{
				sequences.MaxPath maxPath = null;
				sequences.MaxPath maxPath1 = null;
				if (method.equals("n3")) //$NON-NLS-1$
				{
					maxPath = new MaxPathNaive(editGraph);
				}
				if (method.equals("n2l")) //$NON-NLS-1$
				{
					maxPath = new MaxPathJeanette(editGraph);
				}
				if (method.equals("compare")) //$NON-NLS-1$
				{
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					ByteArrayOutputStream b1 = new ByteArrayOutputStream();
					maxPath = new MaxPathNaive(editGraph, new PrintStream(b));
					maxPath1 = new MaxPathJeanette(editGraph, new PrintStream(b1));
					if (b.equals(b1))
					{
						System.out.println(Messages
							.getString("MaxPath.msgMatrixEquals"));//$NON-NLS-1$)
					}
					else
					{
						System.out.println(Messages
							.getString("MaxPath.msgMatrixNotEquals"));//$NON-NLS-1$)
					}
				}
				if (maxPath != null)
				{
					if (time)
					{
						if (maxPath1 != null) //$NON-NLS-1$
						{
							Object[] a = {new Long(maxPath.getTime()),
								new Long(maxPath1.getTime())};
							System.out.println(MessageFormat.format(Messages
								.getString("MaxPath.msgExecutionTimeCompare"), a)); //$NON-NLS-1$
						}
						else
						{
							Object[] a = {new Long(maxPath.getTime())};
							System.out.println(MessageFormat.format(Messages
								.getString("MaxPath.msgExecutionTime"), a)); //$NON-NLS-1$

						}
					}
					if (best)
					{
						Object[] a = {new Integer(maxPath.getMaxValue())};
						System.out.println(MessageFormat.format(Messages
							.getString("MaxPath.msgBest") //$NON-NLS-1$
							, a));
					}
					if (matrix)
					{
						maxPath.getMatrix().print(System.out);
					}
				}
			}
		}
		System.exit(0);
	}

	private static Sequence createSequence(String fileName)
	{
		Sequence seq = null;
		if (fileName == null || fileName.trim().length() == 0)
		{
			System.out.println(Messages.getString("MaxPath.errFileNameEmpty")); //$NON-NLS-1$
		}
		else
		{
			try
			{
				seq = (new FileFastaSequence(fileName)).getSequence();
			}
			catch (FileNotFoundException e)
			{
				Object[] a = {fileName};
				System.out.println(MessageFormat.format(Messages
					.getString("MaxPath.errFileNotFound"), a)); //$NON-NLS-1$
			}
			catch (IOException e)
			{
				Object[] a = {fileName};
				System.out.println(MessageFormat.format(Messages
					.getString("MaxPath.errFileIO"), a)); //$NON-NLS-1$
			}
		}
		return seq;
	}
}
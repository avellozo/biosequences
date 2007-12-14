/*
 * Created on 20/06/2006
 */
package tools.generateSequences;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Random;

import sequences.bim.ExtenderUsingEGInvertedRows;
import sequences.bim.PathBimDummyFactory;
import sequences.common.ComplementReverseSequence;
import sequences.common.SequenceByteArray;
import sequences.editgraph.Arc;
import sequences.editgraph.ArcExtendedOverEGExtender;
import sequences.editgraph.WeighterArcsSimpleSequences;
import sequences.editgraph.ExceptionGeneralEG;
import sequences.editgraph.ExceptionInvalidArc;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.EGInvalidVertexesOfExtensionException;
import sequences.editgraph.ExceptionInvalidVertex;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathDummyFactory;
import sequences.editgraph.Vertex;
import sequences.editgraph.EditGraphSegment;
import sequences.ui.PrinterPath;

/**
 * @author Augusto F. Vellozo
 */
public class GenerateSequences
{
	static Random	random	= new Random();
	static char[]	bases	= new char[] {'A', 'C', 'T', 'G'};

	public static void main(String[] args)
	{
		String f1 = "", f2 = "", fBim="";
		int g = 0, q = 0, n = 0, inv = 0, l = 0;
		if (args.length == 0)
		{
			args = new String[1];
			args[0] = "--help";
		}

		LongOpt[] longOpts = new LongOpt[8];
		longOpts[0] = new LongOpt("f1", LongOpt.REQUIRED_ARGUMENT, null, 's'); //$NON-NLS-1$
		longOpts[1] = new LongOpt("f2", LongOpt.REQUIRED_ARGUMENT, null, 't');//$NON-NLS-1$
		longOpts[2] = new LongOpt("gaps", LongOpt.REQUIRED_ARGUMENT, null, 'g');//$NON-NLS-1$
		longOpts[3] = new LongOpt("mismatches", LongOpt.REQUIRED_ARGUMENT, null, 'q'); //$NON-NLS-1$
		longOpts[4] = new LongOpt("inversions", LongOpt.REQUIRED_ARGUMENT, null, 'i'); //$NON-NLS-1$
		longOpts[5] = new LongOpt("min_inversion", LongOpt.REQUIRED_ARGUMENT, null, 'l'); //$NON-NLS-1$
		longOpts[6] = new LongOpt("size", LongOpt.REQUIRED_ARGUMENT, null, 'n'); //$NON-NLS-1$
		longOpts[7] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');//$NON-NLS-1$

		Getopt o = new Getopt("GenerateSequences", args, "s:t:g:q:n:i:l:h", longOpts); //$NON-NLS-1$ //$NON-NLS-2$
		int c;
		while ((c = o.getopt()) != -1)
		{
			switch (c)
			{
			case 'q':
				q = Integer.parseInt(o.getOptarg());
				break;
			case 'g':
				g = Integer.parseInt(o.getOptarg());
				break;
			case 'n':
				n = Integer.parseInt(o.getOptarg());
				break;
			case 'i':
				inv = Integer.parseInt(o.getOptarg());
				break;
			case 'l':
				l = Integer.parseInt(o.getOptarg());
				break;
			case 's':
				f1 = o.getOptarg();
				break;
			case 't':
				f2 = o.getOptarg();
				break;
			case 'h':
				System.out.println(Messages.getString("GenerateSequences.help")); //$NON-NLS-1$
				System.exit(0);
				break;
			}
		}

		// int na = n * 100 / (100 - (g / 2)); // número de letras da sequencia
		// original
		// int ga = na * g / 100; // número total de gaps a serem gerados
		// int qa = na * q / 100; // número total de mismatches a serem gerados
		int na = n + (g / 2); // número de letras da sequencia original
		int ga = g; // número total de gaps a serem gerados
		int qa = q; // número total de mismatches a serem gerados

		// Cria a sequencia inicial str
		char[] str = new char[na];
		for (int i = 0; i < na; i++)
		{
			str[i] = bases[random.nextInt(4)];
		}

		// cria um array de flags para evitar repetição de gaps e mismatches
		char[] flags = new char[na];
		// cria os pontos de gaps
		int aux;
		int[] gaps = new int[ga];
		for (int i = 0; i < (ga); i++)
		{
			aux = random.nextInt(2 * na);
			while (flags[aux % na] != 0)
			{
				aux = random.nextInt(2 * na);
			}
			if (aux >= na)
			{
				flags[aux % na] = 'g';
			}
			else
			{
				flags[aux] = 'G';
			}
			gaps[i] = aux;
		}
		Arrays.sort(gaps);

		// cria os pontos de mismatches
		int[] mismatches = new int[qa];
		for (int i = 0; i < (qa); i++)
		{
			aux = random.nextInt(2 * na);
			while (flags[aux % na] != 0)
			{
				aux = random.nextInt(2 * na);
			}
			if (aux >= na)
			{
				flags[aux % na] = 'm';
			}
			else
			{
				flags[aux] = 'M';
			}
			mismatches[i] = aux;
		}
		Arrays.sort(mismatches);

		// cria a sequencia 1
		int gi = 0, qi = 0;
		char ch;
		StringBuffer s1 = new StringBuffer(n);
		for (int i = 0; i < na; i++)
		{
			if ((gi < ga) && (gaps[gi] == i))
			{
				gi++;
			}
			else
			{
				ch = str[i];
				if ((qi < qa) && (mismatches[qi] == i))
				{
					ch = change(ch);
					qi++;
				}
				s1.append(ch);
			}
		}

		// cria a sequencia 2
		StringBuffer s2 = new StringBuffer((2 * na) - ga - s1.length());
		for (int i = 0; i < na; i++)
		{
			if (gi < ga && gaps[gi] == (na + i))
			{
				gi++;
			}
			else
			{
				ch = str[i];
				if (qi < qa && mismatches[qi] == na + i)
				{
					ch = change(ch);
					qi++;
				}
				s2.append(ch);
			}
		}

		// cria os pontos de inversões
		if (l == 0)
		{
			l++;
		}
		int tams1 = s1.length() - (l * inv) + 1;
		if ((tams1) <= 0)
		{
			System.out.println(Messages.getString("GenerateSequences.errLengthInversions"));
			System.exit(0);
		}
		int[] inversions = new int[2 * inv];
		for (int i = 0; i < (2 * inv); i++)
		{
			inversions[i] = random.nextInt(tams1);
		}
		Arrays.sort(inversions);

		int i1, i2;
		for (int i = 0; i < inv; i++)
		{
			char c1, c2;
			i1 = (inversions[2 * i] = inversions[2 * i] + (i * l));
			i2 = (inversions[2 * i + 1] = inversions[2 * i + 1] + ((i + 1) * l) - 1);
			while (i1 <= i2)
			{
				c1 = complement(s1.charAt(i1));
				c2 = complement(s1.charAt(i2));
				s1.setCharAt(i1, c2);
				s1.setCharAt(i2, c1);
				i1++;
				i2--;
			}
		}

		// escreve nos arquivos as sequencias geradas
		StringBuffer out = null;
		try
		{
			// parâmetros de entrada
			out = new StringBuffer(" n=" + n + " q=" + q + " g=" + g + " i=" + inv + " l=" + l + " < ");
			if (f1.length() == 0)
			{
				f1 = MessageFormat.format(Messages.getString("GenerateSequences.file1Name"),n,q,g,inv,l);
			}

			if (f2.length() == 0)
			{
				f2 = MessageFormat.format(Messages.getString("GenerateSequences.file2Name"),n,q,g,inv,l);
			}

			fBim = MessageFormat.format(Messages.getString("GenerateSequences.fileBimName"),n,q,g,inv,l);
			
			int tamInv = inversions.length;
			BufferedWriter file1 = new BufferedWriter(new FileWriter(f1));
			BufferedWriter file2 = new BufferedWriter(new FileWriter(f2));

			for (int i = 0; i < tamInv; i = i + 2)
			{
				out.append((inversions[i] + 1) + "-" + (inversions[i + 1] + 1) + " ");
			}
			out.append('>');

			// arquivo 1
			file1.write("> Arquivo par:" + f2 + out);
			for (int i = 0; i < s1.length(); i++)
			{
				if ((i % 70) == 0)
				{
					file1.newLine();
				}
				file1.append(s1.charAt(i));
			}
			file1.flush();
			file1.close();

			// arquivo 2
			file2.write("> Arquivo par:" + f1 + out);
			for (int i = 0; i < s2.length(); i++)
			{
				if ((i % 70) == 0)
				{
					file2.newLine();
				}
				file2.append(s2.charAt(i));
			}
			file2.flush();
			file2.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println(MessageFormat.format(Messages.getString("GenerateSequences.filesGenerated"), f1, f2, out));
		// Print path
		try
		{
			SequenceByteArray seq1 = new SequenceByteArray(f1, s1.toString().getBytes());
			SequenceByteArray seq2 = new SequenceByteArray(f2, s2.toString().getBytes());
			EditGraph eg = null, egInverted = null;
			PathBimDummyFactory pathBimDummyFactory = new PathBimDummyFactory();
			OptimumPathDummyFactory optimumPathDummyFactory = new OptimumPathDummyFactory();
			egInverted = new WeighterArcsSimpleSequences(new ComplementReverseSequence(seq1), seq2, 1, -1, -2,
				optimumPathDummyFactory, null);
			eg = new WeighterArcsSimpleSequences(seq1, seq2, 1, -1, -2, pathBimDummyFactory, new ExtenderUsingEGInvertedRows(
				egInverted, 2));
			OptimumPath path = eg.getOptimumPath(false);
			OptimumPath pathInverted;
			int inversionIndex = 0;
			int vj; // index j of vertex v
			int vi; // index i of vertex v
			Vertex v1; // vertex end of an arc which begins with v
			Arc arc;
			int iExtended;
			Vertex v = eg.getVertex(0, 0); // end vertex of the last arc
			for (int i = 0; i < na; i++)
			{
				if ((inversionIndex < inversions.length) && ((v.getRow()) == inversions[inversionIndex])
					&& (flags[i] != 'G'))
				{
					ArcExtendedOverEGExtender arcExtended;
					// find the last vertex
					vj = v.getCol();
					vi = v.getRow();
					for (iExtended = i; (vi < (inversions[inversionIndex + 1] + 1)) && (iExtended < na); iExtended++)
					{
						if (flags[iExtended] == 'g') // gap on the second
						// sequence
						{
							vi++;
						}
						else if (flags[iExtended] == 'G')// gap on the first
						// sequence
						{
							vj++;
						}
						else
						// match or mismatch
						{
							vj++;
							vi++;
						}
					}
					v1 = eg.getVertex(inversions[inversionIndex + 1] + 1, vj);
					arcExtended = (ArcExtendedOverEGExtender) eg.getExtendedArc(new EditGraphSegment(v, v1));// new
					// ArcExtendedOverEGExtender(v,
					// v1);
					pathInverted = arcExtended.getPathEGExtender();
					vj = v.getCol();
					vi = v.getRow();
					for (; (vi < (inversions[inversionIndex + 1] + 1)) && (i < na); i++)
					{
						if (flags[i] == 'g') // gap on the second sequence
						{
							vi++;
							pathInverted.add(egInverted.getVerticalArc(arcExtended.transformVertex(eg.getVertex(
								v1.getRow() - vi + v.getRow() + 1, vj))));
						}
						else if (flags[i] == 'G')// gap on the first sequence
						{
							vj++;
							pathInverted.add(egInverted.getHorizontalArc(arcExtended.transformVertex(eg.getVertex(
								v1.getRow() - vi + v.getRow() + 1, vj))));
						}
						else
						// match or mismatch
						{
							vj++;
							vi++;
							pathInverted.add(egInverted.getDiagonalArc(arcExtended.transformVertex(eg.getVertex(
								v1.getRow() - vi + v.getRow() + 1, vj))));
						}
					}
					i--;
					path.add(arcExtended);
					inversionIndex += 2;
					v = v1;
				}
				else if (flags[i] == 'g') // gap on the second sequence
				{
					path.add(eg.getVerticalArc(v = eg.getVertex(v.getRow() + 1, v.getCol())));
				}
				else if (flags[i] == 'G') // gap on the first sequence
				{
					path.add(eg.getHorizontalArc(v = eg.getVertex(v.getRow(), v.getCol() + 1)));
				}
				else
				// match or mismatch
				{
					path.add(eg.getDiagonalArc(v = eg.getVertex(v.getRow() + 1, v.getCol() + 1)));
				}
			}
			PrintStream ps = new PrintStream(fBim);
			
			PrinterPath.printStatistics(path, ps);
			PrinterPath.printBlast(path, ps, 60);
			PrinterPath.printMatches(path, ps);
		}
		catch (ExceptionInvalidVertex e)
		{
			e.printStackTrace();
		}
		catch (ExceptionInvalidArc e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
		}
		catch (EGInvalidVertexesOfExtensionException e)
		{
			e.printStackTrace();
		}
	}

	public static char change(char c)
	{
		int i = random.nextInt(4);
		char c1 = c;
		while (c1 == c)
		{
			c1 = bases[random.nextInt(4)];
		}
		return c1;
	}

	public static char complement(char c)
	{
		switch (c)
		{
		case 'A':
			return 'T';
		case 'T':
			return 'A';
		case 'C':
			return 'G';
		case 'G':
			return 'C';
		}
		throw new RuntimeException("Invalid char");
	}
}
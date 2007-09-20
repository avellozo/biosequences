/*
 * Created on 07/10/2005
 */
package tools.buildCompmat;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sequences.ui.Utils;

/**
 * @author Augusto F. Vellozo
 */
public class BuildCompmat
{
	public static void main(String[] args) throws Exception
	{
		CharBuffer gbk1 = null, gbk2 = null;
		BufferedReader inBlast = null;
		String compmatFileName = null;
		int i1 = 0, j1 = -1, i2 = 0, j2 = -1;
		boolean invert1 = false, invert2 = false;
		int numFrags1, numFrags2;

		if (args.length == 0)
		{
			args = new String[1];
			args[0] = "--help";
		}

		LongOpt[] longOpts = new LongOpt[11];
		longOpts[0] = new LongOpt("f1", LongOpt.REQUIRED_ARGUMENT, null, 's'); //$NON-NLS-1$
		longOpts[1] = new LongOpt("i1", LongOpt.REQUIRED_ARGUMENT, null, 'i');//$NON-NLS-1$
		longOpts[2] = new LongOpt("j1", LongOpt.REQUIRED_ARGUMENT, null, 'j');//$NON-NLS-1$
		longOpts[3] = new LongOpt("invert1", LongOpt.NO_ARGUMENT, null, 'v'); //$NON-NLS-1$
		longOpts[4] = new LongOpt("f2", LongOpt.REQUIRED_ARGUMENT, null, 't'); //$NON-NLS-1$
		longOpts[5] = new LongOpt("i2", LongOpt.REQUIRED_ARGUMENT, null, 'k');//$NON-NLS-1$
		longOpts[6] = new LongOpt("j2", LongOpt.REQUIRED_ARGUMENT, null, 'l');//$NON-NLS-1$
		longOpts[7] = new LongOpt("invert2", LongOpt.NO_ARGUMENT, null, 'x'); //$NON-NLS-1$
		longOpts[8] = new LongOpt("blast", LongOpt.REQUIRED_ARGUMENT, null, 'b'); //$NON-NLS-1$
		longOpts[9] = new LongOpt("out", LongOpt.REQUIRED_ARGUMENT, null, 'o'); //$NON-NLS-1$
		longOpts[10] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');//$NON-NLS-1$

		Getopt g = new Getopt(
			"BuildCompmat", args, "s:i:j:t:k:l:b:o:vxh", longOpts); //$NON-NLS-1$ //$NON-NLS-2$
		int c;
		while ((c = g.getopt()) != -1)
		{
			switch (c)
			{
			case 'i':
				i1 = Integer.parseInt(g.getOptarg());
				break;
			case 'j':
				j1 = Integer.parseInt(g.getOptarg());
				break;
			case 'k':
				i2 = Integer.parseInt(g.getOptarg());
				break;
			case 'l':
				j2 = Integer.parseInt(g.getOptarg());
				break;
			case 'v':
				invert1 = true;
				break;
			case 'x':
				invert2 = true;
				break;
			case 'h':
				System.out.println(Messages.getString("BuildCompmat.help")); //$NON-NLS-1$
				System.exit(0);
				break;
			case 's':
				String fileName = g.getOptarg();
				try
				{
					gbk1 = Utils.getFileCharBuffer(fileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages
						.getString("BuildCompmat.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages
						.getString("BuildCompmat.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			case 't':
				fileName = g.getOptarg();
				try
				{
					gbk2 = Utils.getFileCharBuffer(fileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages
						.getString("BuildCompmat.errFileNotFound"), a)); //$NON-NLS-1$
				}
				catch (IOException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages
						.getString("BuildCompmat.errFileIO"), a)); //$NON-NLS-1$
				}
				break;
			case 'b':
				fileName = g.getOptarg();
				try
				{
					inBlast = Utils.getFileBufferedReader(fileName);
				}
				catch (FileNotFoundException e)
				{
					Object[] a = {fileName};
					System.out.println(MessageFormat.format(Messages
						.getString("BuildCompmat.errFileNotFound"), a)); //$NON-NLS-1$
				}
				break;
			case 'o':
				compmatFileName = g.getOptarg();
				break;
			}
		}

		if (compmatFileName == null || inBlast == null || gbk1 == null
			|| gbk2 == null)
		{
			System.out.println(Messages.getString("Bim.help")); //$NON-NLS-1$
			System.exit(0);
		}

		Pattern p = Pattern.compile(
			"gene\\s*(complement)?.*?db_xref=\"GI:(.*?)\"", Pattern.DOTALL);

		int i = 0;
		Hashtable hash1 = new Hashtable();
		Matcher m = p.matcher(gbk1);
		while (m.find())
		{
			hash1.put(m.group(2), new GenePosition(m.group(1) != null, i++));
		}

		numFrags1 = i;
		if (i1>=numFrags1)
		{
			i1= numFrags1 - 1;
		}
		if (j1>=numFrags1)
		{
			j1= numFrags1 - 1;
		}
		if (j1 == -1)
		{
			if (i1 == 0)
			{
				j1 = numFrags1 - 1;
			}
			else
			{
				j1 = i1 - 1;
			}
		}

		i = 0;
		Hashtable hash2 = new Hashtable();
		m = p.matcher(gbk2);
		while (m.find())
		{
			hash2.put(m.group(2), new GenePosition(m.group(1) != null, i++));
		}

		numFrags2 = i;
		if (i2>=numFrags2)
		{
			i2= numFrags2 - 1;
		}
		if (j2>=numFrags2)
		{
			j2= numFrags2 - 1;
		}
		if (j2 == -1)
		{
			if (i2 == 0)
			{
				j2 = numFrags2 - 1;
			}
			else
			{
				j2 = i2 - 1;
			}
		}
		
		Iterator it;
		
		it = hash1.values().iterator();
		while (it.hasNext())
		{
			transformaPos((GenePosition) it.next(), i1, j1, numFrags1, invert1);
		}

		it = hash2.values().iterator();
		while (it.hasNext())
		{
			transformaPos((GenePosition) it.next(), i2, j2, numFrags2, invert2);
		}

		String line = inBlast.readLine();
		String[] tokens;
		double evalue;
		GenePosition gene1, gene2;
		TreeMap matches = new TreeMap(new MatchComparator());
		while (line != null)
		{
			tokens = line.split("[\\|\\s]");
			evalue = Double.parseDouble(tokens[18]);
			line = inBlast.readLine();

			gene1 = (GenePosition) hash1.get(tokens[1]);
			gene2 = (GenePosition) hash2.get(tokens[6]);
			if (gene1 != null && gene2 != null && 
					gene1.getPosition() != -1 && gene2.getPosition() != -1)
			{
				Match m1 = new Match(gene1, gene2, evalue);
				Match m2 = (Match) matches.put(m1, m1);
				if (m2 != null)
				{
					if (m1.evalue < m2.evalue)
					{
						matches.put(m2, m2);
					}
				}
			}
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(compmatFileName));
		out.write("# NumFrags:         " + numFrags1);
		out.newLine();
		out.newLine();
		out.write("# NumFrags:         " + numFrags2);
		out.newLine();
		out.newLine();

		Match match;
		it = matches.keySet().iterator();
		int pos1, pos2;
		while (it.hasNext())
		{
			match = (Match) it.next();
			pos1 = match.gene1.position;
			pos2 = match.gene2.position;
				out.write(match.getSignal() + " " + pos1 + " " + pos2 + " "
					+ match.getMatchValue());
				out.newLine();
		}

		out.flush();
		out.close();
	}

	public static void transformaPos(GenePosition genePosition, int i, int j, int numFrags,
			boolean o)
	{
		int pos = genePosition.getPosition();
		int ret = pos - i;
		int maxRet = j - i;
		if (maxRet < 0)
		{
			maxRet = maxRet + numFrags + 1;
		}
		if (ret < 0)
		{
			ret = ret + numFrags + 1;
		}
		ret = (ret > maxRet ? -1 : (!o ? ret : maxRet - ret));
		genePosition.setPosition(ret);
	}
}
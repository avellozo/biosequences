/*
 * Criado em 24/06/2004
 */

package sequences.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Augusto
 * @data 24/06/2004 Esta classe monta duas listas de scores que são lidos de um
 *       arquivo .compmat
 */

public class FragmentsScores
{
	LinkedList<Score>	directScores;
	LinkedList<Score>	invertedScores;
	FragmentsProperties	propSeq1, propSeq2, propGraph;

	public FragmentsScores(String fileName) throws IOException,
			FileNotFoundException
	{
		BufferedReader in;
		File file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				in = new BufferedReader(new InputStreamReader(Thread
					.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName)));
			}
			catch (Exception e)
			{
				throw new FileNotFoundException("Arquivo inexistente:"
					+ fileName);
			}
		}
		else
		{
			in = new BufferedReader(new FileReader(fileName));
		}
		build(in);
	}

	public FragmentsScores(File file) throws FileNotFoundException, IOException
	{

		build(new BufferedReader(new FileReader(file)));
	}

	protected void build(BufferedReader in) throws IOException
	{
		propSeq1 = new FragmentsProperties(in);
		// a próxima linha deve ser uma linha em branco
		String line = propSeq1.getLastLine();
		if (line == null) // não pode ser fim de arquivo
		{
			throw new IOException("Arquivo incompleto");
		}
		if (line.trim().length() != 0)
		{
			throw new IOException("Formato do arquivo incorreto");
		}
		propSeq2 = new FragmentsProperties(in);
		// a próxima linha deve ser uma linha em branco
		line = propSeq2.getLastLine();
		if (line == null) // não pode ser fim de arquivo
		{
			throw new IOException("Arquivo incompleto");
		}
		if (line.trim().length() != 0)
		{
			throw new IOException("Formato do arquivo incorreto");
		}
		propGraph = new FragmentsProperties(in);
		directScores = new LinkedList<Score>();
		invertedScores = new LinkedList<Score>();
		LineValue lineValue;
		line = propGraph.getLastLine();
		while (line != null)
		{
			lineValue = new LineValue(line);
			if (lineValue.isInverted())
			{
				invertedScores.addLast(new Score(lineValue.getIndex1(),
					lineValue.getIndex2(), lineValue.getValueMatch()));
			}
			else
			{
				directScores.addLast(new Score(lineValue.getIndex1(), lineValue
					.getIndex2(), lineValue.getValueMatch()));
			}
			line = in.readLine();
		}
	}

	public String getNameSeq1()
	{
		return propSeq1.getProperty("display_id");
	}

	public String getNameSeq2()
	{
		return propSeq2.getProperty("display_id");
	}

	public int getNumFragsSeq1()
	{
		return propSeq1.getNumFrags();
	}

	public int getNumFragsSeq2()
	{
		return propSeq2.getNumFrags();
	}

	public FragmentsProperties getPropGraph()
	{
		return propGraph;
	}

	public FragmentsProperties getPropSeq1()
	{
		return propSeq1;
	}

	public FragmentsProperties getPropSeq2()
	{
		return propSeq2;
	}

	public List<Score> getDirectScores()
	{
		return this.directScores;
	}

	public List<Score> getInvertedScores()
	{
		return this.invertedScores;
	}
}

class LineValue
{
	boolean	inverted;
	int		index1, index2;
	int		valueMatch;

	LineValue(String line) throws IOException
	{
		String aux;
		int indexSpace1, indexSpace2;
		indexSpace1 = line.indexOf(' ', 2);
		indexSpace2 = line.indexOf(' ', indexSpace1 + 1);
		if ((indexSpace1 == -1) || (indexSpace2 == -1))
		{
			throw new IOException("Linha do arquivo incorreta: " + line);
		}
		inverted = (line.charAt(0) == '-');
		index1 = Integer.parseInt(line.substring(2, indexSpace1));
		index2 = Integer.parseInt(line.substring(indexSpace1 + 1, indexSpace2));
		valueMatch = Integer.parseInt(line.substring(indexSpace2 + 1));
	}

	public int getIndex1()
	{
		return index1;
	}

	public int getIndex2()
	{
		return index2;
	}

	public boolean isInverted()
	{
		return inverted;
	}

	public int getValueMatch()
	{
		return valueMatch;
	}

}
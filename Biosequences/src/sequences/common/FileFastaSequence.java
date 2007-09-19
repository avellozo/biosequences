/*
 * Criado em 21/05/2004
 */

package sequences.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Augusto
 * @data 21/05/2004
 */
public class FileFastaSequence extends FileSequence
{
	BufferedReader	in;
	int				b;
	private int		initialCapacity	= 256;

	public FileFastaSequence(File file) throws IOException,
			FileNotFoundException
	{
		super(file);
	}

	public FileFastaSequence(String fileName) throws IOException,
			FileNotFoundException
	{
		super(fileName);
	}

	public Sequence getSequence() throws IOException
	{
		String name = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream(
			initialCapacity = 256);
		while (b != -1)
		{
			if (((char) b) == '>')
			{
				// le o nome da sequencia se for a primeira letra válida
				if ((out.size() == 0) && (name == null))
				{
					name = "";
					while (((b = in.read()) != -1) && (b != 10) && (b != 13))
					{
						name = name + (char) b;
					}
				}
				else
				{
					break;
				}
			}
			else
			// le a sequencia
			{
				if (isValid((char) b))
				{
					out.write(b);
				}
				b = in.read();
			}
		}
		if (name == null)
		{
			name = file.getName();
		}
		return createSequence(name, out.toByteArray());
	}

	private boolean isValid(char c)
	{
		switch (c)
		{
		case 'A':
		case 'C':
		case 'T':
		case 'G':
		case 'a':
		case 'c':
		case 't':
		case 'g':
			return true;
		default:
			return false;
		}
	}

	public void setFile(File file) throws IOException, FileNotFoundException
	{
		this.file = file;
		String fileName = file.getAbsolutePath();
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
		b = in.read();
	}

	public void setFile(String fileName) throws IOException,
			FileNotFoundException
	{
		this.file = new File(fileName);
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
		b = in.read();
	}
}

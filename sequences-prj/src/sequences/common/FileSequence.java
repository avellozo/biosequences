/*
 * Criado em 21/05/2004
 */

package sequences.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Augusto
 * @data 21/05/2004
 */
public class FileSequence
{
	File	file;

	/**
	 * 
	 */
	public FileSequence(File file) throws IOException, FileNotFoundException
	{
		setFile(file);
	}

	public FileSequence(String fileName) throws IOException,
			FileNotFoundException
	{
		setFile(fileName);
	}

	public Sequence getSequence() throws IOException
	{
		String name = file.getName();
		FileInputStream fis = new FileInputStream(file);
		byte[] letters = new byte[(int) file.length()];
		int off = 0;
		int numBytesRead = 0;
		while ((numBytesRead = fis.read(letters, off = off + numBytesRead, fis
			.available())) != 0)
			;
		fis.close();
		return createSequence(name, letters);
	}

	protected Sequence createSequence(String name, byte[] letters)
	{
		return new SequenceByteArray(name, letters);
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file) throws IOException, FileNotFoundException
	{
		if (file == null)
		{
			throw new FileNotFoundException("Arquivo inexistente: Null");
		}
		if (!file.exists())
		{
			throw new FileNotFoundException("Arquivo inexistente:"
				+ file.getPath());
		}
		this.file = file;
	}

	public void setFile(String fileName) throws IOException,
			FileNotFoundException
	{
		setFile(new File(fileName));
	}

}
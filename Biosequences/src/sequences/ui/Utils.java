/*
 * Created on 07/10/2005
 */
package sequences.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author Augusto F. Vellozo
 */
public class Utils
{

	public static BufferedReader getFileBufferedReader(String fileName)
			throws FileNotFoundException
	{
		File file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				return new BufferedReader(new InputStreamReader(Thread
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
			return new BufferedReader(new FileReader(fileName));
		}
	}
	
	public static CharBuffer getFileCharBuffer(String fileName) throws IOException
	{
		// Get a Channel for the source file
		File f = new File(fileName);
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc
			.size());
		Charset cs = Charset.forName("8859_1");
		CharsetDecoder cd = cs.newDecoder();
		return cd.decode(bb);
	}
}
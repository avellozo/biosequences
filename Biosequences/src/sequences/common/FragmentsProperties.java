/*
 * Criado em 26/06/2004
 */

package sequences.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map;

/**
 * @author Augusto
 * @data 26/06/2004
 */
public class FragmentsProperties
{
	Properties	prop;
	String		lastLine;

	FragmentsProperties(BufferedReader in) throws IOException
	{
		StringBuffer strPropSeq = new StringBuffer(400);
		lastLine = in.readLine();
		while ((lastLine != null) && (lastLine.length() != 0)
			&& (lastLine.charAt(0) == '#'))
		{
			strPropSeq.append(lastLine.toCharArray(), 1, lastLine.length() - 1);
			strPropSeq.append('\n');
			lastLine = in.readLine();
		}
		prop = new Properties();
		prop.load(new StringBufferInputStream(strPropSeq.toString()));
	}

	public int getNumFrags()
	{
		return Integer.parseInt(prop.getProperty("NumFrags"));
	}

	public String getProperty(String key)
	{
		return prop.getProperty(key);
	}

	public String getLastLine()
	{
		return lastLine;
	}

	public String toString()
	{
		int max = prop.size() - 1;
		StringBuffer buf = new StringBuffer();
		Iterator it = prop.entrySet().iterator();
		for (int i = 0; i <= max; i++)
		{
			Map.Entry e = (Map.Entry) (it.next());
			Object key = e.getKey();
			Object value = e.getValue();
			buf.append((key == this ? "(this Map)" : key) + "="
				+ (value == this ? "(this Map)" : value));
			if (i < max)
				buf.append("\n");
		}
		return buf.toString();
	}
}

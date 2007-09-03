/*
 * Created on 29/12/2004
 */
package sequences.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author Augusto
 */
public class MatchesWeight
// Arquivo do tipo: score weight
{
	int		maxMatch;
	int		minMatch;

	int[]	weights;

	public MatchesWeight(String fileName) throws FileNotFoundException,
			IOException
	{
		// descobre o máximo e mínimo valor de match
		Properties prop = new Properties();
		InputStream in;
		File file = new File(fileName);
		if (!file.exists())
		{
			try
			{
				in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName);
			}
			catch (Exception e)
			{
				throw new FileNotFoundException("Arquivo inexistente:"
					+ fileName);
			}
		}
		else
		{
			in = new FileInputStream(file);
		}

		prop.load(in);
		int key, value;
		Map.Entry entry;
		Iterator it = prop.entrySet().iterator();
		if (it.hasNext())
		{
			entry = (Map.Entry) it.next();
			try
			{
				key = Integer.parseInt((String) entry.getKey());
				value = Integer.parseInt((String) entry.getValue());
				maxMatch = key;
				minMatch = key;
			}
			catch (ClassCastException e)
			{
			}
			catch (NumberFormatException e)
			{
			}
			while (it.hasNext())
			{
				entry = (Map.Entry) it.next();
				try
				{
					key = Integer.parseInt((String) entry.getKey());
					value = Integer.parseInt((String) entry.getValue());
					if (key > maxMatch)
					{
						maxMatch = key;
					}
					if (key < minMatch)
					{
						minMatch = key;
					}
				}
				catch (ClassCastException e)
				{
				}
				catch (NumberFormatException e)
				{
				}
			}
		}
		else
		{
			maxMatch = 0;
			minMatch = 1;
		}
		weights = new int[maxMatch - minMatch + 1];
		String strWeight;
		int lastWeight = 0;
		for (int i = minMatch; i <= maxMatch; i++)
		{
			strWeight = (String) prop.get(Integer.toString(i));
			if (strWeight != null)
			{
				lastWeight = Integer.parseInt(strWeight);
			}
			weights[i - minMatch] = lastWeight;
		}
	}

	public int getWeight(int match)
	{
		if (match >= maxMatch)
		{
			return weights[maxMatch - minMatch];
		}
		if (match <= minMatch)
		{
			return weights[0];
		}
		return weights[match - minMatch];
	}
}

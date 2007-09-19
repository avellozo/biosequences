/*
 * Created on 03/08/2006
 */
package sequences.ui;

import java.io.PrintStream;
import java.text.MessageFormat;

public class LinePrinterBlast
{
	int	b1, e1, b2, e2, pos;
	StringBuffer	str1, str2, str3;
	PrintStream		out;
	int				maxCols;

	public LinePrinterBlast(PrintStream out, int maxCols)
	{
		this.out = out;
		this.maxCols = maxCols;
		initLine();
	}

	public void initLine()
	{
		str1 = new StringBuffer(maxCols);
		str2 = new StringBuffer(maxCols);
		str3 = new StringBuffer(maxCols);
		b1 = b2 = e1 = e2 = -1;
		pos = 0;
	}

	public void print(char c1, char c2, char c3, int pos1, int pos2)
	{
		if (pos1 >= 0)
		{
			if (b1 < 0)
			{
				b1 = pos1;
			}
			e1 = pos1;
		}
		if (pos2 >= 0)
		{
			if (b2 < 0)
			{
				b2 = pos2;
			}
			e2 = pos2;
		}
		str1.append(c1);
		str2.append(c2);
		str3.append(c3);
		pos++;
		if (pos >= maxCols)
		{
			flush();
		}
	}

	public void flush()
	{
		int i;
		String b1Str = "" + b1, b2Str = "" + b2, str = "";
		int b1Length = b1Str.length();
		int b2Length = b2Str.length();
		if (b1Length > b2Length)
		{
			for (i = b2Length; i < b1Length; i++)
			{
				b2Str = " " + b2Str;
			}
			for (i = 0; i < b1Length; i++)
			{
				str = " " + str;
			}
		}
		else
		{
			for (i = b1Length; i < b2Length; i++)
			{
				b1Str = " " + b1Str;
			}
			for (i = 0; i < b2Length; i++)
			{
				str = " " + str;
			}
		}
		out.println(MessageFormat.format(Messages.getString("Bim.msgBlastLine1") //$NON-NLS-1$
			, b1Str, str1.toString(), e1));
		out.println(MessageFormat.format(Messages.getString("Bim.msgBlastLine2") //$NON-NLS-1$
			, str, str2.toString()));
		out.println(MessageFormat.format(Messages.getString("Bim.msgBlastLine3") //$NON-NLS-1$
			, b2Str, str3.toString(), e2));
		out.println();
		initLine();
	}

	public void preparePrintExtended(int qtty)
	{
		if ((pos+qtty)>maxCols)
		{
			flush();
		}
	}

}

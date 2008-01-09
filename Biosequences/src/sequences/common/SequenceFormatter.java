/*
 * Created on 09/01/2008
 */
package sequences.common;

public class SequenceFormatter
{
	private static final int	LINE_WIDTH	= 60;

	//Reference: JAligner
	public static String toFasta(Sequence seq, int lineWidth)
	{
		StringBuffer buffer = new StringBuffer(">");
		buffer.append(seq.getName() == null ? "" : seq.getName());
		buffer.append("\n");
		for (int i = 0, n = seq.getLength(); i * lineWidth < n; i++)
		{
			for (int j = i * lineWidth, m = (i + 1) * lineWidth < n ? (i + 1) * lineWidth : n; j < m; j++)
			{
				buffer.append(seq.getLetter(j));
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public static String toFasta(Sequence seq)
	{
		return toFasta(seq, LINE_WIDTH);
	}
}

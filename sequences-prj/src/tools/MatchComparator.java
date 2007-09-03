/*
 * Created on 13/10/2005
 */
package tools;

import java.util.Comparator;

/**
 * @author Augusto F. Vellozo
 */
public class MatchComparator implements Comparator
{

	public int compare(Object o1, Object o2)
	{
		Match m1 = (Match) o1;
		Match m2 = (Match) o2;
		if (m1.gene1.position == m2.gene1.position && m1.gene2.position == m2.gene2.position && m1.getSignal().equals(m2.getSignal()))
		{
			return 0;
		}
		if ((m1.gene1.position < m2.gene1.position) || (m1.gene1.position == m2.gene1.position && m1.gene2.position < m2.gene2.position) || (m1.gene1.position == m2.gene1.position && m1.gene2.position == m2.gene2.position && m1.getSignal().equals(m1.SIGNAL_PLUS)))
		{
			return -1;
		}
		return 1;
	}

}

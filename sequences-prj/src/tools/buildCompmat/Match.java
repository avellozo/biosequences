/*
 * Created on 13/10/2005
 */
package tools.buildCompmat;

/**
 * @author Augusto F. Vellozo
 */
public class Match
{

	public final String SIGNAL_MINUS = "-"; 
	public final String SIGNAL_PLUS = "+"; 
	GenePosition gene1;
	GenePosition gene2;
	double evalue;
	

	public Match(GenePosition gene1, GenePosition gene2, double evalue) throws Exception
	{
		this.gene1 = gene1;
		this.gene2 = gene2;
		if (gene1 == null || gene2 == null)
		{
			throw new Exception("genes inválidos");
		}
		this.evalue = evalue;
	}
	
	public String getSignal()
	{
		if (gene1 != null && gene2 != null)
		{
			return (gene1.isComplement() == gene2.isComplement() ? SIGNAL_PLUS
				: SIGNAL_MINUS);
		}
		return null;
	}
	
	public int getMatchValue()
	{
		int value;
		double logEvalue = Math.log(evalue)/Math.log(10);
		if (evalue == 0 || logEvalue < -500)
		{
			value = 500;
		}
		else
		{
			value = -Math.round((float) logEvalue);
		}
		return value;
	}
	
	
//	public boolean equals(Object obj)
//	{
//		Match m1 = this;
//		Match m2 = (Match) obj;
//		return (m1.gene1.position == m2.gene1.position && m1.gene2.position == m2.gene2.position && m1.getSignal().equals(m2.getSignal()));
//	}
}

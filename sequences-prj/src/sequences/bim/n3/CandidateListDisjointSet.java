/*
 * Created on 16/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public class CandidateListDisjointSet extends CandidateList
{
	CandidateEntryDisjointSet[]	candidate;

	public CandidateListDisjointSet(int maxJ)
	{
		super();
		candidate = new CandidateEntryDisjointSet[maxJ];
	}

	protected CandidateEntry createCandidateEntry(int j, int gap)
	{
		candidate[j] = new CandidateEntryDisjointSet(j, gap);
		return candidate[j];
	}

	public Candidate find(int j)
	{
		return findSet(candidate[j]);
	}

	protected CandidateEntryDisjointSet findSet(CandidateEntryDisjointSet c)
	{
		CandidateEntryDisjointSet c1 = c;
		CandidateEntryDisjointSet parent;
		while (c != c.parent)
		{
			c = c.parent;// = findSet(c.parent);
		}
		parent = c;
		c = c1;
		while (c != c.parent)
		{
			c1 = c;
			c = c.parent;// = findSet(c.parent);
			c1.parent = parent;
		}
		return parent;
	}

	public void remove(Candidate candidate)
	{
		CandidateEntryDisjointSet c = (CandidateEntryDisjointSet) candidate;
		CandidateEntryDisjointSet next = (CandidateEntryDisjointSet) c.next;
		if (c.rank > next.rank)
		{
			next.parent = c;
			c.setGap(next.getGap());
			c.setJ(next.getJ());
			super.remove(next);
		}
		else
		{
			c.parent = next;
			if (c.rank == next.rank)
			{
				next.rank++;
			}
			super.remove(c);
		}
	}
}

class CandidateEntryDisjointSet extends CandidateEntry
{
	CandidateEntryDisjointSet	parent;
	int							rank;

	public CandidateEntryDisjointSet(int j, int gap)
	{
		super(j, gap);
		parent = this;
		rank = 0;
	}

	public void setJ(int j)
	{
		this.j = j;
	}

}
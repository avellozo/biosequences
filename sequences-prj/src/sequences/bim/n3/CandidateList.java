/*
 * Created on 08/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public class CandidateList implements CandidateCollection
{
	CandidateEntry	header		= null;
	int				size		= 0;
	CandidateEntry	lastFind	= null;

	public CandidateList()
	{
	}

	public Candidate add(int j, int gap)
	{
		CandidateEntry c = createCandidateEntry(j, gap);
		add(c);
		return c;
	}

	protected CandidateEntry createCandidateEntry(int j, int gap)
	{
		return new CandidateEntry(j, gap);
	}

	protected void add(CandidateEntry c)
	{
		if (header == null)
		{
			header = c;
			c.next = header;
			c.previous = header;
			size++;
		}
		else
		{
			addBefore(c, header);
		}
	}

	protected void addBefore(CandidateEntry newCandidate,
			CandidateEntry candidate)
	{
		newCandidate.previous = candidate.previous;
		candidate.previous.next = newCandidate;
		newCandidate.next = candidate;
		candidate.previous = newCandidate;
		size++;
	}

	public void remove(Candidate candidate)
	{
		CandidateEntry c = (CandidateEntry) candidate;
		if (c == header)
		{
			if (header.next != c)
			{
				header = header.next;
			}
			else
			{
				header = null;
			}
		}
		c.next.previous = c.previous;
		c.previous.next = c.next;
		size--;
	}

	public Candidate find(int j)
	{
		if (lastFind == null)
		{
			lastFind = header;
		}
		CandidateEntry c;
		do
		{
			c = lastFind;
			if (lastFind.getJ() < j && lastFind.next != header)
			{
				lastFind = lastFind.next;
			}
			else
			{
				if (lastFind.getJ() > j && lastFind != header
					&& lastFind.previous.getJ() >= j)
				{
					lastFind = lastFind.previous;
				}
			}
		}
		while (c != lastFind);
		return lastFind;
	}

	public Candidate getFirst()
	{
		return header;
	}

	public boolean isEmpty()
	{
		return (header == null);
	}

	public int size()
	{
		return size;
	}

	public Candidate getPrevious(Candidate candidate)
	{
		CandidateEntry c = (CandidateEntry) candidate;
		if (c != header)
		{
			return c.previous;
		}
		return null;
	}

}

class CandidateEntry extends Candidate
{
	CandidateEntry	next;
	CandidateEntry	previous;

	public CandidateEntry(int j, int gap)
	{
		super(j, gap);
	}

}
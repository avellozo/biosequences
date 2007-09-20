/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

/**
 * @author Augusto F. Vellozo
 */
public interface CandidateCollection
{
	public Candidate add(int j, int gap);

	public void remove(Candidate candidate);

	public Candidate find(int j);

	// encontra o candidato com menor j tq candidate.j >= j

	public Candidate getPrevious(Candidate candidate);

	public Candidate getFirst();

	public boolean isEmpty();

	public int size();

}

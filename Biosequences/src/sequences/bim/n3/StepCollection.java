/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

import java.util.Iterator;

/**
 * @author Augusto F. Vellozo
 */
public interface StepCollection
{
	public void add(Step step);

	public Step get(int index);

	public boolean isEmpty();

	public int size();

	public Iterator iterator();

}

/*
 * Created on 07/09/2005
 */
package sequences.bim.n3;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Augusto F. Vellozo
 */
public class StepArray implements StepCollection
{
	ArrayList	array;

	public StepArray()
	{
		array = new ArrayList();
	}

	public StepArray(int initialCapacity)
	{
		array = new ArrayList(initialCapacity);
	}

	public void add(Step step)
	{
		array.add(step);
	}

	public Step get(int index)
	{
		return (Step) array.get(index);
	}

	public boolean isEmpty()
	{
		return array.isEmpty();
	}

	public int size()
	{
		return array.size();
	}

	public Iterator iterator()
	{
		return array.iterator();
	}

}

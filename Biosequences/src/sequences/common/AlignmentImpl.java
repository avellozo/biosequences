/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public abstract class AlignmentImpl implements Alignment
{
	OptimumPathMethod	method;
	long				time;
	EditGraph			eg;
	OptimumPath			path;

	protected AlignmentImpl(EditGraph eg, OptimumPathMethod method) throws ExceptionInvalidEditGraph
	{
		time = System.currentTimeMillis();
		this.method = method;
		this.eg = eg;
		path = method.createPath(eg.getFullRange(), eg);
		time = System.currentTimeMillis() - time;
	}

	public String getMethodName()
	{
		return method.getName();
	}

	public long getTimeToBuild()
	{
		return time;
	}

	public boolean isLocal()
	{
		return method.isLocal();
	}

	public OptimumPath getPath()
	{
		return path;
	}

	public String getParametersToPrint()
	{
		StringBuffer str = new StringBuffer();
		str.append("Method: ");
		str.append(getMethodName());
		str.append(System.getProperty("line.separator"));
		return null;
	}

}

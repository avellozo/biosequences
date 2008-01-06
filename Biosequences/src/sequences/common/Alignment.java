/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.OptimumPath;

public interface Alignment
{

	public Sequence getSeq1();

	public Sequence getSeq2();

	public String getMethodName();

	public long getTimeToBuild();

	public boolean isLocal();

	public OptimumPath getPath();

	public String getParametersToPrint();

}

/*
 * Created on 05/01/2008
 */
package sequences.common;

import sequences.editgraph.ArcFactorySimpleSequences;
import sequences.editgraph.EditGraph;
import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.ExceptionInvalidEditGraph;
import sequences.editgraph.OptimumPath;
import sequences.editgraph.OptimumPathMethod;

public class AlignmentClassic implements Alignment
{
	Sequence			seq1, seq2;
	OptimumPathMethod	method;
	long				time;
	EditGraph			eg;
	OptimumPath			path;

	protected AlignmentClassic(EditGraph eg, OptimumPathMethod method) throws ExceptionInvalidEditGraph
	{
		time = System.currentTimeMillis();
		this.method = method;
		this.eg = eg;
		path = method.createPath(eg.getFullRange(), eg);

		time = System.currentTimeMillis() - time;
	}

	public AlignmentClassic(Sequence seq1, Sequence seq2, boolean local, int match, int mismatch, int gap)
			throws ExceptionInvalidEditGraph
	{
		this(new EditGraphBasic(seq1.getLength(), seq2.getLength(), new ArcFactorySimpleSequences(seq1, seq2, match,
			mismatch, gap)), new OptimumPathMethodClassic(local));
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	public String getMethodName()
	{
		return "Classical alignment";
	}

	public Sequence getSeq1()
	{
		return seq1;
	}

	public Sequence getSeq2()
	{
		return seq2;
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
		// TODO Auto-generated method stub
		return null;
	}

}

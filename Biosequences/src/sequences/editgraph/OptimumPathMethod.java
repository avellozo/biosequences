package sequences.editgraph;

import sequences.editgraph.exception.ExceptionInvalidEditGraph;

public interface OptimumPathMethod<E extends EditGraph>
{
	public static final byte	LOCAL		= 'L';
	public static final byte	GLOBAL		= 'G';
	public static final byte	SEMIGLOBAL	= 'S';

	// vertexRange = (i1,j1)(i2,j2)
	// Global alignment: better path of (i1,j1)(i2,j2)
	// Local alignment: better path of (i1',j1')(i2',j2')
	// SemiGlobal alignment: better path of (i1,j1')(i2,j2')

	public OptimumPath createPath(VertexRange vertexRange, E eg) throws ExceptionInvalidEditGraph;

	public boolean isGlobal();

	public boolean isLocal();

	// SemiGlobal path : better path of first row to last row
	public boolean isSemiGlobal();

	public String getName();

}

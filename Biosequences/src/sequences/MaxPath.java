/*
 * Criado em 09/08/2004
 */
package sequences;

import java.io.PrintStream;


import sequences.editgraph.EditGraphOld;
import sequences.matrix.MatrixInt;

/**
 * @author Augusto
 * @data 09/08/2004
 */
public abstract class MaxPath
{
	//Monta uma matriz m[i,j] = distâncias de (i,0) a (n,j) no grafo de edição
	//onde n é o número de linhas do grafo de edição
	EditGraphOld	editGraph;
	long		time;
	MatrixInt	matrix;

	public MaxPath(EditGraphOld editGraph)
	{
		this(editGraph, null);
	}

	public MaxPath(EditGraphOld editGraph, PrintStream out)
	{
		time = System.currentTimeMillis();
		this.editGraph = editGraph;
		matrix = buildMatrix(out);
		time = System.currentTimeMillis() - time;
	}

	protected abstract MatrixInt buildMatrix(PrintStream out);

	public EditGraphOld getEditGraph()
	{
		return this.editGraph;
	}

	public void setEditGraph(EditGraphOld editGraph)
	{
		this.editGraph = editGraph;
	}

	public long getTime()
	{
		return this.time;
	}

	public int getMaxValue()
	{
		return getMatrix().getValue(0, getMatrix().getColsQtty() - 1);
	}

	public MatrixInt getMatrix()
	{
		return this.matrix;
	}
	
}
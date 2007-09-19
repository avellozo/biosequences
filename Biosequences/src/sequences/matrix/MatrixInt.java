/*
 * Criado em 08/07/2004
 */
package sequences.matrix;

/**
 * @author Augusto
 * @data 08/07/2004
 */
public interface MatrixInt
{
	public int getValue(int row, int col);

	public void setValue(int row, int col, int value);

	public int getRowsQtty();

	public int getColsQtty();

	public int[] getMaxRowValues();

	public int[] getIndexMaxRowValues();

	public int[] getMaxColValues();

	public int[] getIndexMaxColValues();

	// public void print(PrintStream out);
	//	
	// public void print(PrintStream out, int rows);
	//
	// public void printRow(PrintStream out, int row);
	//	
	// public boolean equals(MatrixInt m);

	public RowInt[] getRows();
	// public MatrixInt getInvertedMatrix();
}

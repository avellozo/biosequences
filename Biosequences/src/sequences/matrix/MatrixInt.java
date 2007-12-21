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
	//Representa uma matriz M[iMin..iMax][jMin..jMax] de inteiros

	public int getValue(int row, int col);

	public void setValue(int row, int col, int value);

	//retorna iEnd
	public int getIndexEndRow();

	//retorna iBegin
	public int getIndexBeginRow();

	//retorna jEnd
	public int getIndexEndCol();

	//retorna jBegin
	public int getIndexBeginCol();

	public int getRowsQtty();

	public int getColsQtty();

	public ElementInt getMaxValue();

	public ElementInt getMinValue();

	//Retorna o maior valor de um elemento para cada linha
	public ArrayInt getMaxRowValues();

	//Retorna o índice do elemento de maior valor para cada linha
	public ArrayInt getIndexMaxRowValues();

	public ArrayInt getMaxColValues();

	public ArrayInt getIndexMinColValues();

	public ArrayInt getMinRowValues();

	public ArrayInt getIndexMinRowValues();

	public ArrayInt getMinColValues();

	public ArrayInt getIndexMaxColValues();

	// public void print(PrintStream out);
	//	
	// public void print(PrintStream out, int rows);
	//
	// public void printRow(PrintStream out, int row);
	//	
	// public boolean equals(MatrixInt m);

	public ArrayInt[] getRows();

	public ArrayInt getRow(int row);

	// public MatrixInt getInvertedMatrix();

	//Retorna uma matriz indexada a partir do zero
	public int[][] getMatrixPrimitive();

	public String toString();

	public boolean isValidRowCol(int row, int col);
}

/*
 * Created on 20/12/2007
 */
package sequences.matrix;

public class ElementInt
{

	int	value, row, col;

	public ElementInt(int value, int row, int col)
	{
		super();
		this.value = value;
		this.row = row;
		this.col = col;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

}

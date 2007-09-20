/*
 * Criado em 08/07/2004
 */

package sequences.ui;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Augusto @data 08/07/2004
 */
public class MatrixIntCellRenderer extends JLabel implements TableCellRenderer
{
	private Color	colorBackgroundDefault	= Color.white;
	private Color	colorForegroundDefault	= Color.black;
	
	private Color	colorBackgroundZero		= Color.white;
	private Color	colorForegroundZero		= Color.white;
	
	private Color	colorBackgroundDominant	= Color.red;
	private Color	colorForegroundDominant	= Color.black;
	
	private boolean changeZero = false;
	private boolean changeDominant = false;

	public MatrixIntCellRenderer(boolean changeZero, boolean changeDominant)
	{
		this.changeZero = changeZero;
		this.changeDominant = changeDominant;
		setOpaque(true);
		this.setBackground(Color.white);
		this.setForeground(Color.black);
	}

//	public MatrixIntCellRenderer()
//	{
//		this(false, false);
//	}
//
	public Component getTableCellRendererComponent(JTable table, Object
	value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		this.setBackground(colorBackgroundDefault);
		this.setForeground(colorForegroundDefault);
		if (value.toString().equals("0") && changeZero)
		{
			this.setBackground(colorBackgroundZero);
			this.setForeground(colorForegroundZero);
		}
		if (row > 0 && changeDominant)
		{
			if (column > 0)
			{
				String val, valUp, valLeft, valUpLeft;
				val = value.toString();
				valUp = table.getValueAt(row - 1, column).toString();
				valUpLeft = table.getValueAt(row - 1, column - 1).toString();
				valLeft = table.getValueAt(row, column - 1).toString();
				if (!val.equals(valUpLeft) && !val.equals(valUp)
				&& !val.equals(valLeft))
				{
					this.setForeground(colorForegroundDominant);
					this.setBackground(colorBackgroundDominant);
				}
			}
		}
		this.setText(value.toString());
		return this;
	}

	public Color getColorBackgroundDefault()
	{
		return colorBackgroundDefault;
	}

	public void setColorBackgroundDefault(Color colorBackgroundNotZero)
	{
		this.colorBackgroundDefault = colorBackgroundNotZero;
	}

	public Color getColorBackgroundZero()
	{
		return colorBackgroundZero;
	}

	public void setColorBackgroundZero(Color colorBackgroundZero)
	{
		this.colorBackgroundZero = colorBackgroundZero;
	}

	public Color getColorForegroundDefault()
	{
		return colorForegroundDefault;
	}

	public void setColorForegroundDefault(Color colorForegroundNotZero)
	{
		this.colorForegroundDefault = colorForegroundNotZero;
	}

	public Color getColorForegroundZero()
	{
		return colorForegroundZero;
	}

	public void setColorForegroundZero(Color colorForegroundZero)
	{
		this.colorForegroundZero = colorForegroundZero;
	}

	public Color getColorBackgroundDominant()
	{
		return colorBackgroundDominant;
	}

	public void setColorBackgroundDominant(Color colorBackgroundDominant)
	{
		this.colorBackgroundDominant = colorBackgroundDominant;
	}

	public Color getColorForegroundDominant()
	{
		return colorForegroundDominant;
	}

	public void setColorForegroundDominant(Color colorForegroundDominant)
	{
		this.colorForegroundDominant = colorForegroundDominant;
	}
	public boolean isChangeDominant()
	{
		return changeDominant;
	}

	public void setChangeDominant(boolean changeDominant)
	{
		this.changeDominant = changeDominant;
	}

	public boolean isChangeZero()
	{
		return changeZero;
	}

	public void setChangeZero(boolean changeZero)
	{
		this.changeZero = changeZero;
	}

}

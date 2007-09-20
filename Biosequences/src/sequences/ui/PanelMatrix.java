/*
 * Criado em 02/06/2004
 */

package sequences.ui;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntPrimitive;


/**
 * @author Augusto @data 02/06/2004
 */
public class PanelMatrix extends JScrollPane
{
	MatrixInt	matrix;
	JTable				table;

	public PanelMatrix(int[] a, TableCellRenderer cellRenderer)
	{
		this(new MatrixIntPrimitive(a), cellRenderer);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		table.setPreferredScrollableViewportSize(new Dimension(50, 250));
	}

	public PanelMatrix(int[][] m, TableCellRenderer cellRenderer)
	{
		this(new MatrixIntPrimitive(m), cellRenderer);
	}

	public PanelMatrix(MatrixInt m, TableCellRenderer cellRenderer)
	{
		matrix = m;
		TableModel dataModel = new AbstractTableModel()
		{
			public int getColumnCount()
			{
				return matrix.getColsQtty();
			}

			public int getRowCount()
			{
				return matrix.getRowsQtty();
			}

			public Object getValueAt(int row, int col)
			{
				return String.valueOf(matrix.getValue(row, col));
			}

			public String getColumnName(int columnIndex)
			{
				return String.valueOf(columnIndex);
			}
		};
		table = new JTable(0, 0);
		table.setModel(dataModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn col;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			col = table.getColumnModel().getColumn(i);
			col.setPreferredWidth(30);
		}
		table.setPreferredScrollableViewportSize(new Dimension(250, 250));
		/*
		 * int[] columns; if (matrixBim.length >0) { columns = new
		 * int[matrixBim[0].length]; } else { columns = new int[0]; } for (int i = 0;
		 * i < columns.length; i++) { columns[i] = i; }
		 */
		this.setViewportView(table);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		if (cellRenderer != null)
		{
			table.setDefaultRenderer(Object.class, cellRenderer);
		}
	}
}
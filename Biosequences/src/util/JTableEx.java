package util;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;



/**
 * @author Zafir Anjum
 */

public class JTableEx extends JTable
{
    protected JRowHeader        rowHeader;
    protected TableColumnModel  rowModel;
	
	public JTableEx() {
		this(null, null, null);
	}

	public JTableEx(TableModel dm) {
		this(dm, null, null);
	}

	public JTableEx(TableModel dm, TableColumnModel cm) {
		this(dm, cm, null);
	}

	public JTableEx(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm,cm,sm);
		setUI( new TableUIEx() );
	}

	public JTableEx(int numRows, int numColumns) {
		this(new DefaultTableModel(numRows, numColumns));
	}

	public JTableEx(final Vector rowData, final Vector columnNames) {
		super( rowData, columnNames );
		setUI( new TableUIEx() );
	}

	public JTableEx(final Object[][] rowData, final Object[] columnNames) {
		super( rowData, columnNames );
		setUI( new TableUIEx() );
	}
	
	public int rowAtPoint(Point point) {
		return getRowModel().getColumnIndexAtX(point.y);
	}

	public int getRowHeight( int row )
	{
		if( row >= getRowModel().getColumnCount() )
			return getRowHeight();
		return getRowModel().getColumn(row).getWidth();
	}
	
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        Rectangle cellFrame;
        TableColumn aColumn;

        cellFrame = new Rectangle();
//        cellFrame.height = getRowHeight() + rowMargin;
//        cellFrame.y = row * cellFrame.height;

        int index = 0;
        int columnMargin = getColumnModel().getColumnMargin();
        Enumeration enumeration = getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            aColumn = (TableColumn)enumeration.nextElement();
            cellFrame.width = aColumn.getWidth() + columnMargin;

            if (index == column)
                break;

            cellFrame.x += cellFrame.width;
            index++;
        }

        index = 0;
    	columnMargin = getRowModel().getColumnMargin();
        enumeration = getRowModel().getColumns();
        while (enumeration.hasMoreElements()) {
            aColumn = (TableColumn)enumeration.nextElement();
            cellFrame.height = aColumn.getWidth() + columnMargin;

            if (index == row)
                break;

            cellFrame.y += cellFrame.height;
            index++;
        }

        if (!includeSpacing) {
            Dimension spacing = getIntercellSpacing();
            // This is not the same as grow(), it rounds differently.
            cellFrame.setBounds(cellFrame.x +      spacing.width/2,
                                cellFrame.y +      spacing.height/2,
                                cellFrame.width -  spacing.width,
                                cellFrame.height - spacing.height);
        }
        return cellFrame;
    }

    public Object getValueAt(int row, int column) {
        return getModel().getValueAt(convertRowIndexToModel(row), convertColumnIndexToModel(column));
    }

    public void setValueAt(Object aValue, int row, int column) {
        getModel().setValueAt(aValue, convertRowIndexToModel(row), convertColumnIndexToModel(column));
    }

    public boolean isCellEditable(int row, int column) {
        return getModel().isCellEditable(convertRowIndexToModel(row), convertColumnIndexToModel(column));
    }
	
    public int convertRowIndexToModel(int viewRowIndex) {
        if (viewRowIndex < 0) {
            return viewRowIndex;
        }
        return getRowModel().getColumn(viewRowIndex).getModelIndex();
    }
	
    protected void configureEnclosingScrollPane() {
        Container p = getParent();
        if (p instanceof JViewport) {
            Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane)gp;
                // Make certain we are the viewPort's view and not, for
                // example, the rowHeaderView of the scrollPane -
                // an implementor of fixed columns might do this.
                JViewport viewport = scrollPane.getViewport();
                if (viewport == null || viewport.getView() != this) {
                    return;
                }
                scrollPane.setColumnHeaderView(getTableHeader());
                scrollPane.getViewport().setBackingStoreEnabled(true);
                scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
            	
            	setRowHeader( new JRowHeader( rowModel ) );
            	TableColumnModel rowModel1 = new DefaultTableColumnModel()
            		{
					    public int getColumnIndexAtX(int xPosition) {
						int index = 0;
						Point aPoint = new Point(1, xPosition);
						Rectangle columnRect = new Rectangle(0,0,3,0);
						Enumeration enumeration = getColumns();
					
						while (enumeration.hasMoreElements()) {
						    TableColumn aColumn = (TableColumn)enumeration.nextElement();
						    columnRect.height = aColumn.getWidth() + columnMargin;
					
						    if (columnRect.contains(aPoint))
							return index;
					
						    columnRect.y += columnRect.height;
						    index++;
						}
						return -1;
					    }
            		};
            	setRowModel( rowModel1 );
            	createDefaultRowsFromModel();
                scrollPane.setRowHeaderView(getRowHeader());
            }
        }
    }
	
    public void setRowHeader(JRowHeader newHeader) {
        if (rowHeader != newHeader) {
            // Release the old header
            if (rowHeader != null)
                rowHeader.setTable(null);

            rowHeader = newHeader;
            if (rowHeader != null)
                rowHeader.setTable(this);
        }
    }
	
    public JTableHeader getRowHeader() {
        return rowHeader;
    }
	
    public void setRowModel(TableColumnModel newModel) {
        if (newModel == null) {
            throw new IllegalArgumentException("Cannot set a null ColumnModel");
        }

        TableColumnModel oldModel = rowModel;
        if (newModel != oldModel) {
            if (oldModel != null)
                oldModel.removeColumnModelListener(this);

            rowModel = newModel;
            newModel.addColumnModelListener(this);


            // Set the column model of the header as well.
            if (rowHeader != null) {
                rowHeader.setColumnModel(newModel);
            }

	    firePropertyChange("rowModel", oldModel, newModel);
            resizeAndRepaint();
        }
    }

    public TableColumnModel getRowModel() {
        return rowModel;
    }
	
    public void createDefaultRowsFromModel() {
        TableModel m = getModel();
        if (m != null) {
            // Remove any current rowss
            TableColumnModel cm = getRowModel();
        	if( cm == null )
        		return;
            cm.removeColumnModelListener(this);
            while (cm.getColumnCount() > 0)
                cm.removeColumn(cm.getColumn(0));

            // Create new columns from the data model info
            for (int i = 0; i < m.getRowCount(); i++) {
                TableColumn newColumn = new TableColumn(i, getRowHeight());
		        String columnName = Integer.toString(i);
	            newColumn.setHeaderValue(columnName);
		        cm.addColumn(newColumn);
            }
            cm.addColumnModelListener(this);
        }
    }
	
    public void tableChanged(TableModelEvent e) {
        if (e == null || e.getFirstRow() == TableModelEvent.HEADER_ROW) {
            // The whole thing changed
            clearSelection();

            if (getAutoCreateColumnsFromModel())
                createDefaultColumnsFromModel();
        	
        	createDefaultRowsFromModel();

            resizeAndRepaint();
            if (tableHeader != null) {
                tableHeader.resizeAndRepaint();
            }
        	if( rowHeader != null ) {
        		rowHeader.resizeAndRepaint();
        	}
            return;
        }
		if (e.getType() == TableModelEvent.INSERT) {
			tableRowsInserted(e);							// **********************************
			return;
		}

		if (e.getType() == TableModelEvent.DELETE) {
			tableRowsDeleted(e);							// **********************************
			return;
		}

		int modelColumn = e.getColumn();
		int start = e.getFirstRow();
		int end = e.getLastRow();

		if (start == TableModelEvent.HEADER_ROW) {
			start = 0;
			end = Integer.MAX_VALUE;
		}

//		int rowHeight = getRowHeight() + rowMargin;
		Rectangle dirtyRegion;
		if (modelColumn == TableModelEvent.ALL_COLUMNS) {
			// 1 or more rows changed
//			dirtyRegion = new Rectangle(0, start * rowHeight,
			dirtyRegion = new Rectangle(0, getCellRect(start,0,false).y ,
										getColumnModel().getTotalColumnWidth(), 0);
		}
		else {
			// A cell or column of cells has changed.
			// Unlike the rest of the methods in the JTable, the TableModelEvent
			// uses the co-ordinate system of the model instead of the view.
			// This is the only place in the JTable where this "reverse mapping"
			// is used.
			int column = convertColumnIndexToView(modelColumn);
			dirtyRegion = getCellRect(start, column, false);
		}

		// Now adjust the height of the dirty region according to the weight of "end".
		// Check for Integer.MAX_VALUE as this will cause an overflow.
		if (end != Integer.MAX_VALUE) {
			//dirtyRegion.height = (end-start+1)*rowHeight;
			dirtyRegion.height = getCellRect(end+1,0,false).y - dirtyRegion.y;
			repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
		}
		// In fact, if the end is Integer.MAX_VALUE we need to revalidate anyway
		// because the scrollbar may need repainting.
		else {
			resizeAndRepaint();
		}
    }
	
	private void tableRowsInserted(TableModelEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		if (start < 0)
			start = 0;

		// Move down row height info - for rows below the first inserted row
		int rowCount = getRowCount();
		int rowsInserted = end - start + 1;
        TableColumnModel rm = getRowModel();
		for( int r = start; r < rowCount; r++ )
		{
			// ******** column namevalue
			rm.getColumn(r).setHeaderValue(Integer.toString(r+rowsInserted));
			rm.getColumn(r).setModelIndex(r+rowsInserted); 
			
			
			// Insert new rowmodel
	        TableColumn newColumn = new TableColumn(r, getRowHeight());
	        String columnName = Integer.toString(r);
	        newColumn.setHeaderValue(columnName);
	        rm.addColumn(newColumn);
		}
			
		// 1 or more rows added, so we have to repaint from the first
		// new row to the end of the table.  (Everything shifts down)
//		int rowHeight = getRowHeight() + rowMargin;
		Rectangle drawRect = new Rectangle(0, getCellRect(start,0,false).y ,
										getColumnModel().getTotalColumnWidth(), 0);
//										   (getRowCount()-start) * rowHeight);
		drawRect.height = getCellRect(rowCount,0,false).y - drawRect.y;

		// Adjust the selection to account for the new rows
		if (selectionModel != null) {
			if (end < 0)
				end = getRowCount()-1;
			int length = end - start + 1;

			selectionModel.insertIndexInterval(start, length, true);
		}
		revalidate();
		// PENDING(philip) Find a way to stop revalidate calling repaint
		// repaint(drawRect);
	}

	/*
	 * Invoked when rows have been removed from the table.
	 *
	 * @param e the TableModelEvent encapsulating the deletion
	 */
	private void tableRowsDeleted(TableModelEvent e) {
		int start = e.getFirstRow();
		int end = e.getLastRow();
		if (start < 0)
			start = 0;

		int deletedCount = end - start + 1;
		int previousRowCount = getRowCount() + deletedCount;
		
		// Remove any height information for deleted rows
        TableColumnModel rm = getRowModel();
		for( int i = start; i <= end; i++ )
			rm.removeColumn( rm.getColumn( i ) );
			
		// Move up row height info - for rows below the last deleted row
		for( int r = end + 1; r < previousRowCount; r++ )
		{
			// ******** column namevalue
			rm.getColumn(r).setHeaderValue(Integer.toString(r-deletedCount));
			rm.getColumn(r).setModelIndex(r-deletedCount); 
		}
			
		// 1 or more rows added, so we have to repaint from the first
		// new row to the end of the table.  (Everything shifts up)
//		int rowHeight = getRowHeight() + rowMargin;
		Rectangle drawRect = new Rectangle(0, getCellRect(start,0,false).y ,
										getColumnModel().getTotalColumnWidth(),0);
//										(previousRowCount - start) * rowHeight);
		drawRect.height = getCellRect(previousRowCount,0,false).y - drawRect.y;

		// Adjust the selection to account for the new rows
		if (selectionModel != null) {
			if (end < 0)
				end = getRowCount()-1;

			selectionModel.removeIndexInterval(start, end);
		}
		revalidate();
	}
}  // End of Class JTableEx





class JRowHeader extends JTableHeader
{
	JRowHeader( TableColumnModel colModel )
	{
		super( colModel );
		setUI( new RowHeaderUI() );
	}
	
    public Rectangle getHeaderRect(int columnIndex) {
		TableColumnModel columnModel = getColumnModel();
	
		if ((columnIndex < 0) || (columnIndex >= columnModel.getColumnCount())) {
		    throw new IllegalArgumentException("Column index out of range");
		}
	
		int rectY = 0;
		int column = 0;
		int columnMargin = getColumnModel().getColumnMargin();
		Enumeration enumeration = getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
		    TableColumn aColumn = (TableColumn)enumeration.nextElement();
	
		    if (column == columnIndex) {
			return new Rectangle(0, rectY,
							getPreferredSize().width,
					     aColumn.getWidth() + columnMargin );
		    }
		    rectY += aColumn.getWidth() + columnMargin;
		    column++;
		}
		return new Rectangle();
    }
}

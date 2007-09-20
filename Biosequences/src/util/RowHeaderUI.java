package util;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;



public class RowHeaderUI extends BasicTableHeaderUI
{
	
    class MouseInputHandler1 implements MouseInputListener {

        private int lastEffectiveMouseY;

        public void mouseClicked(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {
            getHeader().setDraggedColumn(null);
            getHeader().setResizingColumn(null);
            getHeader().setDraggedDistance(0);

            Point p = e.getPoint();
            lastEffectiveMouseY = p.y;

            // First find which header cell was hit
            TableColumnModel columnModel = getHeader().getColumnModel();
            int index = columnModel.getColumnIndexAtX(p.y);

            if (index != -1) {
                // The last 3 pixels + 3 pixels of next column are for resizing
                int resizeIndex = getResizingColumn(p);
                if (getHeader().getResizingAllowed() && (resizeIndex != -1)) {
                    TableColumn hitColumn = columnModel.getColumn(resizeIndex);
                    getHeader().setResizingColumn(hitColumn);
                }
                else if (getHeader().getReorderingAllowed()) {
                    TableColumn hitColumn = columnModel.getColumn(index);
                    getHeader().setDraggedColumn(hitColumn);
                }
                else {  // Not allowed to reorder or resize.
                }
            }
        }

        public void mouseMoved(MouseEvent e) {
            if (getResizingColumn(e.getPoint()) != -1) {
                Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                if (getHeader().getCursor() != resizeCursor) {
                    getHeader().setCursor(resizeCursor);
                }
            }
            else {
                Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                if (getHeader().getCursor() != defaultCursor) {
                    getHeader().setCursor(defaultCursor);
                }
            }
        }

        public void mouseDragged(MouseEvent e) {
            int mouseY = e.getY();
            int deltaY = mouseY - lastEffectiveMouseY;

            if (deltaY == 0) {
                return;
            }

            TableColumn resizingColumn  = getHeader().getResizingColumn();
            TableColumn draggedColumn  = getHeader().getDraggedColumn();

            if (resizingColumn != null) {
	        	int oldWidth = resizingColumn.getWidth();
                int newWidth = oldWidth + deltaY;
                resizingColumn.setWidth(newWidth);

//                // PENDING(philip): Should't need to refer to the table here.
                int resizingColumnIndex = viewIndexForColumn(resizingColumn);
//                header.getTable().sizeColumnsToFit(resizingColumnIndex); 
                int acheivedDeltaY = resizingColumn.getWidth() - oldWidth;
                lastEffectiveMouseY = lastEffectiveMouseY + acheivedDeltaY;

                getHeader().revalidate();
                getHeader().repaint();
                if (getHeader().getUpdateTableInRealTime()) {
                    JTable table = getHeader().getTable();
                    table.revalidate();
                    table.repaint();
                }
            }
            else if (draggedColumn != null) {
                move(e, deltaY);
                lastEffectiveMouseY = mouseY;
            }
            else {
                // Neither dragging nor resizing ...
                lastEffectiveMouseY = mouseY;
            }
        }

        public void mouseReleased(MouseEvent e) {
            getHeader().setResizingColumn(null);
            getHeader().setDraggedColumn(null);
            getHeader().setDraggedDistance(0);

            // Repaint to finish cleaning up
            getHeader().repaint();
            JTable table = getHeader().getTable();
            if (table != null)
                table.repaint();
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
//
// Protected & Private Methods
//

        private int viewIndexForColumn(TableColumn aColumn) {
            TableColumnModel cm = getHeader().getColumnModel();
            for (int column = 0; column < cm.getColumnCount(); column++) {
                if (cm.getColumn(column) == aColumn) {
                    return column;
                }
            }
            return -1;
        }

        private void move(MouseEvent e, int delta) {
            TableColumnModel columnModel = getHeader().getColumnModel();
            int lastColumn = columnModel.getColumnCount() - 1;

            TableColumn draggedColumn = getHeader().getDraggedColumn();
            int draggedDistance = getHeader().getDraggedDistance() + delta;
            int hitColumnIndex = viewIndexForColumn(draggedColumn);

            // Now check if we have moved enough to do a swap
            if ((draggedDistance < 0) && (hitColumnIndex != 0)) {
                // Moving left; check prevColumn
                int width = columnModel.getColumnMargin() +
                    columnModel.getColumn(hitColumnIndex-1).getWidth();
                if (-draggedDistance > (width / 2)) {
                    // Swap me
                    columnModel.moveColumn(hitColumnIndex, hitColumnIndex-1);

                    draggedDistance = width + draggedDistance;
                    hitColumnIndex--;
                }
            }
            else if ((draggedDistance > 0) && (hitColumnIndex != lastColumn)) {
                // Moving right; check nextColumn
                int width = columnModel.getColumnMargin() +
                    columnModel.getColumn(hitColumnIndex+1).getWidth();
                if (draggedDistance > (width / 2)) {
                    // Swap me
                    columnModel.moveColumn(hitColumnIndex, hitColumnIndex+1);

                    draggedDistance = -(width - draggedDistance);
                    hitColumnIndex++;
                }
            }

            // Redraw, compute how much we are moving and the total redraw rect
            Rectangle redrawRect = getHeader().getHeaderRect(hitColumnIndex);  // where I was
            redrawRect.y += getHeader().getDraggedDistance();
            // draggedDistance += delta;
            Rectangle redrawRect2 = getHeader().getHeaderRect(hitColumnIndex); // where I'm now
            redrawRect2.y += draggedDistance;
            redrawRect = redrawRect.union(redrawRect2);  // Union the 2 rects

            getHeader().repaint(0, redrawRect.y, redrawRect.width, redrawRect.height);
            if (getHeader().getUpdateTableInRealTime()) {
                JTable table = getHeader().getTable();
                if (table != null)
                    table.repaint(0, redrawRect.y, 
                    				table.getSize().width,			//**************************
                                  redrawRect.height);
            }

            getHeader().setDraggedColumn(columnModel.getColumn(hitColumnIndex));
            getHeader().setDraggedDistance(draggedDistance);
        }

        private int getResizingColumn(Point p) {
            int column = 0;
            Rectangle resizeRect = new Rectangle(0,-3,getHeader().getPreferredSize().width,6);
            int columnMargin = getHeader().getColumnModel().getColumnMargin();
            Enumeration enumeration = getHeader().getColumnModel().getColumns();

            while (enumeration.hasMoreElements()) {
                TableColumn aColumn = (TableColumn)enumeration.nextElement();
                resizeRect.y += aColumn.getWidth() + columnMargin;

                if (resizeRect.y > p.y) {
                    // Don't have to check the rest, we already gone past p
                    break;
                }
                if (resizeRect.contains(p))
                    return column;

                column++;
            }
            return -1;
        }
    }
	
	protected JTableHeader getHeader()
	{
		return header;
	}
	
    public static ComponentUI createUI(JComponent h) {
        return new RowHeaderUI();
    }
	
    protected MouseInputListener createMouseInputListener() {
        return new MouseInputHandler1();
    }
	
//
// Size Methods
//

    private int getHeaderWidth() {
        int width = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for(int column = 0; column < columnModel.getColumnCount(); column++) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null)
            {
            	renderer = getHeader().getDefaultRenderer();
            }
            Component comp = renderer.getTableCellRendererComponent(header.getTable(),
                                               aColumn.getHeaderValue(), false, false,
                                               -1, column);
        	width = Math.max(width, comp.getPreferredSize().width);
        }
        return width + 10;
    }

    private Dimension createHeaderSize(long height) {
        TableColumnModel columnModel = header.getColumnModel();
        // None of the callers include the intercell spacing, do it here.
        height += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (height > Integer.MAX_VALUE) {
            height = Integer.MAX_VALUE;
        }
        return new Dimension(getHeaderWidth(), (int)height);
    }
	
    /**
     * Return the minimum size of the header. The minimum width is the sum 
     * of the minimum widths of each column (plus inter-cell spacing).
     */
    public Dimension getMinimumSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            width = width + aColumn.getMinWidth();
        }
        return createHeaderSize(width);
    }

    /**
     * Return the preferred size of the header. The preferred height is the 
     * maximum of the preferred heights of all of the components provided 
     * by the header renderers. The preferred width is the sum of the 
     * preferred widths of each column (plus inter-cell spacing).
     */
    public Dimension getPreferredSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
//            width = width + aColumn.getPreferredWidth();
            width = width + aColumn.getWidth();
        }
        return createHeaderSize(width);
    }

    /**
     * Return the maximum size of the header. The maximum width is the sum 
     * of the maximum widths of each column (plus inter-cell spacing).
     */
    public Dimension getMaximumSize(JComponent c) {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            width = width + aColumn.getMaxWidth();
        }
        return createHeaderSize(width);
    }


    public void paint(Graphics g, JComponent c) {
        Rectangle clipBounds = g.getClipBounds();

        if (header.getColumnModel() == null)
            return;

        int column = 0;
        boolean drawn = false;
        int draggedColumnIndex = -1;
        Rectangle draggedCellRect = null;
        Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);

        Enumeration enumeration = header.getColumnModel().getColumns();

        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            int columnMargin = header.getColumnModel().getColumnMargin();
            cellRect.height = aColumn.getWidth() + columnMargin;
            // Note: The header cellRect includes columnMargin so the
            //       drawing of header cells will not have any gaps.

            if (cellRect.intersects(clipBounds)) {
                drawn = true;
                if (aColumn != header.getDraggedColumn()) {
                    paintCell(g, cellRect, column);
                }
                else {
                    // Draw a gray well in place of the moving column
                    g.setColor(header.getParent().getBackground());
                    g.fillRect(cellRect.x, cellRect.y,
                               cellRect.width, cellRect.height);
                    draggedCellRect = new Rectangle(cellRect);
                    draggedColumnIndex = column;
                }
            }
            else {
                if (drawn)
                    // Don't need to iterate through the rest
                    break;
            }

            cellRect.y += cellRect.height;
            column++;
        }

        // draw the dragged cell if we are dragging
        TableColumn draggedColumnObject = header.getDraggedColumn();
        if (draggedColumnObject != null && draggedCellRect != null) {
            draggedCellRect.x += header.getDraggedDistance();
            paintCell(g, draggedCellRect, draggedColumnIndex);
        }
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        if (renderer == null)
        {
        	renderer = getHeader().getDefaultRenderer();
        }
        Component component = renderer.getTableCellRendererComponent(
                  header.getTable(), aColumn.getHeaderValue(),
                  false, false, -1, columnIndex);
        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
                            cellRect.width, cellRect.height, true);
    }

}
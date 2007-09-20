package util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Table
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Table");
		frame.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				Window win = e.getWindow();
				win.setVisible(false);
				win.dispose();
				System.exit(0);
			}
		} );
		
		JTable table = new JTableEx( 4, 5 );
//		JTable table = new JTableEx( new String[][]{{"r0c0", "r0c1"},{"r1c0", "r1c1"}}, new String[]{"First", "Second"} );
		table.setRowSelectionAllowed( false );
		JScrollPane sp = new JScrollPane(table);
		frame.getContentPane().add( sp );


		frame.pack();
		frame.show();
		
//		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
//		table.setValueAt( "asd fasldkfj asldjf lkdsa fjlf lhgds klhdsagkljfdsal;jkjfds", 0, 0 );
//		table.sizeColumnsToFit( 0 );
//		
//		JButton b1 = new JButton( "Column 1", new ImageIcon( "new.gif" ) );
//		JButton b2 = new JButton( "Column 2", new ImageIcon( "new.gif" ) );
//		table.getColumnModel().getColumn(0).setHeaderRenderer( new JComponentCellRenderer() );
//		table.getColumnModel().getColumn(1).setHeaderRenderer( new JComponentCellRenderer() );
//		table.getColumnModel().getColumn(0).setHeaderValue( b1 );
//		table.getColumnModel().getColumn(1).setHeaderValue( b2 );
//		table.getTableHeader().revalidate();
//		table.getTableHeader().invalidate();
//		table.validate();
//		frame.repaint();
//		frame.pack();
		
	}
}


class JComponentCellRenderer implements TableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value,
                     boolean isSelected, boolean hasFocus, int row, int column) {
        return (JComponent)value;
    }
}
/*
 * Criado em 28/06/2004
 */
package sequences.ui;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Augusto
 * @data 28/06/2004
 */
public class PanelText extends JScrollPane
{
	protected JTextArea txtArea;
	
	public PanelText ()
	{
		this(10, 10);
	}
	
	public PanelText (int sizeX, int sizeY)
	{
		txtArea = new JTextArea("", sizeX, sizeY);
		txtArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        setLineWrap(false);
        setEditable(false);
        this.setViewportView(txtArea);
        this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);		
	}
	
	public PanelText (String text)
	{
		this();
		setText(text);
	}
	
	public String getText()
	{
		return txtArea.getText();
	}

	public void setText(String text)
	{
		txtArea.setText(text);
	}

	public void setEditable(boolean editable)
	{
		txtArea.setEditable(editable);
	}
	
	public void setLineWrap(boolean lineWrap)
	{
		txtArea.setLineWrap(lineWrap);
	}

}

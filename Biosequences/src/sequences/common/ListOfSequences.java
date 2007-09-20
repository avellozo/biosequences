/*
 * Criado em 21/06/2004
 */
package sequences.common;

import javax.swing.DefaultListModel;

/**
 * @author Augusto
 * @data 21/06/2004
 */
public class ListOfSequences extends DefaultListModel
{
	public ListOfSequences(int minSize)
	{
		ensureCapacity(minSize);
	}

	public ListOfSequences()
	{
		super();
	}

	public Object getElementAt(int index)
	{
		return ((Sequence) super.getElementAt(index)).getName();
	}

	public Sequence getSequence(int index)
	{
		return (Sequence) super.getElementAt(index);
	}

	public void add(int index, Sequence element)
	{
		super.add(index, element);
	}

	public void addElement(Sequence obj)
	{
		super.addElement(obj);
	}

	public void insertElementAt(Sequence obj, int index)
	{
		super.insertElementAt(obj, index);
	}

	public Object set(int index, Sequence element)
	{
		return super.set(index, element);
	}

	public void setElementAt(Sequence obj, int index)
	{
		super.setElementAt(obj, index);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#add(int, java.lang.Object)
	 */
	public void add(int index, Object element)
	{
		throw new RuntimeException(
			"Só é possível colocar seqüências nesta lista.");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#addElement(java.lang.Object)
	 */
	public void addElement(Object obj)
	{
		throw new RuntimeException(
			"Só é possível colocar seqüências nesta lista.");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#insertElementAt(java.lang.Object, int)
	 */
	public void insertElementAt(Object obj, int index)
	{
		throw new RuntimeException(
			"Só é possível colocar seqüências nesta lista.");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element)
	{
		throw new RuntimeException(
			"Só é possível colocar seqüências nesta lista.");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultListModel#setElementAt(java.lang.Object, int)
	 */
	public void setElementAt(Object obj, int index)
	{
		throw new RuntimeException(
			"Só é possível colocar seqüências nesta lista.");
	}

}

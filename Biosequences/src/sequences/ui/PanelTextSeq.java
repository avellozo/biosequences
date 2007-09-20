/*
 * Criado em 02/06/2004
 */
package sequences.ui;

import sequences.common.Sequence;
import sequences.common.SequenceByteArray;

/**
 * @author Augusto
 * @data 02/06/2004
 */
public class PanelTextSeq extends PanelText
{
	Sequence seq;

	public PanelTextSeq(Sequence seq)
	{
		super(20,50);
        setLineWrap(true);
        setEditable(true);
		setSeq(seq);
	}

	public Sequence getSeq()
	{
		return seq;
	}

	public void setSeq(Sequence seq)
	{
		if (seq == null)
		{
			seq = new SequenceByteArray("", 0);
		}
		this.seq = seq;
		setText(new String(seq.getLetters()));
	}

	public void updateSeqFromText()
	{
		getSeq().setLetters(getText().getBytes());
	}
}


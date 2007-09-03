/*
 * Criado em 02/06/2004
 */

package sequences.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sequences.common.Sequence;

/**
 * @author Augusto @data 02/06/2004
 */
public class PanelSeq extends JPanel
{
	PanelTextSeq			textSeq;
	private JButton			btnChooseSeq;
	private JButton			btnUpdateSeq;
	private JLabel			lblNameSeq;
	private ChooseFileSeq	chooseFileSeq;
	private boolean			chooseAvailable, updateAvailable;

	/**
	 * 
	 */
	public PanelSeq(Sequence seq)
	{
		this(seq, true, true);
	}

	public PanelSeq(Sequence seq, boolean chooseAvailable,
			boolean updateAvailable)
	{
		this.chooseAvailable = chooseAvailable;
		this.updateAvailable = updateAvailable;
		init(seq);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public PanelSeq(Sequence seq, boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
		init(seq);
	}

	public void init(Sequence seq)
	{
		chooseFileSeq = new ChooseFileSeq();
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		textSeq = new PanelTextSeq(seq);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridheight = 4;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.add(textSeq, constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(10, 10, 0, 10);
		if (chooseAvailable)
		{
			btnChooseSeq = new JButton("Obter seqüência");
			btnChooseSeq.setToolTipText("Escolhe o arquivo Fasta da seqüência");
			btnChooseSeq.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					chooseSequence();
				}
			});
			this.add(btnChooseSeq, constraints);
		}
		if (updateAvailable)
		{
			btnUpdateSeq = new JButton("<html> Alterar <br> seqüência </html> ");
			btnUpdateSeq
					.setToolTipText("Altera a seqüência de acordo com as letras da caixa de texto.");
			btnUpdateSeq.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					updateSequence();
				}
			});
			constraints.gridy = 1;
			this.add(btnUpdateSeq, constraints);
		}
		lblNameSeq = new JLabel("<html> Seqüência: <br>"
				+ seq.getName() + "<br>" + seq.getLength()+ " letras</html>");
		constraints.gridy = 2;
		this.add(lblNameSeq, constraints);
	}

	protected void chooseSequence()
	{
		setSequence(chooseFileSeq.getSequence());
	}

	protected void updateSequence()
	{
		textSeq.updateSeqFromText();
	}
	
	public Sequence getSequence()
	{
		return textSeq.getSeq();
	}
	
	public void setSequence(Sequence seq)
	{
		if (seq != null)
		{
			textSeq.setSeq(seq);
			lblNameSeq.setText("<html> Seqüência: <br>"
					+ seq.getName() + "<br>" + seq.getLength()+ " letras</html>");
			this.repaint();
		}
	}
}

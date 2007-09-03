/*
 * Criado em 02/06/2004
 */

package sequences.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sequences.common.FragmentsScores;

/**
 * @author Augusto @data 02/06/2004
 */
public class PanelFragmentsScores extends JPanel
{
	private JLabel			lblNameFrags;
	PanelText			txtPropSeq1;
	PanelText			txtPropSeq2;
	PanelText			txtPropGraph;
	private JButton			btnChooseFrags;

	private ChooseFileFragments	chooseFileFragments;
	private boolean			chooseAvailable;
	FragmentsScores fragmentsScores;

	public PanelFragmentsScores(FragmentsScores fragmentsScores)
	{
		this(fragmentsScores, true);
	}

	public PanelFragmentsScores()
	{
		this(true);
	}

	public PanelFragmentsScores(FragmentsScores fragmentsScores, boolean chooseAvailable)
	{
		this(chooseAvailable);
		setFragments(fragmentsScores);
	}

	public PanelFragmentsScores(boolean chooseAvailable)
	{
		this.chooseAvailable = chooseAvailable;
		setPanelProperties();
		createComponents();
		addComponents();
	}
	
	private void setPanelProperties()
	{
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(600,600));
	}

	private void createComponents()
	{
		chooseFileFragments = new ChooseFileFragments();
		btnChooseFrags = new JButton("Obter scores dos fragmentos");
		btnChooseFrags.setToolTipText("Escolhe o arquivo com os scores dos alinhamentos dos fragmentos");
		btnChooseFrags.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				chooseFragmentsScores();
			}
		});
		lblNameFrags = new JLabel("");
		txtPropSeq1 = new PanelText(10,20);	
		txtPropSeq2 = new PanelText(10,20);	
		txtPropGraph = new PanelText(10,20);	
	}

	private void addComponents()
	{	
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		this.add(lblNameFrags, constraints);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.add(txtPropSeq1, constraints);
		constraints.gridy = 3;
		this.add(txtPropSeq2, constraints);
		constraints.gridy = 4;
		this.add(txtPropGraph, constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		if (chooseAvailable)
		{
			this.add(btnChooseFrags, constraints);
		}
	}

	protected void chooseFragmentsScores()
	{
		FragmentsScores frags = chooseFileFragments.getFragments();
		if (frags != null)
		{
			setFragments(frags);
		}
	}

	public FragmentsScores getFragmentsScores()
	{
		return fragmentsScores;
	}
	
	public void setFragments(FragmentsScores fragmentsScores)
	{
		this.fragmentsScores = fragmentsScores;
		if (fragmentsScores != null)
		{
			txtPropSeq1.setText(fragmentsScores.getPropSeq1().toString());
			txtPropSeq2.setText(fragmentsScores.getPropSeq2().toString());
			txtPropGraph.setText(fragmentsScores.getPropGraph().toString());
			lblNameFrags.setText(fragmentsScores.getNameSeq1() + " x " + fragmentsScores.getNameSeq2());
			this.repaint();
		}
		else
		{
			txtPropSeq1.setText("");
			txtPropSeq2.setText("");
			txtPropGraph.setText("");
			lblNameFrags.setText("");
		}
	}
}

/*
 * Created on 22/09/2004
 */
package sequences.ui;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import sequences.MaxPath;
import sequences.common.Sequence;
import sequences.editgraph.WeighterArcsSimpleSequences;

/**
 * @author Augusto
 */
public class FrameMaxPathMatrix extends javax.swing.JFrame
{
	private JTabbedPane	tabbedPane;
	MaxPath				maxPath;
	PanelMatrix panelLcs;
	PanelSeq panelSeq1, panelSeq2;
	Sequence seq1, seq2;

	public FrameMaxPathMatrix(MaxPath maxPathMatrix)
	{
		this.maxPath = maxPathMatrix;
		seq1 = ((WeighterArcsSimpleSequences) maxPathMatrix.getEditGraph()).getSeq1();
		seq2 = ((WeighterArcsSimpleSequences) maxPathMatrix.getEditGraph()).getSeq2();
		setFrameProperties();
		createComponents();
		addComponents();
	}

	private void addComponents()
	{
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Seqüência 1", null, panelSeq1, seq1.getName());
		tabbedPane.addTab("Seqüência 2", null, panelSeq2, seq2.getName());
		
		String tip =  seq1.getName()+ " x " + seq2.getName();
		tabbedPane.addTab("MaxPath", null, panelLcs, tip);

		this.pack();
	}

	private void createComponents()
	{
		tabbedPane = new JTabbedPane();
		panelSeq1 = new PanelSeq(seq1, false, false);
		panelSeq2 = new PanelSeq(seq2, false, false);
		panelLcs = new PanelMatrix(maxPath.getMatrix(), new MatrixIntCellRenderer(true, true));		
	}

	private void setFrameProperties()
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new java.awt.Dimension(400, 300));
		setTitle("MaxPath - tempo (ms):" + maxPath.getTime() + " - " + seq1.getName() + " x " + seq2.getName());
	}

	public MaxPath getMaxPath()
	{
		return maxPath;
	}

	public void setMaxPath(MaxPath maxPathMatrix)
	{
		this.maxPath = maxPathMatrix;
		createComponents();
		addComponents();
	}

}

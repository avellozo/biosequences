package sequences.ui;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import sequences.lcs.LCS;

public class FrameLCS extends javax.swing.JFrame
{
	private JTabbedPane	tabbedPane;
	LCS				lcs;
	PanelMatrix panelLcs;
	PanelSeq panelSeq1, panelSeq2;

	public FrameLCS(LCS lcs)
	{
		this.lcs = lcs;
		setFrameProperties();
		createComponents();
		addComponents();
	}

	private void addComponents()
	{
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Seq��ncia 1", null, panelSeq1, lcs.getSeq1().getName());
		tabbedPane.addTab("Seq��ncia 2", null, panelSeq2, lcs.getSeq2().getName());
		
		String tip = lcs.getSeq1().getName() + " x " + lcs.getSeq2().getName();
		tabbedPane.addTab("LCS", null, panelLcs, tip);

		this.pack();
	}

	private void createComponents()
	{
		tabbedPane = new JTabbedPane();
		panelSeq1 = new PanelSeq(lcs.getSeq1(), false, false);
		panelSeq2 = new PanelSeq(lcs.getSeq2(), false, false);
		panelLcs = new PanelMatrix(lcs.getMatrix(), new MatrixIntCellRenderer(true, true));		
	}

	private void setFrameProperties()
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new java.awt.Dimension(400, 300));
		setTitle("LCS - tempo (ms):" + lcs.getTime() + " - " + lcs.getSeq1().getName() + " x " + lcs.getSeq2().getName());
	}

	public LCS getLcs()
	{
		return lcs;
	}

	public void setAlcs(LCS lcs)
	{
		this.lcs = lcs;
		createComponents();
		addComponents();
	}

}

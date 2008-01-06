package sequences.ui;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import sequences.lcs.ALCS;

public class FrameALCS extends javax.swing.JFrame
{
	private JTabbedPane	tabbedPane;
	ALCS				alcs;
	PanelMatrix			dg, vg, ih, iv;
	PanelSeq			panelSeq1, panelSeq2;

	public FrameALCS(ALCS alcs)
	{
		this.alcs = alcs;
		setFrameProperties();
		createComponents();
		addComponents();
	}

	private void setFrameProperties()
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new java.awt.Dimension(400, 300));
		setTitle("ALCS - tempo (ms):" + alcs.getTime() + " - " + alcs.getSeq1().getName() + " x "
			+ alcs.getSeq2().getName());
	}

	private void createComponents()
	{
		tabbedPane = new JTabbedPane();
		panelSeq1 = new PanelSeq(alcs.getSeq1(), false, false);
		panelSeq2 = new PanelSeq(alcs.getSeq2(), false, false);
		MatrixIntCellRenderer renderer = new MatrixIntCellRenderer(true, false);
		dg = new PanelMatrix(alcs.getDg(), renderer);
		vg = new PanelMatrix(alcs.getVg(), renderer);
		ih = new PanelMatrix(alcs.getIh(), renderer);
		iv = new PanelMatrix(alcs.getIv(), renderer);
	}

	private void addComponents()
	{
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Seqüência 1", null, panelSeq1, alcs.getSeq1().getName());
		tabbedPane.addTab("Seqüência 2", null, panelSeq2, alcs.getSeq2().getName());

		String tip = alcs.getSeq1().getName() + " x " + alcs.getSeq2().getName();
		tabbedPane.addTab("DG", null, dg, tip);
		tabbedPane.addTab("VG", null, vg, tip);
		tabbedPane.addTab("ih", null, ih, tip);
		tabbedPane.addTab("iv", null, iv, tip);

		this.pack();
	}

	public ALCS getAlcs()
	{
		return alcs;
	}

	public void setAlcs(ALCS alcs)
	{
		this.alcs = alcs;
		createComponents();
		addComponents();
	}

}

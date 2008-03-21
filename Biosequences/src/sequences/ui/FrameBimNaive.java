package sequences.ui;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import sequences.bim.BimNaive;

public class FrameBimNaive extends javax.swing.JFrame
{
	private JTabbedPane	tabbedPane;
	BimNaive				bim;
	PanelMatrix panelBim;
	PanelSeq panelSeq1, panelSeq2;

	public FrameBimNaive(BimNaive bim)
	{
		this.bim = bim;
		setFrameProperties();
		createComponents();
		addComponents();
	}

	private void addComponents()
	{
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Seqüência 1", null, panelSeq1, bim.getSeq1().getName());
		tabbedPane.addTab("Seqüência 2", null, panelSeq2, bim.getSeq2().getName());
		
		String tip = bim.getSeq1().getName() + " x " + bim.getSeq2().getName();
		tabbedPane.addTab("BIM", null, panelBim, tip);

		this.pack();
	}

	private void createComponents()
	{
		tabbedPane = new JTabbedPane();
		panelSeq1 = new PanelSeq(bim.getSeq1(), false, false);
		panelSeq2 = new PanelSeq(bim.getSeq2(), false, false);
		panelBim = new PanelMatrix(bim.getMatrixValues(), new MatrixIntCellRenderer(true, true));		
	}

	private void setFrameProperties()
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new java.awt.Dimension(400, 300));
		setTitle("BIM Naive - tempo (ms):" + bim.getTime() + " - " + bim.getSeq1().getName() + " x " + bim.getSeq2().getName());
	}

	public BimNaive getBim()
	{
		return bim;
	}

	public void setBim(BimNaive bim)
	{
		this.bim = bim;
		createComponents();
		addComponents();
	}

}

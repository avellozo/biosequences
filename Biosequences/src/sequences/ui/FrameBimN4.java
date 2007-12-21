package sequences.ui;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import sequences.bim.n4.BimN4;
import sequences.matrix.MatrixIntEditGraph;

public class FrameBimN4 extends javax.swing.JFrame
{
	private JTabbedPane	tabbedPane;
	BimN4				bim;
	PanelMatrix panelBim;
	PanelMatrix panelGraph;
	PanelMatrix panelGraphInverted;
	PanelMatrix panelInversionsI;
	PanelMatrix panelInversionsJ;
	String tip, title;

	public FrameBimN4(BimN4 bim, String tip, String title)
	{
		this.bim = bim;
		this.tip = tip;
		this.title = title;
		setFrameProperties();
		createComponents();
		addComponents();
	}

	private void addComponents()
	{
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("BIM="+bim.getTotalBimValue(), null, panelBim, 
				tip);
		tabbedPane.addTab("Pesos do grafo", null, panelGraph, tip);
		tabbedPane.addTab("Pesos do grafo invertido", null, panelGraphInverted, tip);
		tabbedPane.addTab("Linhas dos inícios das inversões", null, panelInversionsI, tip);
		tabbedPane.addTab("Colunas dos inícios das inversões", null, panelInversionsJ, tip);
		
		this.pack();
	}

	private void createComponents()
	{
		tabbedPane = new JTabbedPane();
		MatrixIntCellRenderer renderer = new MatrixIntCellRenderer(true, false);
		panelGraph = new PanelMatrix(new MatrixIntEditGraph(bim.getEditGraph()), renderer);
		panelGraphInverted = new PanelMatrix(new MatrixIntEditGraph(bim.getEditGraphInverted()), renderer);
		panelInversionsI = new PanelMatrix(bim.getInversionsI(), renderer);
		panelInversionsJ = new PanelMatrix(bim.getInversionsJ(), renderer);
		renderer = new MatrixIntCellRenderer(true, true);
		panelBim = new PanelMatrix(bim.getMatrixBim(), renderer);		
	}

	private void setFrameProperties()
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new java.awt.Dimension(400, 300));
		setTitle(title);
	}

	public BimN4 getBim()
	{
		return bim;
	}

	public void setBim(BimN4 bim)
	{
		this.bim = bim;
		createComponents();
		addComponents();
	}

}

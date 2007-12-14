package sequences.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import sequences.bim.n3lgn.BimN3lgn;
import sequences.bim.n4.BimN4;
import sequences.bim.sN4.BimSN4;
import sequences.common.FragmentsScores;
import sequences.editgraph.EGSparseWithDiagonals;
import sequences.editgraph.EditGraphOld;

public class FrameBimN4Parameters extends JFrame
{	
	PanelFragmentsScores panelFrags;
	JLabel lblMinValue;
	JSpinner spinMinValue;
	JLabel lblInversionPenalty;
	JSpinner spinInversionPenalty;
	JButton btnBuildBimN4;
	JButton btnBuildBimN4Sparse;
	JButton btnBuildBimN3l;
	JButton btnCancel;
	
	public FrameBimN4Parameters()
	{
		setFrameProperties();
		createComponents();
		addComponents();
	}
	
	private void setFrameProperties()
	{
		this.setTitle("Parâmetros para a geração do Bim");
		this.getContentPane().setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	private void createComponents()
	{
		panelFrags = new PanelFragmentsScores();
		lblMinValue = new JLabel("Pontuação mínima");
		spinMinValue = new JSpinner(new SpinnerNumberModel(45, 0, 1000, 1));
		lblInversionPenalty  = new JLabel("Penalidade para inversão");;
		spinInversionPenalty = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));
		
		btnBuildBimN4= new JButton("Gera BIM N4");
		btnBuildBimN4.setToolTipText("Gera a matriz BIM através do método N4, utilizando os parâmetros fornecidos");
		btnBuildBimN4.setEnabled(true);
		btnBuildBimN4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				buildBimN4();
			}
		});	
		btnBuildBimN4Sparse= new JButton("Gera BIM N4 esparso");
		btnBuildBimN4Sparse.setToolTipText("Gera a matriz BIM através do método N4 esparso, utilizando os parâmetros fornecidos");
		btnBuildBimN4Sparse.setEnabled(true);
		btnBuildBimN4Sparse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				buildBimN4Sparse();
			}
		});	
		btnBuildBimN3l= new JButton("Gera BIM N3 log n");
		btnBuildBimN3l.setToolTipText("Gera a matriz BIM através do método N3 log n, utilizando os parâmetros fornecidos");
		btnBuildBimN3l.setEnabled(true);
		btnBuildBimN3l.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				buildBimN3l();
			}

		});	
		
		btnCancel= new JButton("Fecha");
		btnCancel.setToolTipText("Fecha esta Janela");
		btnCancel.setEnabled(true);
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				close();
			}
		});			
	}

	private void addComponents()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.getContentPane().add(panelFrags, constraints);
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		this.getContentPane().add(lblMinValue, constraints);
		constraints.gridy = 1;
		this.getContentPane().add(spinMinValue, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.getContentPane().add(lblInversionPenalty, constraints);
		constraints.gridy = 1;
		this.getContentPane().add(spinInversionPenalty, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(10, 10, 0, 10);
		this.getContentPane().add(btnBuildBimN4, constraints);
		constraints.gridx = 1;
		this.getContentPane().add(btnBuildBimN4Sparse, constraints);
		constraints.gridx = 2;
		this.getContentPane().add(btnBuildBimN3l, constraints);
		constraints.gridx = 3;
		this.getContentPane().add(btnCancel, constraints);
		
		this.pack();
	}

	protected void close()
	{
		this.dispose();
		
	}

	protected void buildBimN4()
	{
		FragmentsScores fragments = panelFrags.getFragmentsScores();
		if (fragments != null)
		{
			int threshold = ((Number)spinMinValue.getValue()).intValue();
			int inversionPenalty = ((Number)spinInversionPenalty.getValue()).intValue();
			EditGraphOld eg = new EGSparseWithDiagonals(fragments.getDirectScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, false, null);
			EditGraphOld egInverted = new EGSparseWithDiagonals(fragments.getInvertedScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, true, null);
			BimN4 bim = new BimN4(eg, egInverted, inversionPenalty);
			String nameSeq1 = fragments.getNameSeq1();
			String nameSeq2 = fragments.getNameSeq2();
			String title = "BIM N4 - tempo (ms):" + bim.getTime() + " - " + nameSeq1 + " x " + nameSeq2;
			String tip = nameSeq1 + " x " + nameSeq2  + " com valor mínimo:" + threshold + 
			" e penalidade:" + inversionPenalty;
			(new FrameBimN4(bim, tip, title)).show();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Informe antes os scores do alinhamento dos fragmentos.");
		}
	}

	protected void buildBimN4Sparse()
	{
		FragmentsScores fragments = panelFrags.getFragmentsScores();
		if (fragments != null)
		{
			int threshold = ((Number)spinMinValue.getValue()).intValue();
			int inversionPenalty = ((Number)spinInversionPenalty.getValue()).intValue();
			EditGraphOld eg = new EGSparseWithDiagonals(fragments.getDirectScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, false, null);
			EditGraphOld egInverted = new EGSparseWithDiagonals(fragments.getInvertedScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, true, null);
			BimN4 bim = new BimSN4(eg, egInverted, inversionPenalty);
			String nameSeq1 = fragments.getNameSeq1();
			String nameSeq2 = fragments.getNameSeq2();
			String title = "BIM N4 - tempo (ms):" + bim.getTime() + " - " + nameSeq1 + " x " + nameSeq2;
			String tip = nameSeq1 + " x " + nameSeq2  + " com valor mínimo:" + threshold + 
			" e penalidade:" + inversionPenalty;
			(new FrameBimN4(bim, tip, title)).show();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Informe antes os scores do alinhamento dos fragmentos.");
		}
	}
	
	protected void buildBimN3l()
	{
		FragmentsScores fragments = panelFrags.getFragmentsScores();
		if (fragments != null)
		{
			int threshold = ((Number)spinMinValue.getValue()).intValue();
			int inversionPenalty = ((Number)spinInversionPenalty.getValue()).intValue();
			EditGraphOld eg = new EGSparseWithDiagonals(fragments.getDirectScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, false, null);
			EditGraphOld egInverted = new EGSparseWithDiagonals(fragments.getInvertedScores(), threshold, 1, -1, fragments.getNumFragsSeq1()+1, fragments.getNumFragsSeq2()+1, true, null);
			BimN4 bim = new BimN3lgn(eg, egInverted, inversionPenalty);
			String nameSeq1 = fragments.getNameSeq1();
			String nameSeq2 = fragments.getNameSeq2();
			String title = "BIM N3 log n - tempo (ms):" + bim.getTime() + " - " + nameSeq1 + " x " + nameSeq2;
			String tip = nameSeq1 + " x " + nameSeq2  + " com valor mínimo:" + threshold + 
			" e penalidade:" + inversionPenalty;
			(new FrameBimN4(bim, tip, title)).show();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Informe antes os scores do alinhamento dos fragmentos.");
		}
	}
}

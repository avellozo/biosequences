/*
 * Criado em 19/06/2004
 */

package sequences.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sequences.common.FileFastaSequence;
import sequences.common.ListOfSequences;
import sequences.common.Sequence;
import sequences.lcs.ALCS;

/**
 * @author Augusto
 * @data 19/06/2004
 */
public class FrameMain extends JFrame implements ListSelectionListener
{
	private JButton				btnChooseSeq;
	private JButton				btnAlignClassic;
	//	private JButton btnAlcs, btnLcs, btnBimNaive;
	//	private JButton				btnBuildFileBimN4, btnBuildBimN4, btnBimN3l;
	//	private JButton				btnBuildMaxPathMatrix, btnBuildMaxPathMatrixNaive;
	private ChooseFileSeq		chooseFileSeq	= new ChooseFileSeq();
	private ChooseFileFragments	chooseFileGraph	= new ChooseFileFragments();
	private JPanel				panelRight;
	private PanelSeq			panelSeq		= null;
	private ListOfSequences		sequences		= new ListOfSequences(2);
	private JList				sequenceList;
	private JScrollPane			scrollPaneSequences;
	private JLabel				lblListHeader;
	private PanelDebug			panelDebug		= new PanelDebug();

	public FrameMain(String[] args)
	{
		setFrameProperties();
		createComponents(args);
		addComponents();
	}

	private void setFrameProperties()
	{
		this.setTitle("Sequence alignment");
		this.getContentPane().setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	private void createComponents(String[] args)
	{
		panelRight = new JPanel(new BorderLayout());
		lblListHeader = new JLabel("Sequences list:");
		sequenceList = new JList(sequences);
		sequenceList.addListSelectionListener(this);
		sequenceList.setPreferredSize(new Dimension(100, sequenceList.getHeight()));
		scrollPaneSequences = new JScrollPane(sequenceList);
		for (int i = 0; i < args.length; i++)
		{
			try
			{
				sequences.addElement((new FileFastaSequence(args[i])).getSequence());
			}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(this, "File not found.");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(this, "Error in file:" + e.getMessage());
				e.printStackTrace();
			}
		}

		btnChooseSeq = new JButton("Add sequence");
		btnChooseSeq.setToolTipText("Choose Fasta file");
		btnChooseSeq.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				chooseSequence();
			}
		});

		btnAlignClassic = new JButton("Classic Alignment");
		btnAlignClassic.setToolTipText("Build one alignment of 2 sequences using the method classic.");
		btnAlignClassic.setEnabled(false);
		btnAlignClassic.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				buildAlignClassic();
			}
		});

		/*		btnAlcs = new JButton("Gera ALCS");
				btnAlcs.setToolTipText("Gera o ALCS de 2 seqüências selecionadas");
				btnAlcs.setEnabled(false);
				btnAlcs.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildAlcs();
					}
				});


				btnLcs = new JButton("Gera LCS");
				btnLcs.setToolTipText("Gera o LCS de 2 seqüências selecionadas");
				btnLcs.setEnabled(false);
				btnLcs.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildLcs();
					}
				});

				btnBimNaive = new JButton("Gera BIM Naive");
				btnBimNaive.setToolTipText("Gera a matriz BIM de 2 seqüências selecionadas utilizando um método ingênuo");
				btnBimNaive.setEnabled(false);
				btnBimNaive.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildBimNaive();
					}
				});

				btnBimN3l = new JButton("Gera BIM N3Log n");
				btnBimN3l.setToolTipText("Gera a matriz BIM de 2 seqüências selecionadas utilizando um método n3 log n");
				btnBimN3l.setEnabled(false);
				btnBimN3l.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildBimN3l();
					}

				});

				
				 * // btnBuildFileBimN4= new JButton(" <html> Gera arquivo do grafo <br>
				 * bipartido para BIM N4 </html> "); //
				 * btnBuildFileBimN4.setToolTipText("Gera um arquivo com as
				 * especificações do grafo bipartido para ser utilizado na geração da
				 * matriz BIM"); // btnBuildFileBimN4.setEnabled(false); //
				 * btnBuildFileBimN4.addActionListener(new ActionListener() // { //
				 * public void actionPerformed(ActionEvent evt) // { //
				 * buildFileBimN4(); // } // }); //
				 
				btnBuildBimN4 = new JButton("Gera BIM N4");
				btnBuildBimN4.setToolTipText("Gera a matriz BIM através do método N4, utilizando um arquivo com as especificações do grafo bipartido");
				btnBuildBimN4.setEnabled(false);
				btnBuildBimN4.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildBimN4();
					}
				});

				btnBuildMaxPathMatrix = new JButton("Gera Matriz de caminhos máximos");
				btnBuildMaxPathMatrix.setToolTipText("Gera a matriz de caminhos máximos dos sufixos da seq1 contra os prefixos da seq2");
				btnBuildMaxPathMatrix.setEnabled(false);
				btnBuildMaxPathMatrix.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildMaxPathMatrixJeanette();
					}
				});

				btnBuildMaxPathMatrixNaive = new JButton("Gera Matriz de caminhos máximos ingênuo");
				btnBuildMaxPathMatrixNaive.setToolTipText("Gera a matriz de caminhos máximos com um método ingênuo dos sufixos da seq1 contra os prefixos da seq2");
				btnBuildMaxPathMatrixNaive.setEnabled(false);
				btnBuildMaxPathMatrixNaive.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						buildMaxPathMatrixNaive();
					}
				});
		*/}

	private void addComponents()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridheight = 20;
		//		constraints.gridwidth = 4;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.getContentPane().add(panelRight, constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		this.getContentPane().add(lblListHeader, constraints);
		constraints.gridy = 1;
		this.getContentPane().add(scrollPaneSequences, constraints);
		constraints.gridy = 2;
		constraints.insets = new Insets(10, 10, 0, 10);
		this.getContentPane().add(btnChooseSeq, constraints);
		constraints.gridy = 3;
		this.getContentPane().add(btnAlignClassic, constraints);
		constraints.gridy = 4;
		/*		this.getContentPane().add(btnLcs, constraints);
				constraints.gridy = 4;
				this.getContentPane().add(btnAlcs, constraints);
				constraints.gridy = 5;
				this.getContentPane().add(btnBimNaive, constraints);
				//		constraints.gridy = 6;
				//		this.getContentPane().add(btnBuildFileBimN4, constraints);
				constraints.gridy = 7;
				this.getContentPane().add(btnBuildBimN4, constraints);
				constraints.gridy = 8;
				this.getContentPane().add(btnBimN3l, constraints);
				constraints.gridy = 9;
				this.getContentPane().add(btnBuildMaxPathMatrix, constraints);
				constraints.gridy = 10;
				this.getContentPane().add(btnBuildMaxPathMatrixNaive, constraints);
				constraints.gridy = 11;*/
		this.getContentPane().add(panelDebug, constraints);
		this.pack();
	}

	/*	protected void buildMaxPathMatrixJeanette()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				(new FrameMaxPathMatrix(new MaxPathJeanette(new ArcFactorySimpleSequences(seq1, seq2, 1, -1, 0, false),
					panelDebug.getPrintStream()))).show();
				System.gc();
			}
		}

		protected void buildMaxPathMatrixNaive()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				(new FrameMaxPathMatrix(new MaxPathNaive(new ArcFactorySimpleSequences(seq1, seq2, 1, -1, 0, false),
					panelDebug.getPrintStream()))).show();
				System.gc();
			}
		}

		protected void buildBimN4()
		{
			(new FrameBimN4Parameters()).show();
		}

		protected void buildFileBimN4()
		{
		}

		protected void buildBimNaive()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				(new FrameBimNaive(new BimNaive(seq1, seq2))).show();
			}
		}

		protected void buildBimN3l()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				EditGraphOld editGraph = new ArcFactorySimpleSequences(seq1, seq2, 1, -1, 0, false);
				EditGraphOld editGraphInverted = new ArcFactorySimpleSequences(seq1, seq2, 1, -1, 0, true);
				BimN4 bim = new BimN3lgn(editGraph, editGraphInverted, 2);
				String nameSeq1 = seq1.getName();
				String nameSeq2 = seq2.getName();
				String title = "BIM N3 log n - tempo (ms):" + bim.getTime() + " - " + nameSeq1 + " x " + nameSeq2;
				String tip = nameSeq1 + " x " + nameSeq2 + " e penalidade:" + 2;
				(new FrameBimN4(bim, tip, title)).show();
			}
		}

		protected void buildLcs()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				(new FrameLCS(new LCS(seq1, seq2))).show();
			}
		}

		protected void buildAlcs()
		{
			int[] selectedSequences = sequenceList.getSelectedIndices();
			if (selectedSequences.length != 2)
			{
				JOptionPane.showMessageDialog(this, "Selecione 2 seqüências");
			}
			else
			{
				Sequence seq1 = sequences.getSequence(selectedSequences[0]);
				Sequence seq2 = sequences.getSequence(selectedSequences[1]);
				(new FrameALCS(new ALCS(seq1, seq2))).show();
			}
		}

	*/

	protected void buildAlignClassic()
	{
		int[] selectedSequences = sequenceList.getSelectedIndices();
		if (selectedSequences.length != 2)
		{
			JOptionPane.showMessageDialog(this, "Please, select 2 sequences");
		}
		else
		{
			Sequence seq1 = sequences.getSequence(selectedSequences[0]);
			Sequence seq2 = sequences.getSequence(selectedSequences[1]);
			(new FrameALCS(new ALCS(seq1, seq2))).show();
		}
	}

	protected void chooseSequence()
	{
		Sequence seq = chooseFileSeq.getSequence();
		if (seq != null)
		{
			sequences.addElement(seq);
		}
	}

	public static void main(String[] args)
	{
		(new FrameMain(args)).setVisible(true);
	}

	protected void showSequence(Sequence seq)
	{
		if (panelSeq == null)
		{
			panelSeq = new PanelSeq(seq, false, true);
			panelRight.add(panelSeq);
			this.pack();
		}
		else
		{
			panelSeq.setSequence(seq);
			this.pack();
		}
	}

	public void valueChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			showSequence(sequences.getSequence(sequenceList.getMinSelectionIndex()));
		}
		if (sequenceList.getSelectedIndices().length != 2)
		{
			btnAlignClassic.setEnabled(false);
			/*			btnAlcs.setEnabled(false);
						btnLcs.setEnabled(false);
						btnBimNaive.setEnabled(false);
						btnBimN3l.setEnabled(false);
						btnBuildMaxPathMatrix.setEnabled(false);
						btnBuildMaxPathMatrixNaive.setEnabled(false);
			*/}
		else
		{
			btnAlignClassic.setEnabled(true);
			/*			btnAlcs.setEnabled(true);
						btnLcs.setEnabled(true);
						btnBimNaive.setEnabled(true);
						btnBimN3l.setEnabled(true);
						btnBuildMaxPathMatrix.setEnabled(true);
						btnBuildMaxPathMatrixNaive.setEnabled(true);
			*/}
	}
}
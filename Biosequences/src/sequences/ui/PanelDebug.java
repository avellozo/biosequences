/*
 * Created on 29/09/2004
 */
package sequences.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 * @author Augusto
 */
public class PanelDebug extends javax.swing.JPanel
{
	private ButtonGroup		group				= new ButtonGroup();
	private JRadioButton	radioNoDebug		= new JRadioButton("No debug",
													true);
	private JRadioButton	radioDebugToConsole	= new JRadioButton(
													"debug to console", false);
	private JRadioButton	radioDebugToFile	= new JRadioButton(
													"debug to file", false);
	private JFileChooser	chooseFileDebug		= new JFileChooser();

	public PanelDebug()
	{
		super();
		chooseFileDebug.setDialogType(JFileChooser.SAVE_DIALOG);
		chooseFileDebug.setAcceptAllFileFilterUsed(true);
		chooseFileDebug.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooseFileDebug.setMultiSelectionEnabled(false);
		chooseFileDebug.setCurrentDirectory(new File("."));

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(radioNoDebug, constraints);
		constraints.gridy = 1;
		this.add(radioDebugToConsole, constraints);
		constraints.gridy = 2;
		this.add(radioDebugToFile, constraints);
		group.add(radioNoDebug);
		group.add(radioDebugToConsole);
		group.add(radioDebugToFile);
	}

	public PrintStream getPrintStream()
	{
		if (radioNoDebug.isSelected())
		{
			return null;
		}
		else
		{
			if (radioDebugToConsole.isSelected())
			{
				return System.out;
			}
			else
			{
				int retval = chooseFileDebug.showSaveDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION)
				{
					File file = chooseFileDebug.getSelectedFile();
					if (file != null)
					{
						if (file.isDirectory())
						{
							JOptionPane
								.showMessageDialog(null,
									"Você escolheu um diretório, por favor escolha um arquivo.");
						}
						else
						{
							try
							{
								return new PrintStream(new FileOutputStream(
									file));
							}
							catch (FileNotFoundException e)
							{
								JOptionPane.showMessageDialog(null,
									"Arquivo não encontrado.");
								e.printStackTrace();
							}
						}
					}
				}
				else
				{
					if (retval != JFileChooser.CANCEL_OPTION)
					{
						if (retval == JFileChooser.ERROR_OPTION)
						{
							JOptionPane
								.showMessageDialog(null,
									"Ocorreu um erro. Nenhum arquivo foi escolhido.");
						}
						else
						{
							JOptionPane
								.showMessageDialog(null,
									"Ocorreu um erro desconhecido. Nenhum arquivo foi escolhido.");
						}
					}
				}
				return null;
			}
		}
	}
}
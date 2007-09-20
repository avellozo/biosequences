/*
 * Criado em 03/06/2004
 */
package sequences.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import sequences.common.FragmentsScores;

/**
 * @author Augusto
 * @data 03/06/2004
 */
public class ChooseFileFragments
{
	
	private JFileChooser	chooseFileDialog;

	public ChooseFileFragments()
	{
		super();
		chooseFileDialog = new JFileChooser();
		chooseFileDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		chooseFileDialog.setAcceptAllFileFilterUsed(true);
		chooseFileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooseFileDialog.setMultiSelectionEnabled(false);
		chooseFileDialog.setCurrentDirectory(new File("."));
	}
	
	public FragmentsScores getFragments()
	{
		int retval = chooseFileDialog.showOpenDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION)
		{
			File file = chooseFileDialog.getSelectedFile();
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
						return createFragmentsScores(file);
					}
					catch (FileNotFoundException e)
					{
						JOptionPane.showMessageDialog(null, 
						"Arquivo não encontrado.");
						e.printStackTrace();
					}
					catch (IOException e)
					{
						JOptionPane.showMessageDialog(null, 
						"Erro ao ler o arquivo:" + file.getPath());
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
					JOptionPane.showMessageDialog(null, 
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

	protected FragmentsScores createFragmentsScores(File file)
	throws FileNotFoundException, IOException
	{
		return new FragmentsScores(file);
	}


}

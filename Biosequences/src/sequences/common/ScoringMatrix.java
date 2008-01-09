/*
 * Created on 08/01/2008
 */
package sequences.common;

import jaligner.ui.filechooser.NamedInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Reference: JALigner
 * 
 * @author Augusto F. Vellozo
 */
public class ScoringMatrix
{

	/**
	 * The starter character of a comment line.
	 */
	private static final char	COMMENT_STARTER	= '#';

	/**
	 * The size of the scoring matrix. It is the number of the characters in the ASCII table. It is more than the 20
	 * amino acids just to save the processing time of the mapping.
	 */
	private static final int	SIZE			= 127;

	/**
	 * Matrix id (or name)
	 */
	private String				id				= null;

	/**
	 * Scores
	 */
	private int[][]				scores			= null;

	public ScoringMatrix(NamedInputStream nis) throws IOException
	{
		scores = new int[SIZE][SIZE];
		id = nis.getName();

		char[] acids = new char[SIZE];

		// Initialize the acids array to null values (ascii = 0)
		for (int i = 0; i < SIZE; i++)
		{
			acids[i] = 0;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(nis.getInputStream()));
		String line;

		// Skip the comment lines
		while ((line = reader.readLine()) != null && line.trim().charAt(0) == COMMENT_STARTER)
			;

		// Read the headers line (the letters of the acids)
		StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(line.trim());
		for (int j = 0; tokenizer.hasMoreTokens(); j++)
		{
			acids[j] = tokenizer.nextToken().charAt(0);
		}

		// Read the scores
		while ((line = reader.readLine()) != null)
		{
			tokenizer = new StringTokenizer(line.trim());
			char acid = tokenizer.nextToken().charAt(0);
			for (int i = 0; i < SIZE; i++)
			{
				if (acids[i] != 0)
				{
					scores[acid][acids[i]] = Integer.parseInt(tokenizer.nextToken());
				}
			}
		}
	}

	public ScoringMatrix(String id, int[][] scores)
	{
		this.id = id;
		this.scores = scores;
	}

	public String getId()
	{
		return this.id;
	}

	public int[][] getScores()
	{
		return this.scores;
	}

	public int getScore(byte a, byte b)
	{
		return this.scores[a][b];
	}

	public int getScore(char letter, char letter2)
	{
		return getScore((byte) letter, (byte) letter2);
	}

}

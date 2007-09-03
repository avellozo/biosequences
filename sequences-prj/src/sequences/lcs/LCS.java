/*
 * Criado em 18/06/2004
 */

package sequences.lcs;

import sequences.common.Sequence;

/**
 * @author Augusto @data 18/06/2004
 */
public class LCS
{
	Sequence	seq1, seq2;
	int[][]		matrix;
	long time;

	public LCS(Sequence linhas, Sequence colunas)
	{
		time= System.currentTimeMillis();
		seq1 = linhas;
		seq2 = colunas;
		int na = linhas.getLength();
		int nb = colunas.getLength();
		matrix = new int[na + 1][nb + 1];
		int i, j;
		for (i = 0; i <= nb; i++)
		{
			matrix[0][i] = 0;
		}
		for (i = 1; i <= na; i++)
		{
			matrix[i][0] = 0;
		}
		for (i = 1; i <= na; i++)
		{
			for (j = 1; j <= nb; j++)
			{
				matrix[i][j] = max(matrix[i - 1][j - 1] + f(i, j),
						matrix[i][j - 1], matrix[i - 1][j]);
			}
		}
		time = System.currentTimeMillis() - time;
	}

	private int max(int x, int y, int z)
	{
		return Math.max(x, Math.max(y, z));
	}

	private int f(int i, int j)
	{
		return (seq1.getLetter(i) == seq2.getLetter(j)) ? 1 : 0;
	}

	public int[][] getMatrix()
	{
		return matrix;
	}

	public int getLCSValue()
	{
		return matrix[seq1.getLength()][seq2.getLength()];
	}

	public Sequence getSeq1()
	{
		return seq1;
	}

	public void setSeq1(Sequence seq1)
	{
		this.seq1 = seq1;
	}

	public Sequence getSeq2()
	{
		return seq2;
	}

	public void setSeq2(Sequence seq2)
	{
		this.seq2 = seq2;
	}

	public long getTime()
	{
		return time;
	}

}

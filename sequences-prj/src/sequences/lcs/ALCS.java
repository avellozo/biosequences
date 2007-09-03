/*
 * Criado em 22/05/2004
 */

package sequences.lcs;

import java.lang.Math;

import sequences.common.Sequence;

/**
 * @author Augusto @data 22/05/2004
 */
public class ALCS
{
	int[][]	dg;
	int[]	vg;
	Sequence	seq1, seq2;
	int[][]		ih;
	int[][]		iv;
	long time;

	public ALCS(Sequence a, Sequence b)
	{
		time = System.currentTimeMillis();
		seq1 = a;
		seq2 = b;
		int na = a.getLength();
		int nb = b.getLength();
		ih = new int[na + 1][nb + 1];
		iv = new int[na + 1][nb + 1];
		dg = new int[nb + 1][na + 1];
		vg = new int[nb + 1];
		int i, j;
		byte ai, bj;
		for (j = 0; j <= nb; j++)
		{
			ih[0][j] = j;
		}
		for (i = 0; i <= na; i++)
		{
			iv[i][0] = 0;
		}
		for (i = 1; i <= na; i++)
		{
			ai = a.getLetter(i);
			for (j = 1; j <= nb; j++)
			{
				bj = b.getLetter(j);
				if (ai != bj)
				{
					ih[i][j] = Math.max(iv[i][j - 1], ih[i - 1][j]);
					iv[i][j] = Math.min(iv[i][j - 1], ih[i - 1][j]);
				}
				else
				{
					ih[i][j] = iv[i][j - 1];
					iv[i][j] = ih[i - 1][j];
				}
			}
		}
		for (j = 0; j <= nb; j++)
		{
			vg[j] = -1;
		}
		dg[0][0] = 0;
		int k = 1;
		for (j = 1; j <= nb; j++)
		{
			if (ih[na][j] == 0)
			{
				dg[0][k] = j;
				k++;
			}
			else
			{
				vg[ih[na][j]] = j;
			}
		}
		for (; k <= na; k++)
		{
			dg[0][k] = -1;
		}
		boolean insertVg;
		for (i = 1; i <= nb; i++)
		{
			k = 1;
			insertVg = (vg[i] != -1);
			for (j = 0; (j <= na) && (k <= na); j++)
			{
				if (insertVg && ((dg[i - 1][k] > vg[i]) || (dg[i - 1][k] == -1)))
				{
					dg[i][j] = vg[i];
					insertVg = false;
				}
				else
				{
					dg[i][j] = dg[i - 1][k];
					k++;
				}
			}
			if (j == na)
			{
				if (insertVg)
				{
					dg[i][j] = vg[i];
				}
				else
				{
					dg[i][j] = -1;
				}
			}
		}
		time = System.currentTimeMillis() - time;
	}

	public Sequence getSeq1()
	{
		return seq1;
	}

	public void setSeq1(Sequence a)
	{
		this.seq1 = a;
	}

	public Sequence getSeq2()
	{
		return seq2;
	}

	public void setSeq2(Sequence b)
	{
		this.seq2 = b;
	}

	public int[][] getDg()
	{
		return dg;
	}

	public void setDg(int[][] dg)
	{
		this.dg = dg;
	}

	public int[] getVg()
	{
		return vg;
	}

	public void setVg(int[] vg)
	{
		this.vg = vg;
	}

	public int[][] getIh()
	{
		return ih;
	}

	public void setIh(int[][] ih)
	{
		this.ih = ih;
	}

	public int[][] getIv()
	{
		return iv;
	}

	public void setIv(int[][] iv)
	{
		this.iv = iv;
	}
	public long getTime()
	{
		return time;
	}

}

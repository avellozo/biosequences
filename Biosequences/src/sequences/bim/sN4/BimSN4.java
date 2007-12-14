/*
 * Criado em 03/07/2004
 */

package sequences.bim.sN4;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import sequences.bim.n4.BimN4;
import sequences.editgraph.Arc;
import sequences.editgraph.EditGraphOld;
import sequences.editgraph.EGArcOld;
import sequences.editgraph.EGVertexBasic;
import sequences.editgraph.Vertex;
import sequences.matrix.MatrixInt;
import sequences.matrix.MatrixIntDP;
import sequences.matrix.MatrixIntPrimitive;
import sequences.util.TreeMap;

/**
 * @author Augusto
 * @data 03/07/2004
 */

public class BimSN4 extends BimN4
{
	int				sLength;
	int				tLength;
	RankCollection	r;
	List			arcs, invertedArcs;
	ListIterator	lstItrInvArcs;
	Arc			currentInvArc	= null;

	public BimSN4(EditGraphOld editGraph, EditGraphOld editGraphInverted,
			int inversionPenalty, boolean local)
	{
		super(editGraph, editGraphInverted, inversionPenalty, local);
	}

	protected void buildBim()
	{
		sLength = editGraph.getRowsQtty();
		tLength = editGraph.getColsQtty();
		arcs = getEditGraph().getPositiveArcs();
		invertedArcs = getEditGraphInverted().getPositiveArcs();
		MatchCollection m = buildMatches();
		r = buildRanks(m, false);
		//		matrixBim = buildMatrix(r);
	}

	private MatchCollection buildMatches()
	{
		int i, j;
		MatchCollection m = new MatchCollection();
		int iMax = sLength;
		int jMax = tLength;
		Iterator arcsIterator = this.arcs.iterator();
		Iterator invertedArcsIterator = this.invertedArcs.iterator();
		Arc arc1 = null, arc2 = null;

		if (arcsIterator.hasNext())
		{
			arc1 = (Arc) arcsIterator.next();
		}
		if (invertedArcsIterator.hasNext())
		{
			arc2 = (Arc) invertedArcsIterator.next();
		}
		while ((arc1 != null) || (arc2 != null))
		{
			if ((arc2 != null)
				&& ((arc1 == null) || (arc2.getEndVertex().compareTo(
					arc1.getEndVertex()) <= 0)))
			{
				m.addLast(new Match(arc2.getEndVertex().getRow(), arc2
					.getEndVertex().getCol(), true, arc2.getWeight()));
				if (invertedArcsIterator.hasNext())
				{
					arc2 = (Arc) invertedArcsIterator.next();
				}
				else
				{
					arc2 = null;
				}
			}
			else
			{
				m.addLast(new Match(arc1.getEndVertex().getRow(), arc1
					.getEndVertex().getCol(), false, arc1.getWeight()));
				if (arcsIterator.hasNext())
				{
					arc1 = (Arc) arcsIterator.next();
				}
				else
				{
					arc1 = null;
				}
			}
		}
		m.setFirst(m.getHead().next);
		return m;
	}

	private RankCollection buildRanks(MatchCollection m, boolean lcsInverted)
	{
		RankCollection r = new RankCollection();
		Match match;
		int rankNumber, i, j, i2, j2;
		int lastI = -1;
		int rank;
		MatchInvertedCollection m2 = new MatchInvertedCollection();
		RankCollection b;
		for (MatchLinkedListEntry entryIteratorM = m.getFirst(); entryIteratorM != m
			.getHead(); entryIteratorM = entryIteratorM.next)
		{
			match = entryIteratorM.match;
			i = match.getI();
			j = match.getJ();
			if (!match.isInverted())
			{
				rankNumber = r.getRankNumber(i - 1, j - 1) + match.getWeight();
				rank = r.getRankNumber(i, j);
				if (rankNumber > rank)
				{
					r.update(rankNumber, i, j);
				}
			}
			else
			{
				if (lastI != i)
				{
					m2.addRowInverted(entryIteratorM);
					lastI = i;
				}
				m2.setFirst(m2.getFirstEntry(j));
				b = buildRanks(m2, true);
				for (MatchLinkedListEntry entryIteratorM2 = m2.getFirst(); entryIteratorM2 != m2
					.getHead(); entryIteratorM2 = entryIteratorM2.next)
				{
					match = entryIteratorM2.match.reflectionRotationInverted();
					i2 = match.getI();
					j2 = match.getJ();
					rankNumber = b.getRankNumber(entryIteratorM2.match.getI(),
						entryIteratorM2.match.getJ())
						+ r.getRankNumber(i2 - 1, j - 1)
						- getInversionPenalty();
					rank = r.getRankNumber(i, j2);
					if (rankNumber > rank)
					{
						r.update(rankNumber, new InvertedVertex(new Vertex(
							i2 - 1, j - 1), new Vertex(i, j2)));
					}
				}
			}
		}
		return r;
	}

	private MatrixInt buildMatrix(RankCollection r)
	{
		int[][] ret = new int[sLength + 1][tLength + 1];
		for (int i = 1; i <= sLength; i++)
		{
			for (int j = 1; j <= tLength; j++)
			{
				ret[i][j] = r.getRankNumber(i, j);
			}
		}
		return new MatrixIntPrimitive(ret);
	}

	protected void buildPath()
	{
		lstItrInvArcs = invertedArcs.listIterator(invertedArcs.size());
		if (lstItrInvArcs.hasPrevious())
		{
			currentInvArc = (Arc) lstItrInvArcs.previous();
		}
		int i, j, i1, j1;
		Vertex beginVertex, endVertex;
		endVertex = r.getRankVertex(new Vertex(sLength, tLength));
		while ((endVertex != null) && (!endVertex.equals(new Vertex(0, 0))))
		{
			i = endVertex.getRow();
			j = endVertex.getCol();
			if (endVertex instanceof InvertedVertex)
			{
				beginVertex = ((InvertedVertex) endVertex).getBeginVertex();
				i1 = beginVertex.getRow();
				j1 = beginVertex.getCol();
				addInvertedPath(i + 1, j1, i1 + 1, j);
			}
			else
			{
				beginVertex = new Vertex(i - 1, j - 1);
				getPath().addFirst(
					new Arc(beginVertex, endVertex, arcDiagonal, 0));
			}
			endVertex = r.getRankVertex(beginVertex);
		}
	}

	public int getTotalBimValue()
	{
		return r.getRankNumber(new Vertex(sLength, tLength));
	}

	public MatrixInt getMatrixBim()
	{
		if (matrixBim == null)
		{
			matrixBim = buildMatrix(r);
		}
		return matrixBim;
	}

	protected void addInvertedPath(int iBegin, int jBegin, int iEnd, int jEnd)
	{
		RankCollection r = new RankCollection();
		Vertex vBegin, vEnd;
		int reflectionI, reflectionJ, rank, rank1;
		while ((currentInvArc != null)
			&& ((vEnd = currentInvArc.getEndVertex()).getRow() >= iEnd))
		{
			vBegin = currentInvArc.getBeginVertex();
			if ((vBegin.getRow() <= iBegin) && (vBegin.getCol() >= jBegin)
				&& (vEnd.getCol() <= jEnd))
			{
				reflectionI = iBegin - vEnd.getRow();
				reflectionJ = vEnd.getCol();
				rank = r.getRankNumber(reflectionI - 1, reflectionJ - 1)
					+ currentInvArc.getWeight();
				rank1 = r.getRankNumber(reflectionI, reflectionJ);
				if (rank > rank1)
				{
					r.update(rank, reflectionI, reflectionJ);
				}
			}
			if (lstItrInvArcs.hasPrevious())
			{
				currentInvArc = (Arc) lstItrInvArcs.previous();
			}
			else
			{
				currentInvArc = null;
			}
		}
		int i, j, i1, j1;
		Vertex beginVertex, endVertex, v, v1;
		v = r.getRankVertex(new Vertex(iBegin - iEnd, jEnd));
		while ((v != null) && (!v.equals(new Vertex(0, 0))))
		{
			i = iBegin - v.getRow();
			j = v.getCol();
			beginVertex = new Vertex(i + 1, j - 1);
			endVertex = new Vertex(i, j);
			getPath().addFirst(
				new Arc(beginVertex, endVertex, arcDiagonalInverted, 0));
			v = r.getRankVertex(new Vertex(v.getRow() - 1, v.getCol() - 1));
		}
	}

	class Match
	{
		int		i, j;
		boolean	inverted;
		int		weight;

		Match(int i, int j, boolean inverted, int weight)
		{
			this.i = i;
			this.j = j;
			this.inverted = inverted;
			this.weight = weight;
		}

		public Vertex getVertex()
		{
			return new Vertex(i, j);
		}

		Match reflectionRotation()
		{
			return new Match(j, sLength + 1 - i, !inverted, getWeight());
		}

		public Match reflectionRotationInverted()
		{
			return new Match(sLength + 1 - j, i, !inverted, getWeight());
		}

		boolean isInverted()
		{
			return inverted;
		}

		int getI()
		{
			return i;
		}

		int getJ()
		{
			return j;
		}

		public int getWeight()
		{
			return this.weight;
		}
	}

	class MatchCollection
	{
		MatchLinkedListEntry	head;
		MatchLinkedListEntry	first;	// para ser utilizado em iterações

		// pode variar dependendo de onde deve começar a iteração

		MatchCollection()
		{
			head = new MatchLinkedListEntry(null, null, null);
			head.next = head.previous = head;
			first = head;
		}

		MatchLinkedListEntry addBefore(Match match, MatchLinkedListEntry e)
		{
			MatchLinkedListEntry newEntry = new MatchLinkedListEntry(match, e,
				e.previous);
			newEntry.previous.next = newEntry;
			newEntry.next.previous = newEntry;
			return newEntry;
		}

		void addLast(Match match)
		{
			addBefore(match, head);
		}

		MatchLinkedListEntry getFirst()
		{
			return first;
		}

		void setFirst(MatchLinkedListEntry first)
		{
			this.first = first;
		}

		MatchLinkedListEntry getHead()
		{
			return head;
		}
	}

	class MatchInvertedCollection extends MatchCollection
	{
		MatchLinkedListEntry[]	firstEntries;

		MatchInvertedCollection()
		{
			super();
			firstEntries = new MatchLinkedListEntry[tLength];
		}

		public MatchLinkedListEntry getFirstEntry(int j)
		{
			return firstEntries[j - 1];
		}

		public void setFirstEntry(int j, MatchLinkedListEntry entry)
		{
			firstEntries[j - 1] = entry;
		}

		public void addRowInverted(MatchLinkedListEntry entryIteratorForeign)
		{
			int j;
			Match match = entryIteratorForeign.match;
			int i = match.getI();
			MatchLinkedListEntry entry = head.next;
			do
			{
				if (match.isInverted())
				{
					j = match.getJ();
					if (getFirstEntry(j) != null)
					{
						entry = addBefore(match.reflectionRotation(),
							getFirstEntry(j));
					}
					else
					{
						while ((entry != head) && (entry.match.getI() < j))
						{
							entry = entry.next;
						}
						entry = addBefore(match.reflectionRotation(), entry);
					}
					setFirstEntry(j, entry);
				}
				entryIteratorForeign = entryIteratorForeign.next;
				match = entryIteratorForeign.match;
			}
			while ((match != null) && (match.getI() == i));
		}
	}

	private class MatchLinkedListEntry
	{
		Match	match;
		MatchLinkedListEntry	next, previous;

		MatchLinkedListEntry(Match match, MatchLinkedListEntry next,
				MatchLinkedListEntry previous)
		{
			this.match = match;
			this.next = next;
			this.previous = previous;
		}
	}

	public class InvertedVertex extends Vertex
	{
		Vertex	beginVertex;

		InvertedVertex(Vertex beginVertex, Vertex endVertex)
		{
			super(endVertex.getRow(), endVertex.getCol());
			this.beginVertex = beginVertex;
		}

		public Vertex getBeginVertex()
		{
			return this.beginVertex;
		}
	}

	// Dummy weight to associate with an Object in the backing Map
	static final Object	PRESENT	= new Object();

	class VertexCollection extends TreeMap
	{
		VertexCollection()
		{
			super();
		}

		VertexCollection(Vertex vertex)
		{
			this();
			put(vertex);
		}

		private Object put(Vertex vertex)
		{
			return super.put(vertex, PRESENT);
		}

		public Object put(Object key, Object value)
		{
			throw new UnsupportedOperationException(
				"Método inválido para esta classe");
		}

		public Vertex getVertexDominate(Vertex vertex)
		{
			Vertex v;
			TreeMap.Entry entryV = root;
			while (entryV != null)
			{
				v = (Vertex) entryV.getKey();
				if (v.dominates(vertex))
				{
					return v;
				}
				else
				{
					if (v.getRow() > vertex.getRow())
					{
						if (v.getCol() >= vertex.getCol())
						{
							//não pode haver dominado e dominante numa mesma
							// coleção de vértices
							return null;
						}
						entryV = entryV.left;
					}
					else
					{
						entryV = entryV.right;
					}
				}
			}
			return null;
		}

		public void deleteDominateds(Vertex vertex)
		{
			//os dominados devem estar na mesma linha (i) de vertex
			Vertex v;
			TreeMap.Entry entryV = lastEntry();
			int vertexI = vertex.getRow();
			Entry entryAux;
			while ((entryV != null)
				&& ((v = (Vertex) entryV.getKey()).getRow() >= vertexI))
			{
				if (vertex.dominates(v))
				{
					entryAux = predecessor(entryV);
					deleteEntry(entryV);
					entryV = entryAux;
				}
				else
				{
					entryV = predecessor(entryV);
				}
			}
		}
	}

	class RankCollection extends TreeMap
	{
		int	w;	//diferença máxima dos pesos de um dominado e seu

		// dominante

		RankCollection()
		{
			super();
			createRank(0, new Vertex(0, 0));
		}

		private Entry createRank(int rankNumber, Vertex vertex)
		{
			return (Entry) put(new Integer(rankNumber), new VertexCollection(
				vertex));
		}

		private Entry createRank(int rankNumber)
		{
			return (Entry) put(new Integer(rankNumber), new VertexCollection());
		}

		public int getRankNumber(int i, int j)
		{
			return getRankNumber(new Vertex(i, j));
		}

		public int getRankNumber(Vertex vertex)
		{
			int w = getW();
			int m, m1;
			int a = 0;
			Vertex vertexDominate; //vértice que domina vertex
			TreeMap.Entry rankM = root;
			TreeMap.Entry rankM1;
			while (rankM != null)
			{
				vertexDominate = null;
				rankM1 = rankM;
				m = ((Integer) rankM.getKey()).intValue();
				m1 = ((Integer) rankM1.getKey()).intValue();
				while ((vertexDominate == null) && ((m1 - m) < w)
					&& (rankM1 != null))
				{
					vertexDominate = ((VertexCollection) rankM1.getValue())
						.getVertexDominate(vertex);
					if (vertexDominate == null)
					{
						rankM1 = successor(rankM1);
						if (rankM1 != null)
						{
							m1 = ((Integer) rankM1.getKey()).intValue();
						}
					}
				}
				if (vertexDominate == null)
				// não existe vértice em m .. m+w que domina vertex
				{
					//pode estar a esquerda
					rankM = rankM.left;
				}
				else
				{
					a = m1;
					if (vertexDominate.equals(vertex))
					{
						return a;
					}
					else
					{
						while ((rankM != null)
							&& (((Integer) rankM.getKey()).intValue() <= a))
						{
							rankM = rankM.right;
						}
					}
				}
			}
			return a;
		}

		public Vertex getRankVertex(Vertex vertex)
		{
			Vertex ret = null;
			int rankNumber = getRankNumber(vertex);
			VertexCollection vertexCollection = (VertexCollection) get(new Integer(
				rankNumber));
			if (vertexCollection != null)
			{
				ret = vertexCollection.getVertexDominate(vertex);
			}
			return ret;
		}

		public void update(int rankNumber, Vertex vertex)
		{
			VertexCollection vertexes;
			//procura o rank de número rankNumber
			TreeMap.Entry rank = getEntry(new Integer(rankNumber));
			if (rank == null)
			//se não existe cria o rank com rankNumber
			{
				rank = createRank(rankNumber);
				vertexes = (VertexCollection) rank.getValue();
				vertexes.put(vertex);
			}
			else
			{
				//se existe remove todos os vértices que são dominados por
				//vertex e insere vertex na coleção de vértices daquele rank
				vertexes = (VertexCollection) rank.getValue();
				vertexes.deleteDominateds(vertex);
				vertexes.put(vertex);
			}
			rank = predecessor(rank);
			vertexes = (VertexCollection) rank.getValue();
			//procura um rank que tem algum vértice que domine vertex
			while (vertexes.getVertexDominate(vertex) == null)
			{
				//enqto não encontra deleta todos os dominados por vertex
				vertexes.deleteDominateds(vertex);
				rank = predecessor(rank);
				vertexes = (VertexCollection) rank.getValue();
			}
			int w = rankNumber - ((Integer) rank.getKey()).intValue();
			if (w > getW())
			{
				setW(w);
			}
		}

		public void update(int rankNumber, int i, int j)
		{
			update(rankNumber, new Vertex(i, j));
		}

		public int getW()
		{
			return this.w;
		}

		public void setW(int w)
		{
			this.w = w;
		}
	}

}
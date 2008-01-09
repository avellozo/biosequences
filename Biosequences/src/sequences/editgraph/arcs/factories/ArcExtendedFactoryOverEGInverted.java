/*
 * Created on 14/12/2007
 */
package sequences.editgraph.arcs.factories;

import sequences.editgraph.ArcExtendedFactory;
import sequences.editgraph.EditGraph;
import sequences.editgraph.OptimumPathMethod;
import sequences.editgraph.Vertex;
import sequences.editgraph.VertexRange;
import sequences.editgraph.arcs.ArcExtendedOverEG;
import sequences.editgraph.exception.ExceptionInternalEG;
import sequences.editgraph.exception.ExceptionInvalidEditGraph;
import sequences.editgraph.exception.ExceptionInvalidVertex;

public class ArcExtendedFactoryOverEGInverted implements ArcExtendedFactory
{

	// O peso de uma aresta estendida (i,j)(i', j') é o peso do caminho ótimo de (n-i'+k,j)(n-i+k,j') no grafo invertido (egInverted), 
	// onde n é a última linha do grafo estendido e k é a primeira linha do grafo invertido

	EditGraph			egInverted;
	int					n, k;
	OptimumPathMethod	optimumPathMethod;
	int					extensionPenalty;

	public ArcExtendedFactoryOverEGInverted(EditGraph egInverted, OptimumPathMethod optimumPathMethod,
			int extensionPenalty) throws ExceptionInvalidEditGraph
	{
		this(egInverted, optimumPathMethod, egInverted.getRowMax(), egInverted.getRowMin(), extensionPenalty);
	}

	public ArcExtendedFactoryOverEGInverted(EditGraph egInverted, OptimumPathMethod optimumPathMethod,
			int extensionPenalty, int n, int k) throws ExceptionInvalidEditGraph
	{
		this.egInverted = egInverted;
		this.n = n;
		this.k = k;
		this.optimumPathMethod = optimumPathMethod;
		this.extensionPenalty = extensionPenalty;
	}

	public EditGraph getEgInverted()
	{
		return egInverted;
	}

	/**
	 * @return Returns the optimumPathMethod.
	 */
	public OptimumPathMethod getOptimumPathMethod()
	{
		return optimumPathMethod;
	}

	/**
	 * @return Returns the extensionPenalty.
	 */
	public int getExtensionPenalty()
	{
		return extensionPenalty;
	}

	public ArcExtendedOverEG getExtendedArc(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (!canCreateExtendedArc(vertexRange))
		{
			throw new ExceptionInvalidVertex("Can't create an extended arc from " + vertexRange.getBeginVertex()
				+ " to " + vertexRange.getEndVertex() + ".");
		}
		try
		{
			return new ArcExtendedOverEG(vertexRange, getOptimumPathMethod().createPath(
				transformVertexRange(vertexRange), getEgInverted()), getExtensionPenalty());
		}
		catch (ExceptionInvalidEditGraph e)
		{
			e.printStackTrace();
			throw new ExceptionInternalEG();
		}
	}

	public VertexRange transformVertexRange(VertexRange vertexRange) throws ExceptionInvalidVertex
	{
		if (vertexRange == null)
		{
			throw new ExceptionInvalidVertex("Invalid range: null.");
		}
		return new VertexRange(new Vertex(n + k - vertexRange.getEndVertex().getRow(),
			vertexRange.getBeginVertex().getCol()), new Vertex(n + k - vertexRange.getBeginVertex().getRow(),
			vertexRange.getEndVertex().getCol()));
	}

	public boolean canCreateExtendedArc(VertexRange vertexRange)
	{
		try
		{
			VertexRange r = transformVertexRange(vertexRange);
			return (getEgInverted().existsVertex(r.getBeginVertex()) && getEgInverted().existsVertex(r.getEndVertex()));
		}
		catch (ExceptionInvalidVertex e)
		{
			return false;
		}
	}
}

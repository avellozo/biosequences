/*
 * Created on 22/03/2008
 */
package sequences.dup;

import sequences.editgraph.EditGraphBasic;
import sequences.editgraph.arcs.factories.ArcDiagonalFactory;
import sequences.editgraph.arcs.factories.ArcHorizontalFactory;
import sequences.editgraph.arcs.factories.ArcVerticalFactory;
import sequences.editgraph.arcs.factories.GapSetFactory;

public class EGDupModel1 extends EditGraphBasic
{

	public EGDupModel1(int rowMax, int colMax, ArcHorizontalFactory arcHFactory, ArcVerticalFactory arcVFactory,
			ArcDiagonalFactory arcDFactory, ArcExtDupModel1Factory arcEFactory, GapSetFactory arcGapSetFactory)
	{
		super(rowMax, colMax, arcHFactory, arcVFactory, arcDFactory, arcEFactory, arcGapSetFactory);
	}

	public EGDupModel1(int rowMin, int rowMax, int colMin, int colMax, ArcHorizontalFactory arcHFactory,
			ArcVerticalFactory arcVFactory, ArcDiagonalFactory arcDFactory, ArcExtDupModel1Factory arcEFactory,
			GapSetFactory arcGapSetFactory)
	{
		super(rowMin, rowMax, colMin, colMax, arcHFactory, arcVFactory, arcDFactory, arcEFactory, arcGapSetFactory);
	}

	public ArcExtDupModel1Factory getArcDupModel1Factory()
	{
		return (ArcExtDupModel1Factory) getArcExtendedFactory();
	}
}

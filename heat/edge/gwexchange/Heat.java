package edu.montana.cerg.tempsignal.heat.edge.gwexchange;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.FaceFluxDynamDouble;

public class Heat extends FaceFluxDynamDouble {

	public static final String REQ_STATE_THERM_COND_EFF = "ThermCondEff";
	public static final String REQ_STATE_LENGTH = "Length";
	public static final String REQ_STATE_AREA = "Area";
	public static final String REQ_STATE_GROUNDWATER_TEMP = "Temp";
	public static final String REQ_STATE_VOL = "Volume";
	
	private StateDouble thermCondEff;
	private StateDouble volume;
	private Double length;
	private StateDouble area;
	private StateDouble tempHypo;
	private StateDouble tempGround;
	
	@Override
	public double calculate() 
	{		
		length = ( volume.value / area.value ) / 2 ;
		
		return (thermCondEff.value / length) * area.value * 
				(tempHypo.value - tempGround.value);			
	}

	@Override
	public void setCalcDeps() 
	{
		thermCondEff = (StateDouble)createDependency(REQ_STATE_THERM_COND_EFF);		
		tempGround = (StateDouble)createDependency(((Face)holon).getEdge().getToCell(), 
		        REQ_STATE_GROUNDWATER_TEMP);
        Cell hypoCell = ((Face)holon).getEdge().getFromCell();
		tempHypo = (StateDouble)createDependency(hypoCell, 
				edu.montana.cerg.tempsignal.heat.cell.hyporheic.Temp.class.getSimpleName());
		area = (StateDouble)createDependency(hypoCell, REQ_STATE_AREA);
		volume = (StateDouble)createDependency(hypoCell, REQ_STATE_VOL);				
	}
}

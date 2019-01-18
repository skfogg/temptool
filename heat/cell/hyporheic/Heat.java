package edu.montana.cerg.tempsignal.heat.cell.hyporheic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.CellPoolDynamDouble;

public class Heat extends CellPoolDynamDouble{

	public static final String REQ_STATE_DENSITY_WATER = "DensityWater";
	public static final String REQ_STATE_DENSITY_SEDIMENT = "DensitySediment";
	public static final String REQ_STATE_SPHEAT_WATER = "SpHeatWater";
	public static final String REQ_STATE_SPHEAT_SEDIMENT = "SpHeatSediment";
	public static final String REQ_STATE_VOLUME = "Volume";
	public static final String REQ_STATE_POROSITY = "Porosity";
	public static final String REQ_STATE_TEMP = "Temp";
	
	private StateDouble densityWater;
	private StateDouble densitySediment;
	private StateDouble spHeatWater;
	private StateDouble spHeatSediment;
	private StateDouble volume;
	private StateDouble porosity;
	private StateDouble temp;
	
	@Override
    public double initialize()
	{		
		return (temp.value + 273.15) *
    		(volume.value * porosity.value * densityWater.value * spHeatWater.value +
    		volume.value * (1 - porosity.value) * densitySediment.value * spHeatSediment.value);
    }	
	
	@Override
    public void setInitDeps()
    {
		densityWater = (StateDouble)createDependency(REQ_STATE_DENSITY_WATER);
		densitySediment = (StateDouble)createDependency(REQ_STATE_DENSITY_SEDIMENT);
		spHeatWater = (StateDouble)createDependency(REQ_STATE_SPHEAT_WATER);
		spHeatSediment = (StateDouble)createDependency(REQ_STATE_SPHEAT_SEDIMENT);
		volume = (StateDouble)createDependency(REQ_STATE_VOLUME);
		porosity = (StateDouble)createDependency(REQ_STATE_POROSITY);
		temp = (StateDouble)createDependency(REQ_STATE_TEMP);
    }
	
}

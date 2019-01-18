package edu.montana.cerg.tempsignal.heat.cell.hyporheic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;

public class Temp extends AutoDynamDouble {
		
	private StateDouble densityWater;
	private StateDouble densitySediment;
	private StateDouble spHeatWater;
	private StateDouble spHeatSediment;
	private StateDouble volume;
	private StateDouble porosity;
	private StateDouble temp;
	private StateDouble heat;
	
	@Override
	public double calculate() 
	{		
		return (heat.value /
				(volume.value * porosity.value * densityWater.value * spHeatWater.value +
				volume.value * (1 - porosity.value) * densitySediment.value * spHeatSediment.value)) - 273.15;				
	}

	@Override
	public double initialize()
	{
        if (getStateVal().isNull())
        {
            holon.getSimulationModel().getLogger().logSevere(
                    "Temperature not initialized in " +
                    holon.getName());
            return 0;
        }
        else
        {
            return ((StateDouble)getStateVal()).value;
        }
	}
	
	@Override
	public void setCalcDeps() 
	{
		densityWater = (StateDouble)createDependency(Heat.REQ_STATE_DENSITY_WATER);
		densitySediment = (StateDouble)createDependency(Heat.REQ_STATE_DENSITY_SEDIMENT);
		spHeatWater = (StateDouble)createDependency(Heat.REQ_STATE_SPHEAT_WATER);
		spHeatSediment = (StateDouble)createDependency(Heat.REQ_STATE_SPHEAT_SEDIMENT);
		volume = (StateDouble)createDependency(Heat.REQ_STATE_VOLUME);
		porosity = (StateDouble)createDependency(Heat.REQ_STATE_POROSITY);
		heat = (StateDouble)createDependency("Heat");
	}

	@Override
	public void setInitDeps()
	{
	}
	
}

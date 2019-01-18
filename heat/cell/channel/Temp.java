package edu.montana.cerg.tempsignal.heat.cell.channel;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;

import edu.montana.cerg.tempsignal.heat.DensityCalculator;
import edu.montana.cerg.tempsignal.heat.HeatCurrency;

public class Temp extends AutoDynamDouble {       
       
    private StateDouble water;
    private StateDouble heat;
    private double spHeat;
    private DensityCalculator densityCalc;
    
    @Override
    public double calculate()
    {
        return (heat.value / (water.value * spHeat * densityCalc.getDensity())) - 273.15;
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
        water = (StateDouble)createDependency(HeatCurrency.REQ_CURRENCY_ADVECT_MEDIUM);
        heat = (StateDouble)createDependency(Heat.class.getSimpleName());
        
        try
        {
            StateDouble spHeatState = (StateDouble)holon.getStateVal(Heat.OPT_STATE_SP_HEAT);
            spHeat = spHeatState.value;
        }
        catch (Exception e)
        {
            spHeat = HeatCurrency.SP_HEAT_WATER_10;
        }

        StateDouble density;
        try
        {
            // Check if density state exists
            holon.getStateVal(Heat.OPT_STATE_DENSITY);
            
            // If density state DOES exist, instantiate the dynamic calculator
            density = (StateDouble)createDependency(Heat.OPT_STATE_DENSITY);                                           
        }
        catch (Exception e)
        {
            // If density states DOES NOT exist, set to null
        	// (instantiate the static calculator)
            density = null;
        }
        
        densityCalc = DensityCalculator.createDensityCalculator(density);        
    }    
    
    @Override
    public void setInitDeps()
    {
    }
    
}

package edu.montana.cerg.tempsignal.heat.cell.channel;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.CellPoolDynamDouble;
import edu.montana.cerg.tempsignal.heat.HeatCurrency;

public class Heat extends CellPoolDynamDouble {
    
    public static final String REQ_STATE_TOCELL_AREA = "Area";
    
    /**
     * Name of optional state for medium density
     */
    public static final String OPT_STATE_DENSITY = "Density";
    
    /**
     * Name of the optional state for medium specific heat
     */
    public static final String OPT_STATE_SP_HEAT = "SpHeat";
    
    private StateDouble water;
    private StateDouble temp;
    private double spHeat;
    private StateDouble density;
    
    @Override
    public double initialize()
    {
        // If density was not provided, use a default value
        if (density == null)
            return water.value * (temp.value + 273.15) * spHeat * HeatCurrency.DENSITY_WATER_10;        
        else
            return water.value * (temp.value + 273.15) * spHeat * density.value;        
    }
    
    @Override
    public void setInitDeps()
    {
        water = (StateDouble)createDependency(HeatCurrency.REQ_CURRENCY_ADVECT_MEDIUM);
        temp = (StateDouble)createDependency(Temp.class.getSimpleName());
        
        try{
            StateDouble spHeatState = (StateDouble)holon.getStateVal(OPT_STATE_SP_HEAT);
            spHeat = spHeatState.value;
        }
        catch (Exception e){
            spHeat = HeatCurrency.SP_HEAT_WATER_10;
        }
        
        try{
            // If density state already exists, get a reference to it
            holon.getStateVal(OPT_STATE_DENSITY);
            density = (StateDouble)createDependency(OPT_STATE_DENSITY);
        }
        catch (Exception e){            
            density = null;
        }
    }

}

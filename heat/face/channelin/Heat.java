package edu.montana.cerg.tempsignal.heat.face.channelin;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.FaceFluxDynamDouble;

import edu.montana.cerg.tempsignal.heat.DensityCalculator;
import edu.montana.cerg.tempsignal.heat.HeatCurrency;
import edu.montana.cerg.tempsignal.heat.cell.channel.Temp;

/**
 * Controls heat movement into or out of a model at a channel boundary
 * 
 * @author robert.payn
 *
 */
@SuppressWarnings("serial")
public class Heat extends FaceFluxDynamDouble {
    
    /**
     * An abstract inner class for changing implementation of how density is obtained 
     * 
     * @author robert.payn
     *
     */    
    
    /**
     * State name for the required table name with data for external temperature
     */
    public static final String REQ_STATE_EXTERNAL_TEMP_TABLE = "ExtTempTable";
    
    /**
     * State name for optional external density
     */
    public static final String OPT_STATE_EXT_DENSITY = "ExtDensity";
    
    /**
     * State name for optional density of water in attached cell
     */
    public static final String OPT_STATE_DENSITY = "CellDensity";
    
    /**
     * State name for optional external specific heat
     */
    public static final String OPT_STATE_EXT_SPHEAT = "ExtSpHeat";
    
    /**
     * Rate of water movement into (+) or out of (-) the boundary
     */
    private StateDouble water;
    
    /**
     * Water temperature external to the model
     */
    private StateDouble extTemp;
    
    /**
     * Water temperature in the connected cell
     */
    private StateDouble toTemp;
    
    /**
     * Specific heat of water external to the model
     */
    private double extSpHeat;
    
    /**
     * Specific heat of water in the connected cell
     */
    private double toSpHeat;
    
    /**
     * Calculator for external density
     */
    private DensityCalculator extDensityCalc;
    
    /**
     * Calculator for density in the connected cell
     */
    private DensityCalculator toDensityCalc;

    /**
     * Calculate the amount of heat moving into the model (water > 0) or out of the model (water < 0)
     */
    @Override
    public double calculate()
    {               
        return water.value > 0 ? 
        		water.value * (extTemp.value + 273.15) * extSpHeat * extDensityCalc.getDensity() :
        		water.value * (toTemp.value + 273.15) * toSpHeat * toDensityCalc.getDensity();
    }

    /**
     * Define the calculators and states necessary to calculate heat movement through the boundary.
     */
    @Override
    public void setCalcDeps()
    {
        water = (StateDouble)createDependency(HeatCurrency.REQ_CURRENCY_ADVECT_MEDIUM);
        extTemp = (StateDouble)createDependency(ExtTemp.class.getSimpleName());
        
        try
        {
            StateDouble spHeatState = (StateDouble)holon.getStateVal(OPT_STATE_EXT_SPHEAT);
            extSpHeat = spHeatState.value;
        }
        catch (Exception e)
        {
            extSpHeat = HeatCurrency.SP_HEAT_WATER_10;
        }

        Cell toCell = ((Face)holon).getCell();
        toTemp = (StateDouble)createDependency(toCell, Temp.class.getSimpleName());

        try
        {
            StateDouble spHeatState = (StateDouble)holon.getStateVal(
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_SP_HEAT);
            toSpHeat = spHeatState.value;
        }
        catch (Exception e)
        {
            toSpHeat = HeatCurrency.SP_HEAT_WATER_10;
        }

        StateDouble density;
        try
        {
            // Check if density states exists
            holon.getStateVal(OPT_STATE_EXT_DENSITY);
            
            // If density state DOES exist, instantiate the dynamic calculator
            density = (StateDouble)createDependency(OPT_STATE_EXT_DENSITY);                       
        }
        catch (Exception e)
        {
            // If density state DOES NOT exist, instantiate the static calculator
            density = null;            
        }
        
        extDensityCalc = DensityCalculator.createDensityCalculator(density);
        
        try
        {
            // Check if specific heat and density states exist
            toCell.getStateVal(OPT_STATE_DENSITY);
            
            // If specific heat and density states DO exist, instantiate the dynamic calculator
            density = (StateDouble)createDependency(toCell, OPT_STATE_DENSITY);                       
        }
        catch (Exception e)
        {
            // If specific heat and density states DO NOT exist, instantiate the static calculator
            density = null;            
        }
        
        toDensityCalc = DensityCalculator.createDensityCalculator(density);
    }

}

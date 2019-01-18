package edu.montana.cerg.tempsignal.heat.edge.channeltransport;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.FaceFluxDynamDouble;

import edu.montana.cerg.tempsignal.heat.DensityCalculator;
import edu.montana.cerg.tempsignal.heat.HeatCurrency;
import edu.montana.cerg.tempsignal.heat.cell.channel.Temp;

public class Heat extends FaceFluxDynamDouble {    
	
    private StateDouble water;
    private StateDouble fromTemp;
    private StateDouble toTemp;
    private double fromSpHeat;
    private double toSpHeat;
    private DensityCalculator fromDensityCalc;
    private DensityCalculator toDensityCalc;

    @Override
    public double calculate()
    {
        if (water.value > 0)        
            return water.value * (fromTemp.value + 273.15) * fromSpHeat * fromDensityCalc.getDensity();        
        else        
            return water.value * (toTemp.value + 273.15) * toSpHeat * toDensityCalc.getDensity();        
    }

    @Override
    public void setCalcDeps()
    {
        water = (StateDouble)createDependency(HeatCurrency.REQ_CURRENCY_ADVECT_MEDIUM);

        Cell fromCell = ((Face)holon).getEdge().getFromCell();
        Cell toCell = ((Face)holon).getEdge().getToCell();
        fromTemp = (StateDouble)createDependency(fromCell, Temp.class.getSimpleName());
        toTemp = (StateDouble)createDependency(toCell, Temp.class.getSimpleName());
        
        try
        {
            StateDouble spHeatState = (StateDouble)fromCell.getStateVal(
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_SP_HEAT);
            fromSpHeat = spHeatState.value;
        }
        catch (Exception e)
        {
            fromSpHeat = HeatCurrency.SP_HEAT_WATER_10;
        }
        
        try
        {
            StateDouble spHeatState = (StateDouble)toCell.getStateVal(
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_SP_HEAT);
            toSpHeat = spHeatState.value;
        }
        catch (Exception e)
        {
            fromSpHeat = HeatCurrency.SP_HEAT_WATER_10;
        }
        
        StateDouble density;
        try
        {
            // Check if density states exists
            fromCell.getStateVal(
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_DENSITY);
            
            // If density state DOES exist, instantiate the dynamic calculator
            density = (StateDouble)createDependency(fromCell, 
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_DENSITY);            
        }
        catch (Exception e)
        {
            // If density state DOES NOT exist, instantiate the static calculator
        	density = null;
        }
        
        fromDensityCalc = DensityCalculator.createDensityCalculator(density);
        
        try
        {
            // Check if specific heat and density states exist
            toCell.getStateVal(
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_DENSITY);
            
            // If specific heat and density states DO exist, instantiate the dynamic calculator
            density = (StateDouble)createDependency(toCell, 
                    edu.montana.cerg.tempsignal.heat.cell.channel.Heat.OPT_STATE_DENSITY);                        
        }
        catch (Exception e)
        {
            // If specific heat and density states DO NOT exist, instantiate the static calculator
            density = null;            
        }
        
        toDensityCalc = DensityCalculator.createDensityCalculator(density);
    }
}

package edu.montana.cerg.tempsignal.heat.edge.atmexchange;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.FaceFluxDynamDouble;

public class Heat extends FaceFluxDynamDouble {
    
    public static final String REQ_STATE_THERM_COND_EFF = "ThermCondEff";
    
    private StateDouble area;
    
    private StateDouble thermCondEff;
    
    private StateDouble tempSurface;
    
    private StateDouble tempAtm;
    
    @Override
    // therm cond = kJ/s
    public double calculate()
    {
        return thermCondEff.value * area.value * (tempAtm.value - tempSurface.value);    	
    }   
    
    @Override 
    public void setCalcDeps()
    {
        thermCondEff = (StateDouble)createDependency(REQ_STATE_THERM_COND_EFF);
        tempAtm = (StateDouble)createDependency(
                ((Face)holon).getEdge().getFromCell(), 
                edu.montana.cerg.tempsignal.heat.cell.atmosphere.Temp.class.getSimpleName());
        Cell surfaceCell = ((Face)holon).getEdge().getToCell();
        area = (StateDouble)createDependency(
                surfaceCell,
                edu.montana.cerg.tempsignal.heat.cell.channel.Heat.REQ_STATE_TOCELL_AREA);
        tempSurface = (StateDouble)createDependency(
                surfaceCell, 
                edu.montana.cerg.tempsignal.heat.cell.channel.Temp.class.getSimpleName());    
    }
}

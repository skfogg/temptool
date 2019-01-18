package edu.montana.cerg.tempsignal.heat.face.channelin.compoundsin;


import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;

import edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic.StartDay;

public class ExtTemp extends AutoDynamDouble {
    
    private StateDouble period;
    private StateDouble average;
    private StateDouble amplitude;
    private TimeKeeper timeKeeper;
    private StateDouble daily;
    private StateDouble phaseDay;
    private StateDouble phase;

    @Override
    public double calculate()
    { 
        double phaseTime = (timeKeeper.getCurrentTime() + phaseDay.value*86400);
    	
    	return average.value + (daily.value * amplitude.value )/2* Math.sin(phaseTime*2*Math.PI*(1/period.value) + phase.value);
    }

    @Override
    public void setCalcDeps()
    {
        
    	Face[] faces = ((Face)holon).getCell().getFacesArray();
    	Cell atmCell = null;
    			
    	for (int i=0; i<faces.length; i++){
    		if (faces[i].getName().startsWith("atm")){
    			atmCell = faces[i].getEdge().getFromCell();
    		}
    		
    	}
    	
        phaseDay = (StateDouble)createDependency(atmCell, StartDay.class.getSimpleName());
    	
    	timeKeeper = holon.getSimulationModel().getTimeKeeper();
        period = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PERIOD);
        average = (StateDouble)createDependency(SubMeanTemp.class.getSimpleName());
        amplitude = (StateDouble)createDependency(SubAmpTemp.class.getSimpleName());
        daily = (StateDouble)createDependency(Heat.REQ_STATE_DAILY_SIN_OR_NOT);
        phase = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PHASE);
    }
}
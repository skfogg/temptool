package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;


import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;

public class Temp extends AutoDynamDouble {   
	
    private StateDouble period;
    private StateDouble average;
    private StateDouble amplitude;
    private StateDouble phaseDay;
    private StateDouble phase;
    private TimeKeeper timeKeeper;
    
    @Override
    public double calculate()
    {
    	double phaseTime = (timeKeeper.getCurrentTime() + phaseDay.value*86400);
        int day = (int)Math.floor(phaseTime/86400);
        if (day == phaseTime/86400){
        	System.out.println("Julian Day: "+day);
        }
        return average.value + amplitude.value/2*Math.sin(phaseTime*2*Math.PI*(1/period.value) +phase.value);
        
    }

    @Override
    public void setCalcDeps()
    {
        
    	

    	
        phaseDay = (StateDouble)createDependency(StartDay.class.getSimpleName());
    	timeKeeper = holon.getSimulationModel().getTimeKeeper();
        period = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PERIOD);
        average = (StateDouble)createDependency(SubMeanTemp.class.getSimpleName());
        amplitude = (StateDouble)createDependency(SubAmpTemp.class.getSimpleName());
        phase = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PHASE);
    }
}
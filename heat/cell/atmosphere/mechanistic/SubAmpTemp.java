package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;


public class SubAmpTemp extends AutoDynamDouble {
    

	private StateDouble period;
    private StateDouble average;
    private StateDouble amplitude;
    private StateDouble phaseDay;
    private StateDouble phase;
    private TimeKeeper timeKeeper;
    private StateDouble annual;

    @Override
    public double calculate()
    {
        double phaseTime = (timeKeeper.getCurrentTime() + phaseDay.value*86400);
    	
        
        return (average.value + amplitude.value/2 * Math.sin(phaseTime*2*Math.PI*(1/period.value) + phase.value ));
        
    }

    @Override
    public void setCalcDeps()
    {
        phaseDay = (StateDouble)createDependency(StartDay.class.getSimpleName());
    	timeKeeper = holon.getSimulationModel().getTimeKeeper();
        period = (StateDouble)createDependency(Heat.REQ_STATE_SUB_AMP_PERIOD);
        average = (StateDouble)createDependency(Heat.REQ_STATE_SUB_AMP_AVG);
        amplitude = (StateDouble)createDependency(Heat.REQ_STATE_SUB_AMP_AMP);
        annual = (StateDouble)createDependency(Heat.REQ_STATE_ANNUAL_OR_NOT);
        phase = (StateDouble)createDependency(Heat.REQ_STATE_SUB_AMP_PHASE);
    }
}
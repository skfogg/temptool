package edu.montana.cerg.tempsignal.heat.cell.atmosphere.compoundsin;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;

public class Temp extends AutoDynamDouble {
    
    private StateDouble period;
    private StateDouble average;
    private StateDouble amplitude;
    private StateDouble phase;
    private TimeKeeper timeKeeper;

    @Override
    public double calculate()
    {
        double angle = 2 * Math.PI * 
            (Math.IEEEremainder(timeKeeper.getCurrentTime() - phase.value, period.value) / period.value); 
        return average.value + (amplitude.value / 2) * Math.sin(angle);
    }

    @Override
    public void setCalcDeps()
    {
        timeKeeper = holon.getSimulationModel().getTimeKeeper();
        period = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PERIOD);
        phase = (StateDouble)createDependency(Heat.REQ_STATE_SUB_PHASE);
        average = (StateDouble)createDependency(SubMeanTemp.class.getSimpleName());
        amplitude = (StateDouble)createDependency(SubAmpTemp.class.getSimpleName());
    }
}
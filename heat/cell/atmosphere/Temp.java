package edu.montana.cerg.tempsignal.heat.cell.atmosphere;

import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.interpolator.InterpolatorFactory;
import org.neosimulation.neo.user.interpolator.InterpolatorFactoryException;
import org.neosimulation.neo.user.interpolator.MathInterpolator;

import edu.montana.cerg.tempsignal.heat.face.channelin.Heat;

public class Temp extends AutoDynamDouble {
	  
	private MathInterpolator interp;
	

    @Override
    public double calculate()
    {
    	return interp.getValue(holon.getSimulationModel().getTimeKeeper().getCurrentTime());
    }

    @Override
    public void setCalcDeps()
    {
    	String tableName = (String)((StateGeneric<?>)createDependency(
                Heat.REQ_STATE_EXTERNAL_TEMP_TABLE)).value;
        try
        {
            interp = InterpolatorFactory.getInstance().createMathInterpolator(
                    tableName, 
                    holon.getSimulationModel());
        }
        catch (InterpolatorFactoryException e)
        {
            holon.getSimulationModel().getLogger().logSevere(
                    stateVal.getName() + " in " +
                    holon.getName() + " cannot create an interpolator");
        }
    }
    
}

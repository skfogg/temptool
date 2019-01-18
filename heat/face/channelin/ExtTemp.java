package edu.montana.cerg.tempsignal.heat.face.channelin;

import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.interpolator.InterpolatorFactory;
import org.neosimulation.neo.user.interpolator.InterpolatorFactoryException;
import org.neosimulation.neo.user.interpolator.MathInterpolator;

/**
 * Controls the external temperature on the boundary of a channel
 * 
 * @author robert.payn
 *
 */
@SuppressWarnings("serial")
public class ExtTemp extends AutoDynamDouble {

    /**
     * Interpolator for external temperature input data
     */
    private MathInterpolator interp;
    
    /**
     * Interpolate the current value of external temperature
     */
    @Override
    public double calculate()
    {
        return interp.getValue(holon.getSimulationModel().getTimeKeeper().getCurrentTime());
//    	return 0.0;
    }

    /**
     * Define the states and set up the interpolator needed to calculate external temperature
     */
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

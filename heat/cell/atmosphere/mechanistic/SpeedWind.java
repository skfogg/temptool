package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.interpolator.InterpolatorFactory;
import org.neosimulation.neo.user.interpolator.InterpolatorFactoryException;
import org.neosimulation.neo.user.interpolator.MathInterpolator;


/**
 * Controls the wind speed (read from file)
 * 
 * @author robert.payn
 *
 */
public class SpeedWind extends AutoDynamDouble {

	private static final String REQ_STATE_WIND_SPEED_TABLE = "ExtWindTable";
	/**
	 * Interpolator containing table input data for wind speed
	 */
	private MathInterpolator interp;

	/**
	 * Calculates the wind speed
	 * 
	 * @return Wind speed (m sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() {

		return interp.getValue(holon.getSimulationModel().getTimeKeeper().getCurrentTime());
		
	}

	/**
	 * Calculates the initial wind speed
	 * 
	 * @return Wind speed (m sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() {

		return calculate();
		
	}

	/**
	 * Defines the state dependencies necessary for interpolating wind speed
	 * from values in a table
	 */
	@Override
	public void setCalcDeps() 
	{

    	String tableName = (String)((StateGeneric<?>)createDependency(
                REQ_STATE_WIND_SPEED_TABLE)).value;		

 
		try
		{
			interp = InterpolatorFactory.getInstance().createMathInterpolator(
					tableName, holon.getSimulationModel());
		}
		catch(InterpolatorFactoryException e)
		{
            holon.getSimulationModel().getLogger().logSevere(
                    stateVal.getName() + " in " +
                    holon.getName() + " cannot create an interpolator");
		}
		

		
	}

}

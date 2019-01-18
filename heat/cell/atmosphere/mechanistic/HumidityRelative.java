package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.interpolator.InterpolatorFactory;
import org.neosimulation.neo.user.interpolator.InterpolatorFactoryException;
import org.neosimulation.neo.user.interpolator.MathInterpolator;



/**
 * Controls the relative humidity of the air (read from file)
 * 
 * @author robert.payn
 */
public class HumidityRelative extends AutoDynamDouble {
	
	private static final String REQ_STATE_RELATIVE_HUMIDITY_TABLE = "RelHumidFile";

	/**
	 * Interpolator containing table input data for relative humidity
	 */
	private MathInterpolator interp;
	
	/**
	 * Calculate the relative humidity
	 * 
	 * @return Relative humidity (%)
	 */
	@Override
	public double calculate() {

		return interp.getValue(holon.getSimulationModel().getTimeKeeper().getCurrentTime());
		
	}

	/**
	 * Calculate the initial relative humidity
	 * 
	 * @return Relative humidity (%)
	 */
	@Override
	public double initialize() {

		return calculate();
		
	}

	/**
	 * Defines the state dependencies necessary for interpolating relative humidity
	 * from values in a table
	 */
	@Override
	public void setCalcDeps() 
	{

    	String tableName = (String)((StateGeneric<?>)createDependency(
                REQ_STATE_RELATIVE_HUMIDITY_TABLE)).value;		

 
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

package edu.montana.cerg.tempsignal.heat.face.solarradflux;

import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.interpolator.InterpolatorFactory;
import org.neosimulation.neo.user.interpolator.InterpolatorFactoryException;
import org.neosimulation.neo.user.interpolator.MathInterpolator;



/**
 * Controls the flux of shortwave radiation transmitted through 
 * air cell on "from" side of edge to water cell on "to" side of edge
 * 
 * <p>This is a simple calculator called by <code>Heat</code>.
 * 
 * @see Heat
 * @author robert.payn
 */
public class ShortwaveFlux extends AutoDynamDouble {


	private static final String REQ_STATE_SW_RAD_TABLE = "ExtInsolationTable";
	/**
	 * Interpolator containing table input data for shortwave radiation
	 */
	private MathInterpolator interp;
	/**
	 * Calculates the flux of shortwave radiation from air to water
	 * 
	 * @return Shortwave radiation flux
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{	
		// interpolate the shortwave flux between the two closest time
		// steps in the interpolation table
		return interp.getValue(holon.getSimulationModel().getTimeKeeper().getCurrentTime());
	}

	/**
	 * Calculates the initial flux of shortwave radiation from air to water
	 * 
	 * @return Shortwave radiation flux 
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() 
	{
		return calculate();	
	}

	
	public void setCalcDeps()
	{
		String tableName = (String)((StateGeneric<?>)createDependency(
                REQ_STATE_SW_RAD_TABLE)).value;		

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

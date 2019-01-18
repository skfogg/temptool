package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;


//FIXME: Implement an estimate that can handle cloudy days.
/**
 * Controls the longwave radiation emissivity of the air, or the fraction
 * of perfect "black-body" emission of longwave radiation at a given
 * temperature.
 * 
 * <p>This implementation currently considers emissivity as a function of air temperature
 * only.  This is only appropriate on clear days.</p>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li><a href="http://www3.interscience.wiley.com/journal/114054884/abstract" target="_BLANK">Swinbank, W. C. (1963)</a> 
 * Long-wave radiation from clear skies.  Quarterly Journal of the Royal Meteorological Society 89(381) 339-348.</li>
 * </ul>
 * 
 * @author robert.payn
 */
public class Emissivity extends AutoDynamDouble {
	
	
	private static final String REQ_STATE_TEMP_K = "TempKelvin";
	private static final String REQ_STATE_VAP_PRESS = "VaporPressure";
	/**
	 * Temperature of air (&deg;K)
	 */
	private StateDouble tempK;
	
	/**
	 * Vapor pressure of air
	 */
	private StateDouble vapPressAir;
	
	/**
	 * Calculate the emissivity of the air
	 * 
	 * <p>Function of air temperature only (Swinbank 1963)</p>
	 * 
	 * @return emissivity (fraction of radiation energy)
	 */
	
	@Override
	public double calculate() 
	{

		//return 9.062e-06 * Math.pow(tempK.value, 2.0);
		
		// following heat source
		//return 1.72 * Math.pow(((0.1*vapPressAir.value)/(tempK.value)), 0.1428571) * 1.22;
		
		//following Brutsaert 1984:
		return 1.72 * Math.pow(((0.1*vapPressAir.value)/(tempK.value)), (1.0/7.0));
		
	}

	/**
	 * Calculate the initial emissivity of the air
	 * 
	 * @return emissivity (fraction of radiation energy)
	 */
	@Override
	public double initialize() {

		return calculate();
		
	}

	/**
	 * Define the state dependencies for calculating emissivity of air
	 * 
	 * @see TempKelvin
	 */

	@Override
	public void setCalcDeps()
	{
		tempK = (StateDouble)createDependency(REQ_STATE_TEMP_K);
		vapPressAir = (StateDouble)createDependency(REQ_STATE_VAP_PRESS);
		
		//warning - does not properly simulate emissivity on cloudy days.
	}

}

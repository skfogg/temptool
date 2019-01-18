package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;


/**
 * Controls the vapor pressure in the air
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Evans, E. C., G. R. McGregor, and G. E. Petts (1998) River energy budgets with special reference to 
 * 		river bed processes. Hydrological Processes 12, 575-595.</li>
 * <li>Murray, F. W. (1967) On the computation of saturation vapour pressure. 
 * 		Journal of Applied Meteorology 6(1), 203-204.</li>
 * </ul>
 * 
 * @author robert.payn
 *
 */
public class VaporPressure extends AutoDynamDouble {
	private static final String REQ_STATE_HUMID_RELATIVE = "HumidityRelative";	
	private static final String REQ_STATE_TEMP_C = "Temp";
	
	
	private StateDouble vaporPressureSat;
	/**
	 * Relative humidity (%)
	 */
	private StateDouble humidityRelative;
	/**
	 * Air temperature (&deg;C)
	 */
	private StateDouble tempCelsius;

	/**
	 * Calculate the vapor pressure in the air
	 * 
	 * @return Vapor pressure (mbar)
	 */
	@Override
	public double calculate() {

		return (humidityRelative.value / 100) * 
				6.1275 * Math.exp((17.2693882 * tempCelsius.value) / (tempCelsius.value + 237.3));
		
	//	return (humidityRelative.value / 100) * vaporPressureSat.value;
	}

	/**
	 * Calculate the initial vapor pressure in the air
	 * 
	 * @return Vapor pressure (mbar)
	 */
	@Override
	public double initialize() {
		return calculate();	
	}

	/**
	 * Define the state dependencies for calculating vapor pressure
	 * 
	 * @see HumidityRelative
	 * @see TempCelsius
	 * @see TempKelvin
	 */
	@Override
	public void setCalcDeps() 
	{

		humidityRelative = (StateDouble)createDependency(REQ_STATE_HUMID_RELATIVE);
		tempCelsius = (StateDouble)createDependency(REQ_STATE_TEMP_C);
		
		/*
		Face[] atmExchFace = ((Cell)holon).getFacesArray(
				getCurrencyName(),
				"heat.atmexchange.mechanistic"
				);
		
		
		vaporPressureSat = (StateDouble)createDependency(
				atmExchFace[0].getEdge().getToCell(),
				"VaporPressureSat"
				);
		*/
	}
	
	/*
	@Override
	public void setInitDeps()
	{

		humidityRelative = (StateDouble)createDependency(REQ_STATE_HUMID_RELATIVE);
		tempCelsius = (StateDouble)createDependency(REQ_STATE_TEMP_C);

	}
*/
}

package edu.montana.cerg.tempsignal.heat.cell.channel.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;


/**
 * Controls the saturated vapor pressure at the water temperature
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
 */
public class VaporPressureSat extends AutoDynamDouble {
	
	private static final String REQ_STATE_TEMP_C = "Temp";
			
	
	/**
	 * Water temperature (&deg;C)
	 */
	private StateDouble tempCelsius;

	/**
	 * Calculates the saturated vapor pressure at the water temperature
	 * 
	 * @return Vapor pressure (mbar)
	 */
	@Override
	public double calculate() 
	{
		return 6.1275 * Math.exp(17.2693882 * (tempCelsius.value / (tempCelsius.value + 237.3)));	
	}

	/**
	 * Calculates the initial saturated vapor pressure at the water temperature
	 * 
	 * @return Vapor pressure (mbar)
	 */
	@Override
	public double initialize() 
	{
		return calculate();	
	}

	/**
	 * Define the state dependencies for calculating saturated vapor pressure
	 * 
	 * @see TempCelsius
	 * @see TempKelvin
	 */
	@Override
	public void setCalcDeps() 
	{
		

		
		tempCelsius = (StateDouble)createDependency(
				edu.montana.cerg.tempsignal.heat.cell.atmosphere.Temp.class.getSimpleName()
				);
	}

}

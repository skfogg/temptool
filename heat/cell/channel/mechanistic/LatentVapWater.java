package edu.montana.cerg.tempsignal.heat.cell.channel.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;


/**
 * Controls the latent heat of vaporization of water at the
 * current temperature
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Evans, E. C., G. R. McGregor, and G. E. Petts (1998) River energy budgets with special reference to 
 * river bed processes. Hydrological Processes 12, 575-595.</li>
 * </ul>
 * 
 * @author robert.payn
 */
public class LatentVapWater extends AutoDynamDouble {
	
	private static final String REQ_STATE_TEMP = "Temp";
	/**
	 * Water temperature (&deg;C)
	 */
	private StateDouble tempCelsius;

	/**
	 * Calculate the latent heat of vaporization of water based on temperature
	 * 
	 * @return Latent heat of vaporization (kJ kg<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{	
		return 2499.64 - (2.51 * tempCelsius.value);	
	}

	/**
	 * Calculate the initial latent heat of vaporization of water based on temperature
	 * 
	 * @return Latent heat of vaporization (kJ kg<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() {

		return calculate();
		
	}

	/**
	 * Define the state dependencies for calculation of latent heat of water
	 * 
	 * @see TempCelsius
	 */
	@Override
	public void setCalcDeps() {

		tempCelsius = (StateDouble)createDependency(REQ_STATE_TEMP);
		
	}

}

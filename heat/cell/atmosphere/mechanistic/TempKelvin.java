package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;

import edu.montana.cerg.tempsignal.heat.Constants;


/**
 * Controls the air temperature (in Kelvin)
 * 
 * @author robert.payn
 */
public class TempKelvin extends AutoDynamDouble {

	private static final String REQ_STATE_TEMP = "Temp";
	/**
	 * Air temperature (&deg;C)
	 */
	private StateDouble tempCelsius;
	
	/**
	 * Calculate the air temperature in Kelvin
	 * 
	 * @return air temperature (K)
	 */
	@Override
	public double calculate() {

		return tempCelsius.value + Constants.C_MINUS_K;

	}

	/**
	 * Calculate the initial air temperature in Kelvins
	 * 
	 * @return air temperature (K)
	 */
	@Override
	public double initialize() {
		
		return calculate();
		
	}

	/**
	 * Define the dependencies for calculating air temperature in Kelvin
	 * 
	 * @see TempCelsius
	 */
	@Override
	public void setCalcDeps() {

		tempCelsius = (StateDouble)createDependency(
				edu.montana.cerg.tempsignal.heat.cell.atmosphere.compoundsin.Temp.class.getSimpleName()
				);
		
	}

}

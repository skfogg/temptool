package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.ManualDynamDouble;



/**
 * Controls the ratio of latent heat transfer to sensible heat transfer
 * across the air-water interface (the Bowen ratio, Bowen 1926)
 * 
 * <p>This is a simple calculator called by <code>Heat</code>.
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li><a href="http://prola.aps.org/abstract/PR/v27/i6/p779_1" target="_BLANK">Bowen, I. S. (1926)</a> The ratio of heat losses by conduction 
 * and by evaporation from any water surface.  Physical Review 27, 779-787.</li>
 * </ul>
 * 
 * @see Heat
 * @author robert.payn
 */
public class BowenRatio extends ManualDynamDouble {

	private static final String REQ_STATE_PRESSURE_ATM = "Pressure";
	private static final String REQ_STATE_TEMP_AIR = "Temp";
	private static final String REQ_STATE_TEMP_WATER = "Temp";
	private static final String REQ_STATE_VAP_PRESSURE = "VaporPressure";
	private static final String REQ_STATE_VAP_PRESSURE_SAT = "VaporPressureSat";
	
	
	/**
	 * Atmospheric pressure (mbar)
	 */
	private StateDouble pressureAtm;
	/**
	 * Air temperature (&deg;C)
	 */
	private StateDouble tempAirC;
	/**
	 * Water temperature (&deg;C)
	 */
	private StateDouble tempWaterC;
	/**
	 * Vapor pressure in the air (mbar)
	 */
	private StateDouble vaporPressureAir;
	/**
	 * Saturated vapor pressure at the water temperature (mbar)
	 */
	private StateDouble vaporPressureSat;
	
	/**
	 * Calculates the ratio of latent heat transfer to sensible heat transfer
	 * across the air-water interface
	 * 
	 * @return Bowen ratio (fraction of heat flux)
	 */
	@Override
	public double calculate() 
	{

		return 0.00061 * pressureAtm.value	* ((tempWaterC.value - tempAirC.value) / (vaporPressureSat.value - vaporPressureAir.value)) ;
	}

	/**
	 * Calculates the initial ratio of latent heat transfer to sensible heat transfer
	 * across the air-water interface
	 * 
	 * @return Bowen ratio (fraction of heat flux)
	 */
	@Override
	public double initialize() 
	{

		return calculate();
		
	}

	/**
	 * Defines the dependencies for calculation of the Bowen ratio
	 * 
	 * @see wren.heat.patch.airheat.Pressure
	 * @see wren.heat.patch.airheat.TempCelsius
	 * @see wren.heat.patch.liquid.TempCelsius
	 * @see wren.heat.patch.airheat.VaporPressure
	 * @see wren.heat.patch.liquid.VaporPressureSat
	 */
	@Override
	public void setCalcDeps()
	{
		
		Cell waterCell = ((Face)holon).getEdge().getToCell();
		Cell airCell = ((Face)holon).getEdge().getFromCell();
		
		pressureAtm = (StateDouble)createDependency(airCell,REQ_STATE_PRESSURE_ATM);
				
		tempAirC = (StateDouble)createDependency(
				airCell,
				edu.montana.cerg.tempsignal.heat.cell.atmosphere.Temp.class.getSimpleName()
				);
		tempWaterC = (StateDouble)createDependency(waterCell,REQ_STATE_TEMP_WATER);
		
		vaporPressureAir = (StateDouble)createDependency(airCell,REQ_STATE_VAP_PRESSURE);
		vaporPressureSat = (StateDouble)createDependency(waterCell,REQ_STATE_VAP_PRESSURE_SAT);
	}
	
}

package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.InitDynamDouble;
import org.neosimulation.neo.user.ManualDynamDouble;

import edu.montana.cerg.tempsignal.heat.TempSignalModel;



/**
 * Controls the evaporation or condensation flux rate between surface water and air
 * 
 * <p>This is a simple calculator called by <code>Heat</code>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Evans, E. C., G. R. McGregor, and G. E. Petts (1998) River energy budgets with special reference to 
 * 		river bed processes. Hydrological Processes 12, 575-595.</li>
 * <li>Penman, H. L. (1948) Natural evaporation from open water, bare soil, and grass.  
 * 		Proceedings of the Royal Society of London A 193, 120-145.</li>
 * <li>Webb, B. W. and Y. Zhang (1997) Spatial and seasonal variability in the components of the river heat budget.
 * 		Hydrological Processes 11, 79-101.</li>
 * </ul>
 * 
 * @see Heat
 * @author robert.payn
 */
public class Evaporation extends ManualDynamDouble {
	
	public static final String REQ_STATE_PENMAN_INT = "PenmanIntercept" ;
	public static final String REQ_STATE_PENMAN_SLOPE = "PenmanSlope";
	public static final String REQ_STATE_SPEED_WIND = "SpeedWind";
	public static final String REQ_STATE_VAPOR_PRESSURE = "VaporPressure";
	public static final String REQ_STATE_VAPOR_PRESSURE_SAT = "VaporPressureSat";
	/**
	 * Value of the intercept for the empirical Penman linear wind function
	 */
	private StateDouble penmanInt;

	/**
	 * Value of the slope for the empirical Penman linear wind function
	 */
	private StateDouble penmanSlope;	

	/**
	 * Wind speed (m sec<sup><small>-1</small></sup>)
	 */
	private StateDouble speedWind;
	
	/**
	 * Vapor pressure in the air (mbar)
	 */
	private StateDouble vaporPressureAir;
	/**
	 * Saturated vapor pressure at water temperature (mbar)
	 */
	private StateDouble vaporPressureSat;

	/**
	 * Calculates the flux rate of evaporation (negative) or condensation (positive)
	 * 
	 * @return Evaporation rate (m sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{
		return ((penmanInt.value + (penmanSlope.value * speedWind.value)) 
				* (vaporPressureAir.value - vaporPressureSat.value)) / 8.64e7;	
	}

	/**
	 * Calculates the initial flux rate of evaporation.
	 * 
	 * @see wren.heat.utils.Constants
	 * @return Evaporation rate (mm day<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() 
	{

		return calculate();
	}

	@Override
	public void setCalcDeps()
	{
 		
		Cell fromCell = ((Face)holon).getEdge().getFromCell();
 		Cell toCell = ((Face)holon).getEdge().getToCell();  
 
		penmanInt = (StateDouble)createDependency(REQ_STATE_PENMAN_INT);
		penmanSlope = (StateDouble)createDependency(REQ_STATE_PENMAN_SLOPE);
		
		speedWind = (StateDouble)createDependency(fromCell,REQ_STATE_SPEED_WIND);
		vaporPressureAir = (StateDouble)createDependency(fromCell,REQ_STATE_VAPOR_PRESSURE);
		vaporPressureSat = (StateDouble)createDependency(toCell,REQ_STATE_VAPOR_PRESSURE_SAT);
	}


}

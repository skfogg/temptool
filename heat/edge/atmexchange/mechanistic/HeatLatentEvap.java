package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.ManualDynamDouble;
import edu.montana.cerg.tempsignal.heat.Constants;


/**
 * Controls the heat flux due to evaporation and condensation of surface water
 * 
 * <p>This is a simple calculator called by <code>Heat</code>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Evans, E. C., G. R. McGregor, and G. E. Petts (1998) River energy budgets with special reference to 
 * 		river bed processes. Hydrological Processes 12, 575-595.</li>
 * <li>Webb, B. W. and Y. Zhang (1997) Spatial and seasonal variability in the components of the river heat budget.
 * 		Hydrological Processes 11, 79-101.</li>
 * </ul>
 * 
 * @see Heat
 * @author robert.payn
 */
public class HeatLatentEvap extends ManualDynamDouble {
	private static final String REQ_STATE_EVAP = "Evaporation";
	private static final String REQ_STATE_LATENT_VAP_WATER = "LatentVapWater"; 
	
	/**
	 * Evaporation rate from surface water (negative) or
	 * condensation rate to surface water (positive)
	 * (mm day<sup><small>-1</small></sup>)
	 */
	private StateDouble evaporation;
	private ManualDynamDouble evaporationDynam;
	/**
	 * Latent heat of vaporization for water (kJ kg<sup><small>-1</small></sup>)
	 */	
	private StateDouble latentVapWater;


	@Override
	public double initialize() 
	{
		evaporationDynam.doInitialize();
		return 0;		
	}
	/**
	 * Calculates the heat removed (negative) or added (positive) to the adjacent
	 * surface water patch due to evaporation or condensation, respectively
	 * 
	 * @return Heat flux due to latent heat of vaporization
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{
		evaporationDynam.doUpdate();
		// Note that H2Okgm3 is the density of liquid water in kg per cubic meter
		return (evaporation.value * Constants.DENS_H2O_LIQ * latentVapWater.value);
		
	}


		/**
	 * Define the state dependencies for calculation of heat flux due to latent heat of
	 * vaporization
	 */
	@Override
	public void setCalcDeps()
	{
		evaporation = (StateDouble)createDependency(REQ_STATE_EVAP);
		
		Cell toCell = ((Face)holon).getEdge().getToCell();
		latentVapWater = (StateDouble)createDependency(toCell,REQ_STATE_LATENT_VAP_WATER);
	}
	
	@Override
	public void setRegistrations()
	{
		evaporationDynam = (ManualDynamDouble)setRegistration(REQ_STATE_EVAP);
	}



	
	

}

package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.ManualDynam;
import org.neosimulation.neo.user.ManualDynamDouble;

/**
 * Controls the sensible heat flux, or the heat flux due to conduction
 * and convection across the air-water interface
 * 
 * <p>This is a simple calculator called by <code>Heat</code>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Bowen, I. S. (1926) The ratio of heat losses by conduction 
 * and by evaporation from any water surface.  Physical Review 27, 779-787.</li>
 * </ul>
 * 
 * @see Heat
 * @author robert.payn
 */
public class HeatSensible extends ManualDynamDouble {
	private static final String REQ_STATE_BOWEN_RATIO = "BowenRatio";
	private static final String REQ_STATE_HEAT_LATENT_EVAP = "HeatLatentEvap";
	
	/**
	 * Bowen ratio (fraction of heat flux)
	 */
	private StateDouble bowenRatio;
	private ManualDynamDouble bowenRatioDynam;

	/**
	 * Latent heat flux due to liquid-gas state change
	 * (kJ m<sup><small>-2</sup></small> sec<sup><small>-1</sup></small>)
	 */
	private StateDouble heatLatentEvap;
	//private ManualDynamDouble heatLatentDynam;
	
	/**
	 * Calculate the sensible heat flux
	 * 
	 * @return Heat flux
	 *		(kJ m<sup><small>-2</sup></small> sec<sup><small>-1</sup></small>)
	 */
	@Override
	public double calculate() 
	{
		bowenRatioDynam.doUpdate();
		//heatLatentDynam.doUpdate();
		return bowenRatio.value * heatLatentEvap.value;
		
	}

	/**
	 * Calculate the initial sensible heat flux
	 * 
	 * @return Heat flux
	 *		(kJ m<sup><small>-2</sup></small> sec<sup><small>-1</sup></small>)
	 */
	@Override
	public double initialize()
	{
		bowenRatioDynam.doInitialize();
		//heatLatentDynam.doInitialize();
		return calculate();
	}

	/**
	 * Define the state dependencies for calculation of sensible heat flux
	 * 
	 * @see BowenRatio
	 * @see HeatLatentEvap
	 */
	@Override
	public void setCalcDeps() 
	{
		bowenRatio = (StateDouble)createDependency(REQ_STATE_BOWEN_RATIO);
		
		heatLatentEvap = (StateDouble)createDependency(REQ_STATE_HEAT_LATENT_EVAP);
	}
	
	@Override
	
	public void setRegistrations()
	{
		bowenRatioDynam = (ManualDynamDouble)setRegistration(REQ_STATE_BOWEN_RATIO);
		
		//heatLatentDynam = (ManualDynamDouble)setRegistration(REQ_STATE_HEAT_LATENT_EVAP);
	}
	

	
}

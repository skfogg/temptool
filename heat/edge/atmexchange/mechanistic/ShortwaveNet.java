package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;


import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.InitDynam;
import org.neosimulation.neo.user.ManualDynam;
import org.neosimulation.neo.user.ManualDynamDouble;


/**
 * Controls the net shortwave radiation (unreflected radiation entering water)
 * 
 * <p>This is a simple calculator called by <code>Heat</code>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Webb, B. W. and Y. Zhang (1997) Spatial and seasonal variability in the components of the river heat budget.
 * 		Hydrological Processes 11, 79-101.</li>
 * </ul>
 * 
 * @see Heat
 * @author Rob Payn
 */
public class ShortwaveNet extends ManualDynamDouble {

	
	public static final String REQ_STATE_REFLECT_SHORTWAVE = "ReflectShortwave";
	public static final String REQ_STATE_FLUX_SHORTWAVE = "ShortwaveFlux"; 
	
	/**
	 * Flux of shortwave radiation
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	private StateDouble fluxShortwave;  
	private ManualDynamDouble fluxShortwaveDynam;
	/**
	 * Incoming shortwave radiation that is transmitted into water (fraction of energy)
	 */
	private double transmitShortwave;  
	/**
	 * State containing albedo of water surface
	 */
	private StateDouble reflectShortwave;

	/**
	 * Calculates the initial rate of heat transfer due to shortwave radiation
	 * 
	 * @return Rate of energy transfer (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() 
	{
		
		fluxShortwaveDynam.doInitialize();
		transmitShortwave = 1 - reflectShortwave.value;
		return calculate();
	}

	
	/**
	 * Calculates the rate of heat transfer due to shortwave radiation
	 * 
	 * @return Rate of energy transfer (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{	
		fluxShortwaveDynam.doUpdate();
		//return fluxShortwave.value * transmitShortwave;
		return fluxShortwave.value;
	}

	@Override
	public void setCalcDeps() 
	{
		fluxShortwave = (StateDouble)createDependency(REQ_STATE_FLUX_SHORTWAVE);
	}

    
    @Override
    public void setInitDeps()
    {
    	reflectShortwave = (StateDouble)createDependency(REQ_STATE_REFLECT_SHORTWAVE);
		fluxShortwave = (StateDouble)createDependency(REQ_STATE_FLUX_SHORTWAVE);
    }
    
    @Override
    public void setRegistrations()
    {
    	fluxShortwaveDynam = (ManualDynamDouble)setRegistration(REQ_STATE_FLUX_SHORTWAVE);
    }
}

package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.InitDynamDouble;
import org.neosimulation.neo.user.ManualDynam;
import org.neosimulation.neo.user.ManualDynamDouble;


/**
 * <p>Calculates the net longwave radiation exchanged between surface water
 * and air.</p>
 * <p>
 * Negative value indicates energy loss from water<br>
 * Positive value indicates energy gain to water
 * <p>
 * 
 * <p>This is a simple calculator called by <code>Heat</code>
 * 
 * <p><b>References:</b></p>
 * <ul style="list-style-type: none; line-height: 200%">
 * <li>Anderson, E. R. (1952) Energy budget studies. Water loss investigations: 
 * 		Vol. 1 Lake Hefner Studies, Technical Report, United States Geological 
 * 		Survey Circular  229: 71-119.</li>
 * <li>Evans, E. C., G. R. McGregor, and G. E. Petts (1998) River energy budgets with special reference to 
 * 		river bed processes. Hydrological Processes 12, 575-595.</li>
 * </ul>
 * 
 * @see Heat
 * @author Rob Payn
 */
public class LongwaveNet extends ManualDynamDouble {

	public static final String REQ_STATE_REFLECT_LONG_WAVE = "ReflectLongwave";
	public static final String REQ_STATE_LONG_WAVE_WATER = "LongwaveFluxWater";
	public static final String REQ_STATE_LONG_WAVE_AIR = "LongwaveFluxAir";  
	/**
	 * Longwave radiation from water (kJ m<sup><small>-2</sup></small> sec<sup><small>-1</sup></small>)
	 */
	private StateDouble longwaveWater;
	private ManualDynamDouble longwaveWaterDynam;
	/**
	 * Longwave radiation from air (kJ m<sup><small>-2</sup></small> sec<sup><small>-1</sup></small>)
	 */
	private StateDouble longwaveAir; 
	private ManualDynamDouble longwaveAirDynam;
	/**
	 * Incoming longwave radiation that is transmitted into water (fraction of energy)
	 */
	private double transmitLongwave;
	/**
	 * State containing reflectivity of longwave radiation (fraction of energy)
	 */
	private StateDouble reflectLongwave;
	
	private double nonsense = 0;

	/**
	 * Calculates heat transfer due to longwave radiation
	 * 
	 * @return current heat transfer due to longwave radiation 
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double calculate() 
	{
		
		longwaveWaterDynam.doUpdate();
		longwaveAirDynam.doUpdate();
		
		//return (longwaveAir.value * transmitLongwave) + longwaveWater.value;
		return (longwaveAir.value * 0.96) + longwaveWater.value;
	}

	/**
	 * Calculates the initial heat transfer due to longwave radiation
	 * 
	 * @return initial heat transfer due to longwave radiation 
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() 
	{	
		longwaveWaterDynam.doInitialize();
		longwaveAirDynam.doInitialize();
		transmitLongwave = 1 - reflectLongwave.value;
		return calculate();
	}


	
	/**
	 * Define dependencies for calculating the heat from longwave radiation.
	 * 
	 */
	public void setCalcDeps()
	{
		
		longwaveAir=(StateDouble)createDependency(REQ_STATE_LONG_WAVE_AIR);
		longwaveWater=(StateDouble)createDependency(REQ_STATE_LONG_WAVE_WATER);

	}
	
    @Override
    public void setRegistrations()
    {
    	longwaveAirDynam = (ManualDynamDouble)setRegistration(REQ_STATE_LONG_WAVE_AIR);
    	longwaveWaterDynam = (ManualDynamDouble)setRegistration(REQ_STATE_LONG_WAVE_WATER);
    }

    @Override
    public void setInitDeps() {
    	reflectLongwave = (StateDouble)createDependency(REQ_STATE_REFLECT_LONG_WAVE);
		longwaveAir=(StateDouble)createDependency(REQ_STATE_LONG_WAVE_AIR);
		longwaveWater=(StateDouble)createDependency(REQ_STATE_LONG_WAVE_WATER);
    }
}

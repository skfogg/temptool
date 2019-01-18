package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.ManualDynamDouble;





/**
 * Controls the flux of shortwave radiation transmitted through 
 * air cell on "from" side of edge to water cell on "to" side of edge
 * 
 * <p>This is a simple calculator called by <code>Heat</code>.
 * 
 * @see Heat
 * @author robert.payn
 */
public class ShortwaveFlux extends ManualDynamDouble {
		
	/**
	 * Solar shortwave flux (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	private StateDouble shortwaveFlux;

	@Override
	public double calculate()
	{
		return shortwaveFlux.value;
	}
	/**
	 * Calculates the initial flux of shortwave radiation from air to water
	 * 
	 * @return Shortwave radiation flux 
	 * (kJ m<sup><small>-2</small></sup> sec<sup><small>-1</small></sup>)
	 */
	@Override
	public double initialize() 
	{
		return calculate();	
	}

	/**
	 * Gets the references needed to calculate shortwave radiation leaving air
	 * 
	 * <p>Dependencies are NOT set to avoid circular dependencies, because this 
	 * is a simple calculator called by another updater.</p>
	 */

	@Override
	public void setCalcDeps()
	{
		Cell airCell = ((Face)holon).getEdge().getFromCell();
		Face solarFace = airCell.getFacesArray("heat","heat.solarradflux.mechanistic")[0];
		shortwaveFlux = (StateDouble)createDependency(solarFace,"ShortwaveFlux");
		
	}
	
	@Override
	public void setInitDeps()
	{
		
		Cell airCell = ((Face)holon).getEdge().getFromCell();
		Face solarFace = airCell.getFacesArray("heat","heat.solarradflux.mechanistic")[0];
		shortwaveFlux = (StateDouble)createDependency(solarFace,"ShortwaveFlux");
		
	}

}

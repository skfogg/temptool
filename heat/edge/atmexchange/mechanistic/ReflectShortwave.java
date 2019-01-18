package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;


import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.InitDynamDouble;

import edu.montana.cerg.tempsignal.heat.Constants;


/**
 * <p>Controls the shortwave reflectivity of the interface between water and air.</p>
 * 
 * <p>Uses the value in the initialization tables, if present.  Otherwise, uses
 * the default value in <code>wren.heat.utils.Constants</code>
 * 
 * @author robert.payn
 *
 */
public class ReflectShortwave extends InitDynamDouble 
{

	/**
	 * Calculates the initial shortwave reflectivity
	 * 
	 * @return Reflectivity (fraction of energy)
	 */
	
	public double initialize() 
	{
		// Use the default value if an initial value was not provided
		if (stateVal.isNull()) {
			return Constants.DEF_SW_REFLECT;
		} else {
			return ((StateDouble)stateVal).value;  
		}		
		
	}
	

	/**
	 * no dependencies other than the constant
	 */
	@Override
	public void setInitDeps() 
	{
		
	}

}

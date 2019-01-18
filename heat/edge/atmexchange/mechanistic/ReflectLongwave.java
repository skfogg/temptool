package edu.montana.cerg.tempsignal.heat.edge.atmexchange.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.InitDynamDouble;

import edu.montana.cerg.tempsignal.heat.Constants;


/**
 * <p>Controls the longwave reflectivity of the interface between water and air.</p>
 * 
 * <p>Uses the value in the initialization tables, if present.  Otherwise, uses
 * the default value in <code>wren.heat.utils.Constants</code>
 * 
 * @see wren.heat.utils.Constants
 * @author robert.payn
 *
 */
public class ReflectLongwave extends InitDynamDouble {

	/**
	 * Calculates the initial longwave reflectivity
	 * 
	 * @return Reflectivity (fraction of energy)
	 */
	@Override
	public double initialize() {
		
		// Use the default value if an initial value was not provided
		if (stateVal.isNull()) {
			return Constants.DEF_LW_REFLECT;
		} else {
			return ((StateDouble)stateVal).value;
		}
		
	}


	/**
	 * no dependencies
	 */
	@Override
	public void setInitDeps() 
	{	
		
	}

}

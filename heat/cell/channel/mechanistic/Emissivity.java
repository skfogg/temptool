package edu.montana.cerg.tempsignal.heat.cell.channel.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.InitDynamDouble;

import edu.montana.cerg.tempsignal.heat.Constants;


/**
 * <p>Controls the emissivity of water.</p>
 * 
 * <p>Uses the value in the initialization tables, if present.  Otherwise, uses
 * the default value in <code>wren.heat.utils.Constants</code>
 * 
 * @see wren.heat.utils.Constants
 * @author robert.payn
 *
 */
public class Emissivity extends InitDynamDouble {

	/**
	 * Calculates the initial emissivity
	 * 
	 * @return Emissivity (fraction of energy)
	 */
	@Override
	public double initialize() {
		
		// Use the default value if an initial value was not provided
		if (stateVal.isNull()) {
			return Constants.DEF_EMISS_H2O;
		} else {
			return ((StateDouble)stateVal).value;
		}
		
	}

	/**
	 * No dependencies
	 */
	@Override
	public void setInitDeps() {}

}

package edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;

public class StartDay extends AutoDynamDouble{

	public StateDouble startDOY;
	@Override
	public double calculate() {
		return startDOY.value;
	}

	@Override
	public void setCalcDeps() {
		startDOY = (StateDouble)createDependency(Heat.REQ_STATE_START_DAY);
	}
}

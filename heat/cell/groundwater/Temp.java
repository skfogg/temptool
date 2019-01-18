package edu.montana.cerg.tempsignal.heat.cell.groundwater;

import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;

public class Temp extends AutoDynamDouble {
	
    private StateDouble temp;

    @Override
    public double calculate()
    {
        return temp.value;
    }

    @Override
    public void setCalcDeps()
    {        
        temp = (StateDouble)createDependency("Temp");        
    }

}

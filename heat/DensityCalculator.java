package edu.montana.cerg.tempsignal.heat;

import org.neosimulation.neo.framework.stateval.StateDouble;

public  class DensityCalculator {
    
    public static DensityCalculator createDensityCalculator(StateDouble density)
    {
        if (density == null)
            return new DensityCalculator();
        else
            return new StateDensityCalculator(density);
    }

	public double getDensity()
	{
	    return HeatCurrency.DENSITY_WATER_10;
	}

}

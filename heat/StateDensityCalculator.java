package edu.montana.cerg.tempsignal.heat;

import org.neosimulation.neo.framework.stateval.StateDouble;

public class StateDensityCalculator extends DensityCalculator {
    
    public StateDouble density;

    public StateDensityCalculator(StateDouble density)
    {
        this.density = density;
    }

    @Override
    public double getDensity()
    {
        return density.value;
    }

}

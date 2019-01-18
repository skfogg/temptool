package edu.montana.cerg.tempsignal.heat.face.solarradflux.mechanistic;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.time.TimeKeeper;
import org.neosimulation.neo.user.AutoDynamDouble;

import edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic.Pressure;
import edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic.StartDay;
import edu.montana.cerg.tempsignal.heat.cell.atmosphere.mechanistic.Heat;
/**
 * Controls the flux of shortwave radiation transmitted through 
 * air cell on "from" side of edge to water cell on "to" side of edge
 * 
 * following some biophysics textbook
*/
public class ShortwaveFlux extends AutoDynamDouble {
	private StateDouble pressure;
	private StateDouble latitude;
	private TimeKeeper timeKeeper;
	private double jDay;
	private double timeHours;
	private double degToRad = (2*Math.PI/360);
	private StateDouble solarConstant;
	private StateDouble startDay;
	private StateDouble longitudeCorrection;
	private StateDouble transmissivity;
	
	@Override
	public double calculate() 
	{	
		jDay = (Math.floor(timeKeeper.getCurrentTime()/86400)) + startDay.value;
		timeHours = (timeKeeper.getCurrentTime()/86400 - Math.floor(timeKeeper.getCurrentTime()/86400)) * 24;
		return groundPerpRad() * Math.max((cosZenithAngle()),0.0) ;
	}

	private double cosZenithAngle() {
		return (Math.sin(latitude.value*degToRad)*Math.sin(solarDeclination()*degToRad)+
				Math.cos(latitude.value*degToRad)*Math.cos(degToRad*solarDeclination())*Math.cos(degToRad*degreesFromSolarNoon()));
	}
	

	private double degreesFromSolarNoon() {
		return ( 15 * (timeHours - solarNoonTime()));
	}

	private double solarNoonTime() {

		return 12 - longitudeCorrection.value - equationTime();
	}

	private double equationTime() {
		double f = degToRad*(-279.575 + 0.9856*jDay);
		return (-104.7*Math.sin(f)+596.2*Math.sin(2*f)+4.3*Math.sin(3*f)-12.7*Math.sin(4*f)
				-429.3*Math.cos(f)-2.0*Math.cos(2*f)+19.3*Math.cos(3*f))/
				3600;	
	}

	private double solarDeclination() {
		return (1/degToRad)*Math.asin(0.39785*Math.sin(degToRad*(278.97 + 0.9856*jDay + 1.9165*Math.sin(degToRad*(356.6 + 0.9856*jDay)))));
	}

	private double groundPerpRad() {
		return spacePerpRad()*Math.pow(transmissivity.value, opticalAir());
	}

	private double opticalAir() {
		return pressure.value / (1013 * Math.cos(degToRad*cosZenithAngle()));
	}

	private double spacePerpRad() {
		return solarConstant.value;
	}

	@Override
	public double initialize() 
	{
		return calculate();	
	}

	
	public void setCalcDeps()
	{
		timeKeeper = holon.getSimulationModel().getTimeKeeper();	
		Cell atmCell = ((Face)holon).getCell();
				
		startDay = (StateDouble)createDependency(atmCell, StartDay.class.getSimpleName());
		pressure = (StateDouble)createDependency(atmCell, Pressure.class.getSimpleName());
		latitude = (StateDouble)createDependency(atmCell,Heat.REQ_STATE_LAT);
		longitudeCorrection = (StateDouble)createDependency(atmCell,Heat.REQ_STATE_LONG_CORRECT);
		transmissivity = (StateDouble)createDependency(atmCell,Heat.REQ_STATE_TRANSMIT);
		solarConstant = (StateDouble)createDependency(atmCell,Heat.REQ_STATE_SOLAR_CONST);

	}

}

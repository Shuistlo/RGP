package Localization;

import lejos.robotics.Color;

public class Localization {

	public static final double SENSOR_FAIL = 0.8;
	public static final double SENSOR_HIT = 0.2;
	public static final double UNDERSHOOT = 0.1;
	public static final double FORWARD = 0.9;

	//1 Dimensional representation of the line the robot is travelling on.
	private static final Boolean[] LINE= {true,true,false,false,true,true,true,false,false,true,true,true,false,true,true,true,false,false,false,true,true,true};

	//array to hold probabilities of each position.
	private double[] Pr;

	public Localization(){
		Pr = new double[LINE.length -1];
		for(double d : Pr){
			d = 1/LINE.length;
		}
	}

	/** Uses Markov Localization to estimate the most likely position of the robot,
	*	using a pre-established map.
	*
	*	@param colour	reading from colour sensor
	*	@param move		boolean to see if robot is moving
	*/
	public double[] sensorFilter(float colour){
		//constant for normalisation of probabilities
		int n = 0;

　
		//calculating probabilities in Pr after a sensor reading
		for(int i = 0; i < LINE.length; i++){

			//if colour sensor reading matches the map reading
			if(((colour < 0.1 && LINE[i]) || !((colour < 0.1) && !LINE[i]))){
				Pr[i] = Pr[i] * SENSOR_HIT;
				n += Pr[i];
			} else {
				Pr[i] = Pr[i] * SENSOR_FAIL;
				n += Pr[i];
			}

		}

		//Normalising probabilities
		for(double d : Pr){
			d = d/n;
		}

		return Pr;
	}

	public double[] motionUpdate(){
		for(int i = 0; i < LINE.length; i++){
			if(i == 0){
				Pr[i] *= UNDERSHOOT;
			} else {
				Pr[i] = Pr[i-1]*FORWARD + Pr[i]*UNDERSHOOT;
			}
		}

		return Pr;
	}

	public Cell mapLocation(double[] p){
		double max;
		int n;

		for(double d : p){
			double m;

			if(d>m){
				m = d;
				max = m;
			} else if(d == m && m > 0){
				n++;
			}

		}

		if(n>1){
			n = 0;
			return null;
		} else {
			n = 0;

			for(;n<p.length;n++){
				if(max>0.2){
					//complete line-map integration
				}
				return null;
			}

		}

	}

}

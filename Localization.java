import lejos.robotics.Color;

public Localization {

	public static final float SENSOR_FAIL = 0.8;
	public static final float SENSOR_HIT = 0.2;
	public static final float UNDERSHOOT = 0.1;
	public static final float FORWARD = 0.9; 
	
	//1 dimentional representation of the line the robot is travelling on.
	private static final Boolean[] MAP = {true, true, false, false, false, true, false, false, false, true, false, false, false, true, true, true, true, true, true, true, true, true};	

	//array to hold probabilities of each position.
	private float[MAP.length-1] Pr;

	public Localization(){
		for(float f : Pr){
			f = 1/MAP.length;
		}
	}

	/** Uses Markov Localization to estimate the most likely position of the robot,
	*	using a pre-established map.
	*
	*	@param colour	reading from colour sensor
	*	@param move		boolean to see if robot is moving
	*/
	public float[] sensorFilter(float colour, Boolean move){
		//constant for normalization of probabilities
		int n = 0;


		//calculating probabilities in Pr after a sensor reading
		for(int i = 0; i < MAP.length; i++){
			
			//if colour sensor reading matches the map reading
			if(((colour == Color.BLUE) && MAP[i]) || !((colour == Color.BLUE) && !MAP[i])){
				Pr[i] = Pr[i] * SENSOR_HIT;
				n += Pr[i];
			} else {
				Pr[i] = Pr[i] * SENSOR_FAIL;
				n += Pr[i];
			}

		}	

		//normalizing probabilities
		for(float f : Pr){
			f /= n;
		}

		//calculating probabilities in Pr after an action 
		for(int i = 0; i < MAP.length; i++){
			if(i == 0){
				Pr[i] *= UNDERSHOOT;
			} else {
				Pr[i] = Pr[i-1]*FORWARD + Pr[i]*UNDERSHOOT;
			}
		}

		return Pr;
	}

}
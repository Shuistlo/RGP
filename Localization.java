import lejos.robotics.Color;

public Localization {

	public static final float SENSOR_FAIL = 0.8;
	public static final float SENSOR_HIT = 0.2;
	public static final float UNDERSHOOT = 0.1;
	public static final float FORWARD = 0.9;

	//1 dimentional representation of the line the robot is travelling on.
	private static final Boolean[] LINE= {true, true, false, false, false, true, false, false, false, true, false, false, false, true, true, true, true, true, true, true, true, true};

	//array to hold probabilities of each position.
	private float[LINE.length-1] Pr;

	public Localization(){
		for(float f : Pr){
			f = 1/LINE.length;
		}
	}

	/** Uses Markov Localization to estimate the most likely position of the robot,
	*	using a pre-established map.
	*
	*	@param colour	reading from colour sensor
	*	@param move		boolean to see if robot is moving
	*/
	public float[] sensorFilter(float colour){
		//constant for normalization of probabilities
		int n = 0;


		//calculating probabilities in Pr after a sensor reading
		for(int i = 0; i < LINE.length; i++){

			//if colour sensor reading matches the map reading
			if(((colour == Color.BLUE) && LINE[i]) || !((colour == Color.BLUE) && !LINE[i])){
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

		return Pr;
	}

	public float[] motionUpdate(){
		for(int i = 0; i < LINE.length; i++){
			if(i == 0){
				Pr[i] *= UNDERSHOOT;
			} else {
				Pr[i] = Pr[i-1]*FORWARD + Pr[i]*UNDERSHOOT;
			}
		}

		return Pr;
	}

	public Pair<int,int> robotLocation(float[] p){
		float max;
		int n;

		for(float f : p){
			float m;

			if(f>m){
				m = f;
				max = m;
			} else if(f == m && m > 0){
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
					//implement method to transpose location on line to location on main map
				}
				return null;
			}

		}

	}

}

public class Localisation {

	public static final double SENSOR_FAIL = 0.8;
	public static final double SENSOR_HIT = 0.2;
	public static final double UNDERSHOOT = 0.1;
	public static final double FORWARD = 0.9;
	public static final double THRESHOLD = 0.1;

	//1 Dimensional representation of the line the robot is travelling on.
	private final Boolean[] LINE= {true,true,false,false,true,true,true,false,false,true,true,true,false,true,true,true,false,false,false,true,true,true};

	//array to hold probabilities of each position.
	private double[] Pr = new double[LINE.length];

	public Localisation(){
		for(int i = 0; i<LINE.length;i++){
			Pr[i] = (double)1/LINE.length;
		}
	}

	/** Uses Markov Localisation to estimate the most likely position of the robot
	*	using a map.
	*
	*	@param colour	reading from colour sensor
	*/

	public void filter(float colour){

		//constant for normalisation of probabilities
		int n = 0;

		//calculating probabilities in Pr after a sensor reading
		for(int i = 0; i < Pr.length; i++){

			//if colour sensor reading matches the map reading
			if(colour < THRESHOLD && LINE[i]){
				Pr[i] *= SENSOR_HIT;
				n += Pr[i];
			} else if(colour > THRESHOLD && LINE[i]){
				Pr[i] *= SENSOR_FAIL;
				n += Pr[i];
			}
		}

		//Normalising probabilities
		for(double d : Pr){
			d = d/n;
		}

		//motion update
		for(int i = 0; i < LINE.length; i++){
			if(i == 0){
				Pr[i] *= UNDERSHOOT;
			} else {
				Pr[i] = Pr[i-1]*FORWARD + Pr[i]*UNDERSHOOT;
			}
		}

	}
	
	public void printProbArray(){
		for(double d : Pr){
			System.out.print(d+", ");
		}
	}

	public Cell mapLocation(){

		double max = 0;
		int n = 0;

		for(double d : Pr){
			if(d>max){
				max = d;
			} else if(d == max){
				n++;
			}
		}

		if(n==1){
			for(n = 0; n<Pr.length; n++){
				if(Pr[n] == max && max > 0.10f){
					if(n>28){
						return new AStarCell(7,6);
					} else if(n>22){
						return new AStarCell(8,5);
					} else if(n>16){
						return new AStarCell(9,4);
					} else if(n>10){
						return new AStarCell(10,3);
					} else if(n>4){
						return new AStarCell(11,2);
					}
				}
			}
		} 

		n = 0;
		return null;
	}

}

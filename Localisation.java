package project;

public class Localisation {

	public static final double SENSOR_FAIL = 0.8;
	public static final double SENSOR_HIT = 0.2;
	public static final double UNDERSHOOT = 0.1;
	public static final double FORWARD = 0.9;
	public static final double THRESHOLD = 0.2;

	//1 Dimensional representation of the line the robot is travelling on.
	private final boolean[] LINE= {true,true,false,false,true,true,true,false,false,true,true,true,false,true,true,true,false,false,false,true,true,true};

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
		System.out.print("[");
		for(double d : Pr){
			System.out.print(d+", ");
		}
		System.out.print("] \n");
	}

	public AstarCell mapLocation(){

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
				if(Pr[n] == max){
					if(n>28){
						return new AstarCell(12,12);
					} else if(n>25){
						return new AstarCell(11,11);
					} else if(n>22){
						return new AstarCell(10,10);
					} else if(n>19){
						return new AstarCell(9,9);
					} else if(n>16){
						return new AstarCell(8,8);
					} else if(n>13){
						return new AstarCell(7,7);
					} else if(n>10){
						return new AstarCell(6,6);
					} else {
						return new AstarCell(5,5);
					}
				}
			}
		} 

		n = 0;
		return null;
	}

}

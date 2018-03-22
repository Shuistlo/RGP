//import java.awt.Point;
//import java.util.Stack;

//import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
//import lejos.hardware.sensor.EV3GyroSensor;
//import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class Main {
	
	
	public static void main(String[] args){
	//setting up sensors
	EV3ColorSensor colourSensor = new EV3ColorSensor(SensorPort.S1);
	//EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(SensorPort.S3);
	//EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S4);

	//getting data from sensors
	//SampleProvider gyroMode = gyroSensor.getAngleMode();
	//float[] gyroSample = new float[gyroMode.sampleSize()];

	//SampleProvider sonicMode = ultraSensor.getDistanceMode();
	//float[] sonicSample = new float[sonicMode.sampleSize()];

	SampleProvider RGBMode = colourSensor.getRGBMode();
	float[] colourSample = new float[RGBMode.sampleSize()];
	colourSensor.setCurrentMode(2);
	
	//setting up motors
	RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	//localisation 
    	Localisation local = new Localisation();
    	leftMotor.resetTachoCount();
    
    	leftMotor.setSpeed(50);
    	rightMotor.setSpeed(50);
    
    	double rotation = leftMotor.getTachoCount()%53;

    	while(local.mapLocation() == null){  
    		leftMotor.forward();
		rightMotor.forward();
		colourSensor.fetchSample(colourSample, 0);
		
	    	if(rotation == 0){
        		local.filter(colourSample[2]);
	    	}
    	}
    
	rightMotor.stop(true);
	leftMotor.stop(true);

	//BoxMap map = new BoxMap();  


	//first A star
	/*
	AstarCell start = local.mapLocation();
	AstarCell end = new AstarCell(x, y);
	Astar astar = new Astar();
	Stack<Point> path = astar.planning(map,start, end);

	//movement through path
    	MotionModel motion = new MotionModel(rightMotor,leftMotor,gyroSensor,gyroSample);
	motion.traversePath(path.pop(),path);

	//moves forward while not far enough inside the box
	ultraSensor.fetchSample(sonicSample,0);
	float distance = sonicSample[0];

	while(sonicSample[0] > 0.036f){
		rightMotor.forward();
		leftMotor.forward();
		ultraSensor.fetchSample(sonicSample,0);
	}

	//sense colour of paper inside box
	colourSensor.fetchSample(colourSample,0);

	/*
	*	set location of obstacle in second set of variable obstacles to
	*	appropriate position depending on colour sensed
	*/

	/*if(colourSample[1] > 0.1f){

	} else {

	}
	Sound.twoBeeps();

	//moving out of box by sensing how far from the inside wall robot is
	ultraSensor.fetchSample(sonicSample,0);
	while(sonicSample[0] < distance){
		rightMotor.backward();
		leftMotor.backward();
		ultraSensor.fetchSample(sonicSample,0);
	}

	//second A star
	start = end;
	end = new Point(x, y);
	path = astar.search(start, end);

	//second movement
	motion.traversePath(path.pop(), path);
	*/
	leftMotor.endSynchronization();
	rightMotor.close();
	leftMotor.close();
	colourSensor.close();
	//gyroSensor.close();
	//ultraSensor.close();

	}

}

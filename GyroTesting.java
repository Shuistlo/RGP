package project;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class GyroTesting {
	
	//robot radius
	private final double R = 45;
	//wheel radius
	private final double r = 27.5;
	//Adjust as necessary
	RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D); //PORT C IS BROKE AS FUCK
	
	private EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(SensorPort.S1);
	private EV3ColorSensor colorsensor = new EV3ColorSensor(SensorPort.S2);
	private EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S4); 
	private SampleProvider gyroMode = gyroSensor.getAngleMode();
	private float[] gyroSample = new float[gyroMode.sampleSize()];

	public static void main(String[] args) {

		GyroTesting test = new GyroTesting();
		//106 as parameter gives roughly 90 degrees (actually gives 91 degrees :'( )
		
		test.setMotorSpeed(100);			
		test.move(80);
		test.turn(90);
		test.move(50);
		test.turn(-135);
		test.move(50);
		//for(int i = 0;i < 4;i++) {
			
			//test.testGyro();
			//Delay.msDelay(1000);
			//test.setMotorSpeed(100);			
			//test.move(500);
			//test.turn(90                                                                                                                                                                                                                                                                                                                                                                                                        );
			//test.turn(106);
			//test.move(1000);
			//test.turn(106);
			//test.turn(106);
			//Delay.msDelay(1000);
		
		//}
		
		/*
		test.setMotorSpeed(50);
		for(int i = 0;i < 10;i++) {
			test.move(-20);
			Delay.msDelay(5000);
		}*/
			
	}

	public void move(int distance) {
		//1. move foraward 20 mm
		//2. measure error
		//3. repeat
		int rotation = (int) (distance/(55*Math.PI)*360);

		leftMotor.startSynchronization();
		leftMotor.rotate(rotation, true);
		rightMotor.rotate(rotation, true);
		leftMotor.endSynchronization();
		leftMotor.waitComplete();
		rightMotor.waitComplete();
		
		
		//COMMENTED OUT, REVIEW RELEVANCE LATER
		
		/*System.out.println(gyrosensor.getAngleAndRateMode());
		//get the actual distance we moved from the gyro
		//display the error
		gyrosensor.reset();*/

	}

	
	public void turn(int deg) {
		
		int turnVal = (int)( (deg) * (R/r));
		gyroMode.fetchSample(gyroSample, 0);
		//leftMotor.rotate(-1000);
		//rightMotor.rotate(1000);
		gyroMode.fetchSample(gyroSample, 0);
		leftMotor.startSynchronization();
		leftMotor.rotate(-turnVal, true);
		rightMotor.rotate(turnVal, true);
		leftMotor.endSynchronization();
		leftMotor.waitComplete();
		rightMotor.waitComplete();
		gyroMode.fetchSample(gyroSample, 0);
		System.out.println(gyroSample[0]);
		
		/*while(gyroSample[0] < deg) {
			
			leftMotor.rotate(-2);
			rightMotor.rotate(2);
			gyroMode.fetchSample(gyroSample, 0);
			
		}*/
		
		//leftMotor.stop();
		//rightMotor.stop();
		//gyroMode.fetchSample(gyroSample, 0);
		//System.out.println(gyroSample[0]);
	}
	
	private void testGyro() {
		
		gyroMode.fetchSample(gyroSample, 0);
		System.out.println(gyroSample[0]);
		Delay.msDelay(3000);
		gyroMode.fetchSample(gyroSample, 0);
		System.out.println(gyroSample[0]);
	}

	private void setMotorSpeed(int speed) {
	
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	
	}

}

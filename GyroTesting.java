　
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class GyroTesting {
	
	//robot radius
	private final double R = 45;
	//wheel radius
	private final double r = 27.5;
	//Adjust as necessary
	RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D); //PORT C IS BROKE AS FUCK
	
	//private EV3GyroSensor gyrosensor = new EV3GyroSensor(SensorPort.S3); //still need to add the port and sensor

	public static void main(String[] args) {

		GyroTesting test = new GyroTesting();
		
		for(int i = 0;i < 4;i++) {
		
			test.setMotorSpeed(50);
			test.move(500);
			test.turn(90);
			test.turn(90);
			test.move(500);
			test.turn(90);
			test.turn(90);
			test.move(500);
			test.turn(90);
			test.turn(90);
			test.move(500);
			test.turn(90);
			test.turn(90);
			Delay.msDelay(5000);
		
		}/*
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
		
		int turnVal =(int)( (deg/2) * (R/r));
		
		leftMotor.startSynchronization();
		leftMotor.rotate(-turnVal, true);
		rightMotor.rotate(turnVal, true);
		leftMotor.endSynchronization();
		leftMotor.waitComplete();
		rightMotor.waitComplete();
		
	}

	private void setMotorSpeed(int speed) {
	
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	
	}

}

package project;

import java.awt.Point;
import java.util.Stack;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;

public class MotionModel {
	private RegulatedMotor rightMotor, leftMotor;
	private float[] gyroSample;
	private EV3GyroSensor gyroSensor;
	
	/**
	*	Moves a robot with a right motor, left motor and a gyro sensor, and moves
	*	it through a path of cells
	*
	*	@param	rightMotor	A RegulatedMotor.
	*	@param	leftMotor	A RegulatedMotor.
	*	@param	gyroSensor	An EV3GyroSensor.
	*	@param	gyroSample	An array of floats that hold values from the GyroSensor.
	*/
	public MotionModel(RegulatedMotor rightMotor, RegulatedMotor leftMotor, EV3GyroSensor gyroSenser, float[] gyroSample){
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
		this.gyroSensor = gyroSensor;
		this.gyroSample = gyroSample;
	}
	
	/**
	*	Moves the robot one grid forward.
	*/
	private void moveStraight(){
		int rotMod = rightMotor.getTachoCount()%159;
		while(rotMod != 0){
			rightMotor.forward();
			leftMotor.forward();
			
			rotMod = rightMotor.getTachoCount()%159;
		}
	}
	
	private void moveDiagonal(){
		int rotMod = rightMotor.getTachoCount()%225;
		while(rotMod != 0){
			rightMotor.forward();
			leftMotor.forward();
			
			rotMod = rightMotor.getTachoCount()%225;
		}
	}
	
	/**
	*	Turns the robot to face the desired angle. Relative to
	*	the robots first orientation.
	*
	*	@param	desired	the angle you wish to orient the robot to.
	*	@see	desired
	*/
	private void turn(int desired){
		gyroSensor.fetchSample(gyroSample, 0);
		float currAngle = gyroSample[0];
		
		double diff = currAngle - desired;
		while(diff > 1 || diff < -1){
			if(diff > 0){
				leftMotor.forward();
				rightMotor.backward();
			} else {
				leftMotor.backward();
				rightMotor.forward();
			}
		}
		
		rightMotor.stop();
		leftMotor.stop();
	}
	
	/**
	*	Takes the current position of a robot on path and uses
	*	turn() and moveStraight() to move through the path one
	*	point at a time.
	*
	*	@param	currentPosition	AstarCell that represents the 
	*			current position of the robot.
	*
	*/
	public void traversePath(Point currentPosition, Stack<Point> path){		
		Point currentPos = currentPosition;
		Point t;
		
		while(!path.isEmpty()){
			t = path.pop();
			if(t.x == currentPos.x + 1){
				if(t.y == currentPos.y + 1){
					turn(0);
					moveDiagonal();
				} else if (t.y == currentPos.y - 1) {
					turn(-90);
					moveDiagonal();
				} else {
					turn(-45);
					moveStraight();
				}
				
			} else if (t.x == currentPos.x) {
				if(t.y == currentPos.y + 1){
					turn(45);
					moveStraight();
				} else {
					turn(-135);
					moveStraight();
				}
				
			} else if(t.x == currentPos.x - 1){
				if(t.y == currentPos.y - 1){
					turn(90);
					moveDiagonal();
				} else if (t.y == currentPos.y - 1) {
					turn(135);
					moveStraight();
				}
			currentPos = t;
			}
		}
	}

}

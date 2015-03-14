package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.PID;
import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.RobotMap;
import org.usfirst.frc100.SlideWinder.commands.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drivetrain of the robot.
 */
public class Drivetrain extends Subsystem {

	SpeedController leftMotor = RobotMap.drivetrainLeftMotor;
	SpeedController rightMotor = RobotMap.drivetrainRightMotor;
	RobotDrive robotDrive = RobotMap.drivetrainRobotDrive;
	SpeedController slideMotor = RobotMap.drivetrainSlideMotor;
	DoubleSolenoid shifter = RobotMap.drivetrainShifter;
	Encoder leftEncoder = RobotMap.drivetrainLeftEncoder;
	Encoder rightEncoder = RobotMap.drivetrainRightEncoder;
	Encoder slideEncoder = RobotMap.drivetrainSlideEncoder;
	Gyro gyro = RobotMap.drivetrainGyro;
	AnalogInput leftLineReader = RobotMap.drivetrainLeftLineReader;
	AnalogInput rightLineReader = RobotMap.drivetrainRightLineReader;

	AnalogTrigger leftLineReadTrigger = RobotMap.drivetrainLeftLineReadTrigger;
	AnalogTrigger rightLineReadTrigger = RobotMap.drivetrainRightLineReadTrigger;
	private double velocity = 0;
	private double driveLimit = 0;
	private double accelerationLoopInterval = 20;
	private double turnLimit = 0;
	private double turnVelocity = 0;
	private double turnAcceleration = 0;
	private double previousTurnVelocity = 0;
	private double slideLimit = 0;
	private double slideVelocity = 0;
	private double trueVelocity = 0;
	private double previousTrueVelocity = 0;
	private double trueAcceleration = 0;
	private Timer timer = new Timer();
	private PID distancePID = new PID("DriveDistance");
	private PID anglePID = new PID("DriveAngle");
	private PID slidePID = new PID("DriveSlide");

	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	/**
	 * Drives the robot
	 * 
	 * @param speed - Forward (+)/back (-)
	 * @param slide - Right (+)/left (-)
	 * @param turn - Clockwise (+)/counterclockwise (-)
	 */
	public void drive(double speed, double slide, double turn) {
		robotDrive.arcadeDrive(-speed, turn);
		slideMotor.set(slide);
	}

	/**
	 * Shifts to high or low gear
	 * 
	 * @param highgear - High or low gear
	 */
	public void shift(boolean highgear) {
		if (highgear) {
			shifter.set(DoubleSolenoid.Value.kForward);
		} else {
			shifter.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * Limits the acceleration
	 * 
	 * @param speed - Forward/back
	 * @param slide - Left/right
	 * @param turn - Clockwise/counterclockwise
	 */
	public void gradualDrive(double speed, double slide, double turn) {
		timer.stop();
		accelerationLoopInterval = timer.get();
		velocity = leftEncoder.getRate();
		slideVelocity = slideEncoder.getRate();
		trueVelocity = Math.hypot(velocity, slideVelocity);
		trueAcceleration = (trueVelocity - previousTrueVelocity)
				/ accelerationLoopInterval;
		turnVelocity = gyro.getRate();
		turnAcceleration = (turnVelocity - previousTurnVelocity)
				/ accelerationLoopInterval;
		// see if the velocity limits work
		if (trueAcceleration > Preferences.getDouble("UpperAccelerationLimit")
				|| turnAcceleration > Preferences
						.getDouble("UpperTurnAccelerationLimit")) {
			drive(driveLimit, slideLimit, turnLimit);

		} else if (trueAcceleration < Preferences.getDouble("LowerAccelerationLimit")
				|| turnAcceleration < Preferences.getDouble("LowerTurnAccelerationLimit")) {
			
			drive(driveLimit, slideLimit, turnLimit);
		} else {
			if (speed > driveLimit) {
				driveLimit += Preferences.getDouble("LimitStep");
			} else if (speed < driveLimit) {
				driveLimit -= Preferences.getDouble("LimitStep");
			}
			if (slide > slideLimit) {
				slideLimit += Preferences.getDouble("LimitStep");
			} else if (slide < slideLimit) {
				slideLimit -= Preferences.getDouble("LimitStep");
			}
			if (turn > turnLimit) {
				turnLimit += Preferences.getDouble("LimitStep");
			} else if (turn < turnLimit) {
				turnLimit -= Preferences.getDouble("LimitStep");
			}

			drive(driveLimit, slideLimit, turnLimit);
		}

		previousTrueVelocity = trueVelocity;
		previousTurnVelocity = turnVelocity;
		timer.start();
	}

	/**
	 * Sets the target for the auto PID
	 * 
	 * @param targetDistance - Forward/back
	 * @param targetSlide - Left/right
	 * @param targetAngle
	 *            - Clockwise/counterclockwise
	 */
	public void setAutoTarget(double targetDistance, double targetSlide, double targetAngle) {
		distancePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		slidePID.update(slideEncoder.getDistance());
		anglePID.update(gyro.getAngle());
		distancePID.setRelativeLocation(0);
		slidePID.setRelativeLocation(0);
		anglePID.setRelativeLocation(0);
		distancePID.setTarget(targetDistance);
		slidePID.setTarget(targetSlide);
		anglePID.setTarget(targetAngle);
	}

	/**
	 * Updates the drive PID
	 * 
	 * @param gradualDrive - whether the acceleration should be limited
	 */
	public void updateAuto(boolean gradualDrive) {
		distancePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		slidePID.update(slideEncoder.getDistance());
		anglePID.update(gyro.getAngle());
		if (gradualDrive) {
			gradualDrive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
		} else {
			drive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
		}
	}

	/**
	 * Updates the drive PID with specific input values
	 * 
	 * @param newDistance - the new distance input in inches
	 * @param newSlide - the new slide input in inches
	 * @param newAngle - the new angle input in degrees
	 * @param gradualDrive - whether the acceleration should be limited
	 */
	public void updateAuto(double newDistance, double newSlide, double newAngle, boolean gradualDrive) {
		distancePID.update(newDistance);
		slidePID.update(newSlide);
		anglePID.update(newAngle);
		if (gradualDrive) {
			gradualDrive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
		} else {
			drive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
		}
	}

	/**
	 * Determines if robot has reached the target position
	 * 
	 * @return Whether all 3 PID loops have reached their target
	 */
	public boolean autoReachedTarget() {
		return distancePID.reachedTarget() && anglePID.reachedTarget()
				&& slidePID.reachedTarget();
	}

	/**
	 * Updates the SmartDashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putBoolean("Drivetrain/High Gear",
				shifter.get() == DoubleSolenoid.Value.kForward);
		SmartDashboard.putNumber("Drivetrain/LeftEncoder Raw",
				leftEncoder.getRaw());
		SmartDashboard.putNumber("Drivetrain/RightEncoder Raw",
				rightEncoder.getRaw());
		SmartDashboard.putNumber("Drivetrain/LeftEncoder",
				leftEncoder.getDistance());
		SmartDashboard.putNumber("Drivetrain/RightEncoder",
				rightEncoder.getDistance());
		SmartDashboard.putNumber("Drivetrain/Gyro", gyro.getAngle());
		SmartDashboard.putNumber("Drivetrain/Left LineReader Value",
				leftLineReader.getValue());
		SmartDashboard.putNumber("Drivetrain/Right LineReader Value",
				rightLineReader.getValue());
		SmartDashboard.putBoolean("Drivetrain/Left LineReader OnWhite",
				!leftLineReadTrigger.getTriggerState());
		SmartDashboard.putBoolean("Drivetrain/Right LineReader OnWhite",
				!rightLineReadTrigger.getTriggerState());
		SmartDashboard.putNumber("Drivetrain/LineTrackerLimit",
				Preferences.getDouble("LineTrackerLimit"));

		// Acceleration code
		SmartDashboard.putNumber("DriveTrain/Acceleration Limit", driveLimit);
		SmartDashboard.putNumber("DriveTrain/Interval",
				accelerationLoopInterval);
		// only applies to non-slide
		SmartDashboard.putNumber("DriveTrain/Velocity", velocity); 

		SmartDashboard.putNumber("DriveTrain/Acceleration", trueAcceleration);
	}

	/**
	 * Sets the angle PID target
	 * 
	 * @param targetAngle - The target angle in degrees
	 */
	public void setAngleTarget(double targetAngle) {
		anglePID.update(gyro.getAngle());
		anglePID.setRelativeLocation(0);
		anglePID.setTarget(targetAngle);
	}

	/**
	 * Updates only the angle PID
	 * 
	 * @return The angle PID output
	 */
	public double updateAngle() {
		anglePID.update(gyro.getAngle());
		return anglePID.getOutput();
	}

	/**
	 * Determines if the angle PID has reached the target
	 * 
	 * @return Whether angle target has been reached
	 */
	public boolean reachedAngle() {
		return anglePID.reachedTarget();
	}

	/**
	 * Sets the distance PID target
	 * 
	 * @param targetDistance - The target distance in inches
	 */
	public void setDistanceTarget(double targetDistance) {
		distancePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		distancePID.setRelativeLocation(0);
		distancePID.setTarget(targetDistance);
	}

	/**
	 * Updates only the distance PID
	 * 
	 * @return The distance PID output
	 */
	public double updateDistance() {
		distancePID.update((leftEncoder.getDistance() + rightEncoder
				.getDistance()) / 2);
		return distancePID.getOutput();
	}

	/**
	 * Determines if the distance PID has reached the target
	 * 
	 * @return Whether distance target has been reached
	 */
	public boolean reachedDistance() {
		return distancePID.reachedTarget();
	}
	
	/**
	 * Sets the slide PID target
	 * 
	 * @param targetSlideDistance - The target slide distance in inches
	 */
	public void setSlideTarget(double targetSlideDistance) {
		slidePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		slidePID.setRelativeLocation(0);
		slidePID.setTarget(targetSlideDistance);
	}

	/**
	 * Updates only the distance PID
	 * 
	 * @return The distance PID output
	 */
	public double updateSlide() {
		slidePID.update(slideEncoder.getDistance());
		return distancePID.getOutput();
	}

	/**
	 * Determines if the distance PID has reached the target
	 * 
	 * @return Whether distance target has been reached
	 */
	public boolean reachedSlideDistance() {
		return slidePID.reachedTarget();
	}

	/**
	 * Calculates angle output based on current PID output
	 * 
	 * @return The calculated angle output
	 */
	private double calculateLineTrackTurn() {
		double distOutput = distancePID.getOutput();
		if (distOutput > 1) {
			distOutput = 1;
		}
		if (distOutput == 0){
			return 0;
		} else{
			return (-.2*distOutput + .4);
		}
	}

	/**
	 * Follows the line of tape
	 * 
	 * @return The specific angle output for each case
	 */
	public double followLine() {
		double turnTrack = 0;
		double rawTurnValue = calculateLineTrackTurn();
		if (!rightLineReadTrigger.getTriggerState() && leftLineReadTrigger.getTriggerState()) {
			turnTrack = 0;
		} else if (!rightLineReadTrigger.getTriggerState() && !leftLineReadTrigger.getTriggerState()) {
			turnTrack = -rawTurnValue;
		} else if (rightLineReadTrigger.getTriggerState() && leftLineReadTrigger.getTriggerState()) {
			turnTrack = rawTurnValue;
		} else if (rightLineReadTrigger.getTriggerState() && !leftLineReadTrigger.getTriggerState()) {
			turnTrack = -rawTurnValue;
		}
		SmartDashboard.putNumber("Drivetrain/TurnTrack", turnTrack);
		return turnTrack;
	}

	/**
	 * Determines and sets the optimal limit for the line trackers
	 */
	public void setLineTrackLimits() {
		int limit;
		int diff = Math.abs(rightLineReader.getValue() - leftLineReader.getValue());
		if (diff > 80 ) {
			limit = (leftLineReader.getValue() + rightLineReader.getValue()) / 2;
			Preferences.set("LineTrackerLimit", limit);
		} else {
			limit = (int) Preferences.getDouble("LineTrackerLimit");
		}
		leftLineReadTrigger.setLimitsRaw(limit, limit);
		rightLineReadTrigger.setLimitsRaw(limit, limit);
	}

	/**
	 * Slides until the line is detected
	 */
	public boolean onLine() {
		return !(rightLineReadTrigger.getTriggerState() || leftLineReadTrigger
				.getTriggerState());
	}

	/**
	 * Follows the line using the camera
	 * 
	 * @param lineError - The distance from the line
	 */
	public void visionFollowLine(double lineError){
		double ratio = SmartDashboard.getNumber("CameraVision/PID_Ratio");
		distancePID.update((-leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		anglePID.update(gyro.getAngle());
		slidePID.update(ratio*lineError);
		drive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
	}
}

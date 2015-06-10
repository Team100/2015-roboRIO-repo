package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.PID;
import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.RobotMap;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The elevator that raises and lowers game pieces. Uses a brake to hold at
 * constant position.
 */
public class Elevator extends Subsystem {
	SpeedController motor1 = RobotMap.elevatorMotor1;
//	SpeedController motor2 = RobotMap.elevatorMotor2;
	Encoder encoder = RobotMap.elevatorEncoder;
	DigitalInput upperLimit = RobotMap.elevatorUpperLimit;
	DigitalInput lowerLimit = RobotMap.elevatorLowerLimit;

	private PID elevatorPID = new PID("Elevator"); // the elevator PID loop
	private final double TOTE_HEIGHT = 11.75; // inches, compensated for 0.35 difference between lip and actual
	private final double SCORING_PLATFORM_HEIGHT = 2.0; // inches
	private final double STEP_HEIGHT = 6.0; // inches, compensated for the standard +2 inches for scoring
	private boolean topTriggered = false;
	private boolean override = false;
	
	private final double MOTOR_1_INVERSION = 1.0;
//	private final double MOTOR_2_INVERSION = -1.0;

	public void initDefaultCommand() {
	}

	/**
	 * Activates the brake and stops the motor
	 */
	public void activateBrake() {
		motor1.set(0);
//		motor2.set(0);
	}

	/**
	 * Deactivates the brake
	 */
	public void releaseBrake() {
	}

	/**
	 * Getter method for the lower limit
	 *
	 * @return Whether the elevator has reached the lower limit
	 */
	public boolean getLowerLimit() {
		return !lowerLimit.get();
	}

	/**
	 * Zeroes the PID loop
	 */
	public void zeroPID() {
		elevatorPID.update(-encoder.getDistance());
		elevatorPID.setRelativeLocation(0);
	}

	/**
	 * Updates the PID loop
	 */
	public void updatePID() {
		elevatorPID.update(-encoder.getDistance());
		manualControl(elevatorPID.getOutput());
	}

	/**
	 * Moves the elevator to a preset position
	 *
	 * @param position - 1 is the lowest tote, 2 is the second tote on the stack, 3
	 *     is the 3rd tote on the stack, etc.
	 */
	public void setPosition(int position) {
		SmartDashboard.putNumber("Elevator/Position", position);
		double height = Preferences.getDouble("ElevatorPositionOne");
		height += (position - 1) * TOTE_HEIGHT;
		if (SlideWinder.oi.scoringButton.get()) {
			height += SCORING_PLATFORM_HEIGHT;
		}
		if (SlideWinder.oi.coopertitionButton.get()) {
			height += STEP_HEIGHT;
		}
		elevatorPID.setTarget(height);
	}

	/**
	 * Determines if PID has reached the target
	 *
	 * @return Whether target has been reached
	 */
	public boolean isInPosition() {
		return elevatorPID.reachedTarget();
	}

	public boolean isGoingUp() {
		return MOTOR_1_INVERSION*motor1.get() > 0.0;
	}

	/**
	 * Sets the PID target
	 *
	 * @param target - Height in inches
	 */
	public void setAutoTarget(double target) {
		elevatorPID.setTarget(target);
	}

	/**
	 * Sets the raw motor output unless limits are triggered
	 *
	 * @param speed - The motor output
	 */
	public void manualControl(double speed) {
		if (speed<0) {
			topTriggered = false;
		}
		if (!upperLimit.get()) {
			topTriggered = true;
		}
		if(override){
			motor1.set(MOTOR_1_INVERSION*speed);
//			motor2.set(MOTOR_2_INVERSION*speed);
			return;
		}
		if (!topTriggered && !getLowerLimit()) {
			releaseBrake();
			motor1.set(MOTOR_1_INVERSION*speed);
//			motor2.set(MOTOR_2_INVERSION*speed);
		} else if (topTriggered) {
			if (speed < 0) {
				releaseBrake();
				motor1.set(MOTOR_1_INVERSION*speed);
//				motor2.set(MOTOR_2_INVERSION*speed);
			} else {
				activateBrake();
			}
		} else if (getLowerLimit()) {
			if (speed > 0) {
				releaseBrake();
				motor1.set(MOTOR_1_INVERSION*speed);
//				motor2.set(MOTOR_2_INVERSION*speed);
			} else {
				activateBrake();
			}
			elevatorPID.update(-encoder.getDistance());
			elevatorPID.setRelativeLocation(0);
		}
	}

	/**
	 * Updates the SmartDashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putBoolean("Elevator/Upper Limit", upperLimit.get());
		SmartDashboard.putBoolean("Elevator/Lower Limit", lowerLimit.get());
		SmartDashboard.putBoolean("Elevator/Top Triggered", topTriggered);
		SmartDashboard.putBoolean("Elevator/Override", override);
		SmartDashboard.putNumber("Elevator/Encoder", -encoder.getDistance());
		elevatorPID.update(-encoder.getDistance());
		SmartDashboard.putNumber("Elevator Height", elevatorPID.getInputValue());
	}

	public boolean getUpperLimit() {
		return topTriggered;
	}
	
	public void setOverride(boolean override) {
		this.override = override;
	}
}
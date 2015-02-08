package org.usfirst.frc100.Robot2015.subsystems;

import org.usfirst.frc100.Robot2015.PID;
import org.usfirst.frc100.Robot2015.Robot;
import org.usfirst.frc100.Robot2015.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The elevator that raises and lowers game pieces. Uses a brake to hold at
 * constant position.
 */
public class Elevator extends Subsystem {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController motor = RobotMap.elevatorMotor;
    Encoder encoder = RobotMap.elevatorEncoder;
    DoubleSolenoid brake = RobotMap.elevatorBrake;
    DigitalInput upperLimit = RobotMap.elevatorUpperLimit;
    DigitalInput lowerLimit = RobotMap.elevatorLowerLimit;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private PID elevatorPID = new PID("Elevator"); // the elevator PID loop
	private final double positionOne = 6; // inches
	private final double toteHeight = 11.75; // inches, compensated for 0.35 difference between lip and actual
	private final double scoringPlatformHeight = 2.0; // inches
	private final double stepHeight = 4.0; // inches, compensated for the standard +2 in for scoring

	public void initDefaultCommand() {
	}

	/**
	 * Activates the brake and stops the motor
	 */
	public void activateBrake() {
		motor.set(0);
		brake.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Deactivates the brake
	 */
	public void releaseBrake() {
		brake.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Getter method for the lower limit
	 * @return Whether the elevator has reached the lower limit
	 */
	public boolean getLowerLimit() {
		return lowerLimit.get();
	}

	/**
	 * Zeroes the PID loop
	 */
	public void zeroPID() {
		elevatorPID.update(encoder.getDistance());
		elevatorPID.setRelativeLocation(0);
	}

	/**
	 * Updates the PID loop
	 */
	public void updatePID() {
		elevatorPID.update(encoder.getDistance());
		manualControl(elevatorPID.getOutput());
	}

	/**
	 * Moves the elevator to a preset position
	 * @param position - 1 is the lowest tote, 2 is the second tote on the stack,
	 * 3 is the 3rd tote on the stack, etc.
	 */
	public void setPosition(int position) {
		SmartDashboard.putNumber("Elevator Position", position);
		double height = positionOne;
		height += (position - 1) * toteHeight;
		if (!Robot.oi.nonScoringButton.get()) {
			height += scoringPlatformHeight;
		}
		if (Robot.oi.coopertitionButton.get()) {
			height += stepHeight;
		}
		elevatorPID.setTarget(height);
	}

	/**
     * Determines if PID has reached the target
     * @return Whether target has been reached
     */
	public boolean isInPosition() {
		return elevatorPID.reachedTarget();
	}

	/**
	 * Sets the PID target
	 * @param target - Height in inches
	 */
	public void setAutoTarget(double target) {
		elevatorPID.setTarget(target);
	}
	
	/**
	 * Sets the raw motor output unless limits are triggered
	 * @param speed - The motor output
	 */
	public void manualControl(double speed) {
		if (!upperLimit.get() && !lowerLimit.get()) {
			releaseBrake();
			motor.set(speed);
		} else if (upperLimit.get()) {
			if (speed < 0) {
				releaseBrake();
				motor.set(speed);
			} else {
				activateBrake();
			}
		} else if (lowerLimit.get()) {
			elevatorPID.setRelativeLocation(0);
			if (speed > 0) {
				releaseBrake();
				motor.set(speed);
			} else {
				activateBrake();
			}
		}
	}
	
	/**
	 * Updates the SmartDashboard
	 */
	public void updateDashboard () {
		SmartDashboard.putBoolean("Elevator Upper Limit", upperLimit.get());
		SmartDashboard.putBoolean("Elevator Lower Limit", lowerLimit.get());
		SmartDashboard.putBoolean("ELevator Brake",brake.get() == DoubleSolenoid.Value.kReverse);
		SmartDashboard.putNumber("Elevator Encoder", encoder.getDistance());
	}
}
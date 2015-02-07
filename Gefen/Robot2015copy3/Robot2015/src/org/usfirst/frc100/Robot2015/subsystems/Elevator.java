package org.usfirst.frc100.Robot2015.subsystems;

import org.usfirst.frc100.Robot2015.*;
import org.usfirst.frc100.Robot2015.commands.*;

import edu.wpi.first.wpilibj.*;
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
	private final double driveDiffHeight = 1.0; // inches
	private final double scoringPlatformHeight = 2.0; // inches
	private final double stepHeight = 4.0; // inches, compensated for the standard +2 in for scoring

	// No default command
	public void initDefaultCommand() {
	}

	// Activates the brake
	public void activateBrake() {
		motor.set(0);
		brake.set(DoubleSolenoid.Value.kReverse);
	}

	// Deactivates the brake
	public void releaseBrake() {
		brake.set(DoubleSolenoid.Value.kForward);
	}

	// Returns whether the elevator has reached the lower limit
	public boolean getLowerLimit() {
		return lowerLimit.get();
	}

	// Zeroes the PID loop
	public void zeroPID() {
		elevatorPID.setRelativeLocation(0);
	}

	// Updates the PID loop
	public void updatePID() {
		elevatorPID.update(encoder.getDistance());
		manualControl(elevatorPID.getOutput());
	}

	// Moves the elevator to a given position (see LiftToteToHeight command)
	public void setPosition(int position) {
		SmartDashboard.putNumber("Elevator Position", position);
		double height = positionOne;
		if (Robot.drivetrain.isSlide()) {
			height += driveDiffHeight;
		}
		height += (position - 1) * toteHeight;
		if (!Robot.oi.nonScoringButton.get()) {
			height += scoringPlatformHeight;
		}
		if (Robot.oi.coopertitionButton.get()) {
			height += stepHeight;
		}
		elevatorPID.setTarget(height);
	}

	// Returns whether PID target has been reached
	public boolean isInPosition() {
		return elevatorPID.reachedTarget();
	}

	// Sets the motor output to the given value
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
	
	// Updates the SmartDashboard
	public void updateDashboard () {
		SmartDashboard.putBoolean("Elevator Upper Limit", upperLimit.get());
		SmartDashboard.putBoolean("Elevator Lower Limit", lowerLimit.get());
		SmartDashboard.putBoolean("ELevator Brake",brake.get() == DoubleSolenoid.Value.kReverse);
		SmartDashboard.putNumber("Elevator Encoder", encoder.getDistance());
	}
}

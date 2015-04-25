package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Moves elevator down to lower limit switch and sets that position to be the
 * zero value.
 */
public class AutoCalibrateElevator extends Command {

	boolean limitTrigger = false;

	public AutoCalibrateElevator() {
        requires(SlideWinder.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		limitTrigger = false;
		SlideWinder.elevator.setOverride(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.elevator.manualControl(-0.6);
		if (SlideWinder.elevator.getLowerLimit()) {
			SlideWinder.elevator.manualControl(0.2);
			limitTrigger = true;
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !SlideWinder.elevator.getLowerLimit() && limitTrigger;

	}

	// Called once after isFinished returns true
	protected void end() {
		SlideWinder.elevator.activateBrake();
		SlideWinder.elevator.zeroPID();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

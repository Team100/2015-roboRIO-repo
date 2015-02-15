package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Uses PID to actively resist changes in position.
 */
public class Immobilize extends Command {

	public Immobilize() {
        requires(SlideWinder.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SlideWinder.drivetrain.setAutoTarget(0, 0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.drivetrain.updateAuto(false);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		SlideWinder.drivetrain.drive(0, 0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

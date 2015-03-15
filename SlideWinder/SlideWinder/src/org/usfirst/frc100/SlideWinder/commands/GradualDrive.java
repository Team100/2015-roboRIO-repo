package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Limits the acceleration of the drivetrain to prevent totes from falling.
 */
public class GradualDrive extends Command {

	public GradualDrive() {
        requires(SlideWinder.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.drivetrain.gradualDrive(SlideWinder.oi.getLeftJoystick().getY(),
			SlideWinder.oi.getLeftJoystick().getX(), SlideWinder.oi.getRightJoystick().getX());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

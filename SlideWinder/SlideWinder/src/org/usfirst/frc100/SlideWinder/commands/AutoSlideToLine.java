package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Slides sideways until the line is detected.
 */
public class AutoSlideToLine extends Command {

	public AutoSlideToLine() {
        requires(SlideWinder.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SlideWinder.drivetrain.setSlideTarget(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(SlideWinder.drivetrain.onLine()){
			SlideWinder.drivetrain.drive(0, SlideWinder.drivetrain.updateSlide(), 0);
		} else {
			SlideWinder.drivetrain.drive(0, -1.0, 0);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return SlideWinder.drivetrain.reachedSlideDistance();
	}

	// Called once after isFinished returns true
	protected void end() {
		SlideWinder.drivetrain.setLineTrackLimits();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

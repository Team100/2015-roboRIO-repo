package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Terminates after an amount of time in seconds specified in the constructor.
 * This class is used by the autonomous command groups to delay the execution of
 * commands.
 */
public class AutoDelay extends Command {

	private final double t;
	private Timer timer = new Timer();

	/**
	 * @param time - The delay in seconds before the command terminates
	 */
	public AutoDelay(double time) {
		t = time;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() > t;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

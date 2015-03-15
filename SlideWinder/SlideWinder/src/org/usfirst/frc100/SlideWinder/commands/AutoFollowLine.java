package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses line readers to follow the line autonomously.
 */
public class AutoFollowLine extends Command {
	
	private final double distance;
	
	/**
	 * @param distance - The distance to travel in inches
	 */
    public AutoFollowLine(double distance) {
    	this.distance = distance;
        requires(SlideWinder.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SlideWinder.drivetrain.setDistanceTarget(distance);
    	SlideWinder.drivetrain.setAngleTarget(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SlideWinder.drivetrain.drive(SlideWinder.drivetrain.updateDistance(), SlideWinder.drivetrain.followLine(),
				SlideWinder.drivetrain.updateAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return SlideWinder.drivetrain.reachedDistance();
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

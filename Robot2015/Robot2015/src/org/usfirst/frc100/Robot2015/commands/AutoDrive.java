package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2015.Robot;

/**
 * Autonomously drives a distance in inches specified in the constructor. Uses
 * PID.
 */
public class  AutoDrive extends Command {
	
	private final double distance;
	private final double slide;

	// Target distance in inches, requires Drivetrain
    public AutoDrive(double distance) {
        this.distance = distance;
    	this.slide = 0;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    
    // Target distance and slide in inches, requires Drivetrain
    public AutoDrive(double distance, double slide) {
    	this.distance = distance;
    	this.slide = slide;
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setAutoTarget(distance, slide, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.updateAuto();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.autoReachedTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.drive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	
    }
}

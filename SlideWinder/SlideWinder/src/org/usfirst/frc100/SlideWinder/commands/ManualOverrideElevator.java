package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualOverrideElevator extends Command {

    public ManualOverrideElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SlideWinder.elevator.setOverride(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SlideWinder.elevator.setOverride(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

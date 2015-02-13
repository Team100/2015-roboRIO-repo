package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.SlideWinder.Robot;

/**
 * Autonomously follow the line using vision.
 */
public class  AutoVisionFollowLine extends Command {

    public AutoVisionFollowLine() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

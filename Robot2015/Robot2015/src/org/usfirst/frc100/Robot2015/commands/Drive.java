package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2015.Robot;

/**
 * Operates the drivetrain during teleoperated period. Left Y is forward/back,
 * left X is strafe left/right, and right X is turn clockwise/counterclockwise.
 */
public class  Drive extends Command {

    public Drive() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive(Robot.oi.getLeftJoystick().getY(), Robot.oi.getLeftJoystick().getX(), Robot.oi.getRightJoystick().getX());
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

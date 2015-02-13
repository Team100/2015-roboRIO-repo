package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.Robot2015.Preferences;
import org.usfirst.frc100.Robot2015.SlideWinder;

/**
 * Operates the drivetrain during teleoperated period. Left Y is forward/back,
 * left X is strafe left/right, and right X is turn clockwise/counterclockwise.
 */
public class Drive extends Command {

    public Drive() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(SlideWinder.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftJoystickX = SlideWinder.oi.getLeftJoystick().getX();
    	if(Math.abs(leftJoystickX) < Preferences.getDouble("SlideJoystickDeadband")) {
    		leftJoystickX = 0;
    	}
   	  	// Joystick Y-axis values are flipped, so send a negative Y-value
    	SlideWinder.drivetrain.drive(-SlideWinder.oi.getLeftJoystick().getY(), leftJoystickX, SlideWinder.oi.getRightJoystick().getX());
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

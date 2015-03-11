package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses vision and PID to line up with a tote.
 */
public class AlignToTote extends Command {
	
    public AlignToTote() {
        requires(SlideWinder.drivetrain);
        requires(SlideWinder.cameraVision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SlideWinder.drivetrain.setAutoTarget(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angleError = SlideWinder.cameraVision.getToteAngleError();
    	double positionError = SlideWinder.cameraVision.getTotePositionError();
    	positionError *= Preferences.getDouble("CameraPID_Ratio");
    	SlideWinder.drivetrain.updateAuto(0.0, positionError, angleError, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return SlideWinder.drivetrain.autoReachedTarget();
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

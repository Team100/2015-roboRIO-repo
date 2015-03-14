package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Uses angle PID to slide in a straight line.
 */
public class SlideStraight extends Command {

	Joystick leftStick;
	Joystick rightStick;

	public SlideStraight() {
        requires(SlideWinder.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		leftStick = SlideWinder.oi.getLeftJoystick();
		rightStick = SlideWinder.oi.getRightJoystick();
		SlideWinder.drivetrain.setAngleTarget(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.drivetrain.drive(leftStick.getY(), leftStick.getX(),
				SlideWinder.drivetrain.updateAngle());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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

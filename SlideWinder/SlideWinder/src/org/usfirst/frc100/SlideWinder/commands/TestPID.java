package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Causes the PID code for a specific subsystem to attempt to reach a value
 * specified on the SmartDashboard.
 */
public class TestPID extends Command {

	private final System system;
	private double target = 0;
	private boolean armDone = false;

	// The various PID loops of the robot
	public enum System {
		DRIVEDISTANCE, DRIVEANGLE, DRIVESLIDE, ELEVATOR, ARM;
	}

	/**
	 * @param system - The PID system to be tested
	 */
	public TestPID(System system) {
		this.system = system;
		if (system == System.ELEVATOR) {
			requires(SlideWinder.elevator);
//		} else if (system == System.ARM) {
//			requires(SlideWinder.arm);
		} else {
			requires(SlideWinder.drivetrain);
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (system == System.DRIVEDISTANCE) {
			target = SmartDashboard.getNumber("PID/DriveDistance TestTarget");
			SlideWinder.drivetrain.setAutoTarget(target, 0, 0);
		} else if (system == System.DRIVESLIDE) {
			target = SmartDashboard.getNumber("PID/DriveSlide TestTarget");
			SlideWinder.drivetrain.setAutoTarget(0, target, 0);
		} else if (system == System.DRIVEANGLE) {
			target = SmartDashboard.getNumber("PID/DriveAngle TestTarget");
			SlideWinder.drivetrain.setAutoTarget(0, 0, target);
		} else if (system == System.ELEVATOR) {
			target = SmartDashboard.getNumber("PID/Elevator TestTarget");
			SlideWinder.elevator.setAutoTarget(target);
//		} else if (system == System.ARM) {
//			target = SmartDashboard.getNumber("PID/Arm TestTarget");
//			SlideWinder.arm.setArmHeight(target);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (system == System.ELEVATOR) {
			SlideWinder.elevator.updatePID();
//		} else if (system == System.ARM) {
//			armDone = SlideWinder.arm.updateArm();
		} else {
			SlideWinder.drivetrain.updateAuto(false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (system == System.ELEVATOR) {
			return SlideWinder.elevator.isInPosition();
		} else if (system == System.ARM) {
			return armDone;
		} else {
			return SlideWinder.drivetrain.autoReachedTarget();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		if (system == System.ELEVATOR) {
			SlideWinder.elevator.activateBrake();
//		} else if (system == System.ARM) {
//			SlideWinder.arm.manualControl(0.0);
		} else {
			SlideWinder.drivetrain.drive(0, 0, 0);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}

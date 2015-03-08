package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Manually controls the elevator and arm using the manipulator joystick.
 */
public class ManipulatorManualControl extends Command {

	boolean LTB1_prev = false;
	boolean LTB2_prev = false;

	public ManipulatorManualControl() {
        requires(SlideWinder.elevator);
//		requires(SlideWinder.arm);
		setInterruptible(false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.elevator.manualControl(-SlideWinder.oi.getManipulatorJoystick().getY());
//		SlideWinder.arm.manualControl(-SlideWinder.oi.getManipulatorJoystick().getThrottle());
//		if (SlideWinder.oi.liftToteButton1.get() ^ !LTB1_prev) { // this block only runs when the button value changes
//			SlideWinder.arm.toggleStab();
//			LTB1_prev = !LTB1_prev;
//		}
//		if (SlideWinder.oi.liftToteButton2.get() ^ !LTB2_prev) { // this block only runs when the button value changes
//			SlideWinder.arm.toggleDeploy();
//			LTB2_prev = !LTB2_prev;
//		}
		if(SlideWinder.oi.coopertitionButton.get() || SlideWinder.oi.scoringButton.get()) {
			this.cancel();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		SlideWinder.elevator.activateBrake();
//		SlideWinder.arm.manualControl(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
//		if(SlideWinder.oi.coopertitionButton.get())
//			new RaiseArm(true).start();
//		else if(SlideWinder.oi.scoringButton.get())
//			new RaiseArm(false).start();
	}
}

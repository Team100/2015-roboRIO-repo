package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2015.SlideWinder;

/**
 * Manually controls the elevator and arm using the manipulator joystick.
 */
public class ManipulatorManualControl extends Command {

    public ManipulatorManualControl() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(SlideWinder.elevator);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(SlideWinder.arm);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SlideWinder.elevator.manualControl(SlideWinder.oi.getManipulatorJoystick().getY());
		SlideWinder.arm.manualControl(SlideWinder.oi.getManipulatorJoystick().getThrottle());
		if (SlideWinder.oi.liftToteButton1.get()) SlideWinder.arm.toggleStab();
		if (SlideWinder.oi.liftToteButton2.get()) SlideWinder.arm.toggleDeploy();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SlideWinder.elevator.activateBrake();
    	SlideWinder.arm.manualControl(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

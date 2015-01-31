package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2015.Robot;

/**
 * Extends or retracts the autonomous arm, depending on the boolean value
 * specified in the constructor.
 */
public class  DeployArm extends Command {
	
	private boolean isFinished = false;
	private boolean extend;
    
    public DeployArm(boolean extend) {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.arm);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        this.extend = extend;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isFinished = Robot.arm.deployArm(extend);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.manualControl(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

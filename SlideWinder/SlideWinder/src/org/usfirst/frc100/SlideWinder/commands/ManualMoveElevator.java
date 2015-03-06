package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator without PID
 */
public class ManualMoveElevator extends Command {

	private final boolean raise;
	
	/**
	 * @param raise - raise or lower the elevator
	 */
    public ManualMoveElevator(boolean raise) {
        requires(SlideWinder.elevator);
        this.raise = raise;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(raise){
    		SlideWinder.elevator.manualControl(1.0);
    	} else {
    		SlideWinder.elevator.manualControl(-1.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SlideWinder.elevator.activateBrake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

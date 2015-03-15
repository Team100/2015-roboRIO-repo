package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.SlideWinder.SlideWinder;

/**
 * Moves the elevator to an integer height specified in the constructor. 1 is
 * the lowest tote, 2 is the second tote on the stack, 3 is the 3rd tote on the
 * stack, etc. Uses PID.
 */
public class SetElevatorPosition extends Command {

	private final double position;
	private final boolean presetPosition;

	/**
	 * @param position - 1 is the lowest tote, 2 is the second tote on the stack, 3
	 *     is the 3rd tote on the stack, etc.
	 */
	public SetElevatorPosition(int position) {
		this.position = position;
		this.presetPosition = true;
        requires(SlideWinder.elevator);
	}

	/**
	 * @param position - The height in inches to which the elevator should be moved, relative to the lower hall effect
	 * @param presetPosition - Whether the given position is one of the presets
	 */
	public SetElevatorPosition(double position, boolean presetPosition) {
		this.position = position;
		this.presetPosition = presetPosition;
        requires(SlideWinder.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SlideWinder.elevator.releaseBrake();
		if(presetPosition) {
			SlideWinder.elevator.setPosition((int)position);
		} else {
			SlideWinder.elevator.setAutoTarget(position);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SlideWinder.elevator.updatePID();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return SlideWinder.elevator.isInPosition() || (!SlideWinder.elevator.isGoingUp() && SlideWinder.elevator.getLowerLimit()) || (SlideWinder.elevator.isGoingUp() && SlideWinder.elevator.getUpperLimit());
	}

	// Called once after isFinished returns true
	protected void end() {
		SlideWinder.elevator.activateBrake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

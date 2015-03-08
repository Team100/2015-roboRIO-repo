package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that picks up all 3 yellow totes and stacks them in the auto
 * zone.
 */
public class AutoModeThree_StackTotes extends CommandGroup {

	private final double DISTANCE_TO_TOTE;
	private final double DISTANCE_FORWARD;
	private final double DISTANCE_TO_SCORING;

	public AutoModeThree_StackTotes() {
		DISTANCE_TO_TOTE = Preferences.getDouble("AutoStackTotes_DistanceToTote");
		DISTANCE_FORWARD = Preferences.getDouble("AutoStackTotes_DistanceForward");
		DISTANCE_TO_SCORING = Preferences.getDouble("AutoStackTotes_DistanceToScoring");

		addSequential(new AutoCalibrateElevator(), 4.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new AutoDrive(DISTANCE_FORWARD, 0), 3.0);
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new CloseClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(4), 3.0);
		addSequential(new AutoDrive(0, DISTANCE_TO_TOTE), 3.0);
		addSequential(new SetElevatorPosition(2), 3.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new CloseClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(4), 3.0);
		addSequential(new AutoDrive(0, DISTANCE_TO_TOTE), 3.0);
		addSequential(new SetElevatorPosition(3), 3.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new CloseClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(2), 3.0);
		addSequential(new AutoDrive(DISTANCE_TO_SCORING), 3.0);
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new OpenClaw(), 1.0);
	}
}
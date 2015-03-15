package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that picks up all 3 yellow totes and stacks them in the auto
 * zone.
 */
public class AutoMode_StackTotes extends CommandGroup {

	private final double DISTANCE_TO_TOTE;
	private final double DISTANCE_FORWARD;
	private final double DISTANCE_TO_SCORING;

	public AutoMode_StackTotes() {
		DISTANCE_TO_TOTE = Preferences.getDouble("AutoStackTotes_DistanceToTote");
		DISTANCE_FORWARD = Preferences.getDouble("AutoStackTotes_DistanceForward");
		DISTANCE_TO_SCORING = Preferences.getDouble("AutoStackTotes_DistanceToScoring");

		addSequential(new AutoCalibrateElevator(), 3.0); //0
		addSequential(new OpenClaw()); 
		addSequential(new AutoDrive(DISTANCE_FORWARD, 0), 2.0); //1
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(0.75)); //.75
		addSequential(new SetElevatorPosition(4), 3.0); //2
		addSequential(new AutoDrive(0, DISTANCE_TO_TOTE), 3.0); //2
		addSequential(new SetElevatorPosition(2), 3.0); //2
		addSequential(new OpenClaw());
		addSequential(new AutoDelay(0.75)); //.75
		addSequential(new SetElevatorPosition(1), 3.0); //1
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(0.75)); //.75
		addSequential(new SetElevatorPosition(4), 3.0); //2
		addSequential(new AutoDrive(0, DISTANCE_TO_TOTE), 3.0); //2
		addSequential(new SetElevatorPosition(3), 3.0); //1
		addSequential(new OpenClaw());
		addSequential(new AutoDelay(0.75)); //.75
		addSequential(new SetElevatorPosition(1), 3.0); //2
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(0.75)); //.75
		addSequential(new SetElevatorPosition(2), 3.0); //1
		addSequential(new AutoDrive(DISTANCE_TO_SCORING), 3.0); //2
		addSequential(new SetElevatorPosition(1), 3.0); //1, optional 
		addSequential(new OpenClaw()); //1, optional
	}
}
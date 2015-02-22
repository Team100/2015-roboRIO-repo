package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeSeven_LandmarkStack extends CommandGroup {
	private final double startPosition;
	
	public AutoModeSeven_LandmarkStack() {
		startPosition = Preferences.getDouble("AutoStackTotes_StartPosition");
		
		addSequential(new AutoCalibrateElevator());
		addSequential(new OpenClaw());
		addSequential(new SetElevatorPosition(1));
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceForward"), 0));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(4));
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToScoring")+8));
		if (startPosition % 2 == 1.0) {
			addSequential(new AutoTurn((2 - startPosition)*90));
			addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToLandmark")));
		}

		addSequential(new SetElevatorPosition((int)Preferences.getDouble("AutoStackTotes_LandmarkStackOrder")));
		addSequential(new OpenClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new AutoDrive(-24.0, 0));
	}

}
package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeSeven_LandmarkStack extends CommandGroup {

	public AutoModeSeven_LandmarkStack() {
		addSequential(new AutoCalibrateElevator());
		addSequential(new OpenClaw());
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_Forward"), 0));
		addSequential(new SetElevatorPosition(1));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(4));
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToScoring")));
		addSequential(new AutoTurn(-90));
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToLandmark")));
		addSequential(new OpenClaw());
		addSequential(new AutoDrive(-24, 0));
	}

}
package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Pushes both a bin and a tote into the auto zone during autonomous
 */
public class AutoMode_PushBinAndTote extends CommandGroup {

	public AutoMode_PushBinAndTote() {
		addSequential(new AutoCalibrateElevator(), 4.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceForward"), 0), 3.0);
		addSequential(new CloseClaw(), 1.0);
		addSequential(new AutoDelay(1));
		addSequential(new SetElevatorPosition(2), 3.0);
		addSequential(new AutoDrive(-12.0, 0), 3.0);
		addSequential(new AutoDrive(0, -20.0), 3.0);
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToScoring") + 12, 0), 3.0);
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new OpenClaw(), 1.0);
	}

}
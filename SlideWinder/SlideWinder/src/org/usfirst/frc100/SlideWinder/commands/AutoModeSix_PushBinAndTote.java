package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeSix_PushBinAndTote extends CommandGroup {

	public AutoModeSix_PushBinAndTote() {
		addSequential(new AutoCalibrateElevator());
		addSequential(new OpenClaw());
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceForward"), 0));
		addSequential(new CloseClaw());
		addSequential(new SetElevatorPosition(2));
		addSequential(new AutoDrive(-12, 0));
		addSequential(new AutoDrive(0, 20));
		addSequential(new AutoDrive(120));
		addSequential(new SetElevatorPosition(1));
		addSequential(new OpenClaw());
	}

}
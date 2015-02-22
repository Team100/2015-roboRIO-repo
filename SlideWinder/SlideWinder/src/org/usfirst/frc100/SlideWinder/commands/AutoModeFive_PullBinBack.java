package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeFive_PullBinBack extends CommandGroup {

	public AutoModeFive_PullBinBack() {
		addSequential(new AutoCalibrateElevator());
		if( SlideWinder.claw.isClosed() ){
			addSequential(new OpenClaw());
			addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceForward"), 0));
		} 
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(2));
		addSequential(new AutoDrive(-Preferences.getDouble("AutoStackTotes_DistanceToScoring") + 4, 0));
		addSequential(new SetElevatorPosition(1));
		addSequential(new OpenClaw());
	}

}
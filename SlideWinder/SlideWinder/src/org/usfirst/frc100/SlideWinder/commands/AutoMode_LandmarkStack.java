package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Stacks tote on the step for the co-opertition set during autonomous
 * to-do explain what 'startPosition' means
 */
public class AutoMode_LandmarkStack extends CommandGroup {
	private final int startPosition;
	
	public AutoMode_LandmarkStack() {
		startPosition = (int)Preferences.getDouble("AutoStackTotes_StartPosition");
		
		addSequential(new AutoCalibrateElevator(), 4.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new SetElevatorPosition(1), 3.0);
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceForward"), 3.0));
		addSequential(new CloseClaw(), 1.0);
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(4), 3.0);
		addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToScoring")+8), 3.0);
		if (startPosition % 2 == 1) {
			addSequential(new AutoTurn((2 - startPosition)*90), 3.0);
			if(startPosition == 1){
				addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToLandmark") - 21), 3.0);
			} else {
				addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToLandmark")), 3.0);
			}		
		}
		addSequential(new SetElevatorPosition((int)Preferences.getDouble("AutoStackTotes_LandmarkStackOrder")), 3.0);
		addSequential(new OpenClaw(), 1.0);
		addSequential(new AutoDelay(1.0));
		addSequential(new AutoDrive(-24.0, 0), 4.0);
	}

}
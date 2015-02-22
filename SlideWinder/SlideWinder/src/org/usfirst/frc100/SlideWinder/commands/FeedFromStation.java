package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Student
 * The driver will park in front of the human player station and add the tote ejected to the bottom of a held stack
 */
public class FeedFromStation extends CommandGroup {

	public FeedFromStation() {
		addSequential(new SetElevatorPosition(24.0, false));	// high enough to unblock the tote shoot
		addSequential(new AutoDelay(2.0));
		addSequential(new SetElevatorPosition(2));
		addSequential(new OpenClaw());
		addSequential(new SetElevatorPosition(1));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(24.0, false));
	}
}

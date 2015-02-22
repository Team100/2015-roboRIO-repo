package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FeedFromStation extends CommandGroup {

	public FeedFromStation() {
		addSequential(new SetElevatorPosition(2));
		addSequential(new OpenClaw());
		addSequential(new SetElevatorPosition(1));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(24.0, false));
	}
}

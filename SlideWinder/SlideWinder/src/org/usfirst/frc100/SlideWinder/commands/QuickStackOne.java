package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class QuickStackOne extends CommandGroup {

	public QuickStackOne() {
		addSequential(new SetElevatorPosition(2));
		addSequential(new OpenClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(1));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(2));
	}

}
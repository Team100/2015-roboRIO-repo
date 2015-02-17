package org.usfirst.frc100.SlideWinder.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Places a stack on two totes and picks up the whole stack
 */
public class QuickStackTwo extends CommandGroup {

	public QuickStackTwo() {
		addSequential(new SetElevatorPosition(3));
		addSequential(new OpenClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(1));
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(1.0));
		addSequential(new SetElevatorPosition(2));
	}

}
package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that picks up all 3 yellow totes and stacks them in the auto zone.
 */
public class AutoStackTotes extends CommandGroup {
	final double DISTANCE_TO_TOTE = 9001.0;
	final double DISTANCE_FORWARD = 10;
	
    AutoStackTotes() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new OpenClaw());
    	addSequential(new AutoDrive(DISTANCE_FORWARD, 0));
    	addSequential(new SetElevatorPosition(1));
    	addSequential(new CloseClaw());
    	addSequential(new SetElevatorPosition(2));
    	addSequential(new AutoDrive(0, DISTANCE_TO_TOTE));
    	addSequential(new OpenClaw());
    	addSequential(new SetElevatorPosition(1));
    	addSequential(new CloseClaw());
    	addSequential(new SetElevatorPosition(2));
    	addSequential(new AutoDrive(0, DISTANCE_TO_TOTE));
    	addSequential(new OpenClaw());
    	addSequential(new SetElevatorPosition(1));
    	addSequential(new CloseClaw());
    	addSequential(new SetElevatorPosition(2));
    	
    }
    
    
}

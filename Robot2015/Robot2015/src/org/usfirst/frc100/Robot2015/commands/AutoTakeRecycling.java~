package org.usfirst.frc100.Robot2015.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the field.
 */
public class AutoTakeRecycling extends CommandGroup {
    
	final double DRIVE_LENGTH = 10;
	final double SLIDE_DISTANCE = 10;
    public  AutoTakeRecycling() {
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
    	addSequential(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_LENGTH));
    	addSequential(new GrabRecycling());
    	addSequential(new DeployArm(false));
    	addSequential(new DropRecycling());
    	addSequential(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_LENGTH));
    	addSequential(new GrabRecycling());
    	addSequential(new DeployArm(false));
    	addSequential(new DropRecycling());
    	addSequential(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_LENGTH));
    	addSequential(new GrabRecycling());
    	addSequential(new DeployArm(false));
    	addSequential(new DropRecycling());
    	addSequential(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_LENGTH));
    	addSequential(new GrabRecycling());
    	addSequential(new DeployArm(false));
    	addSequential(new DropRecycling());
    	addSequential(new AutoDrive(0, SLIDE_DISTANCE));
    }
}
